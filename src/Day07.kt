fun main() {

    fun getCard(c : Char): Int {
        if (c == 'T') return 10
        if (c == 'J') return 11
        if (c == 'Q') return 12
        if (c == 'K') return 13
        if (c == 'A') return 14
        return c -'0'
    }

    fun getCardJoker(c : Char): Int {
        if (c == 'T') return 11
        if (c == 'J') return 2
        if (c == 'Q') return 12
        if (c == 'K') return 13
        if (c == 'A') return 14
        return c -'0'+1
    }

    fun getCards(s: String): List<Int> {
        return s.split(' ').first().map { getCard(it)}
    }

    fun getCardsJoker(s: String): List<Int> {
        return s.split(' ').first().map { getCardJoker(it)}
    }

    fun getBait(s: String) : Int {
        return s.split(' ').last().toInt()
    }

    fun getRank(l : List<Int>) : Int {
        val arr = IntArray(13)

        for (i in l) {
            arr[13-i+1]++
        }

        var max = 0
        var prevMax = 0

        for (i in arr) {
            if (i >= max) {
                prevMax = max
                max = i
            } else if (i > prevMax) prevMax = i
        }

        if (max > 3) return max + 1 //AAAA1 & AAAAA
        if (max + prevMax == 5) return 4 // AAA11
        if (max == 3) return 3 //AAA12
        if (max == 2 && prevMax == 2) return 2 // AA112
        if (max == 2) return 1
        return 0
    }

    fun getRankWithJokers(l : List<Int>) : Int {
        val arr = IntArray(13)

        for (i in l) {
            arr[14-i]++
        }

        val jokers = arr[12]
        arr[12] = 0

        arr.sortDescending()

        if (jokers == 5) return 6

        if (jokers == 0) {
            if (arr[0] == 5) return 6
            if (arr[0] == 4) return 5
            if (arr[0] == 3 && arr[1] == 2) return 4
            if (arr[0] == 3) return 3
            if (arr[0] == 2 && arr[1] == 2) return 2
            if (arr[0] == 2) return 1
            return 0
        } else {
            if (arr[0] + jokers == 5) return 6
            if (arr[0] + jokers == 4) return 5

            if (arr[0] + jokers == 3 && arr[1] == 2 || arr[0] == 3 && arr[1] + jokers == 2) return 4
            if (arr[0] + jokers == 3) return 3
            if (arr[0] + jokers == 2 && arr[1] == 2 || arr[0] == 2 && arr[1] + jokers == 2) return 2
            if (arr[0] + jokers == 2) return 1
            return 0
        }
    }

    fun isLeftStronger(l1 : List<Int>, l2 : List<Int>, getRank : (List<Int>) -> Int) : Int {
        val rank1 = getRank(l1)
        val rank2 = getRank(l2)

        if (rank1 != rank2) return rank1 - rank2
        for (i in l1.indices) {
            if (l1[i] != l2[i]) return l1[i] - l2[i]
        }
        return 0
    }

    fun part1(input: List<String>): ULong {

        val arr = mutableListOf<Pair<List<Int>, Int>>()

        for (s in input) {
            arr.add(Pair(getCards(s), getBait(s)))
        }
        arr.sortWith { p1, p2 -> isLeftStronger(p1.first, p2.first, ::getRank) }
        var rank = 1
        var sum : ULong = 0u

        for (pair in arr) {
            sum += (pair.second*rank).toUInt()
            rank++
        }

        return sum
    }

    fun part2(input: List<String>): ULong {
        val arr = mutableListOf<Pair<List<Int>, Int>>()

        for (s in input) {
            arr.add(Pair(getCardsJoker(s), getBait(s)))
        }
        arr.sortWith { p1, p2 -> isLeftStronger(p1.first, p2.first, ::getRankWithJokers) }
        var rank = 1
        var sum : ULong = 0u

        for (pair in arr) {
            sum += (pair.second*rank).toUInt()
            rank++
        }

        return sum
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
