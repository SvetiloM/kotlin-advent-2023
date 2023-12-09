fun main() {

    fun getNumbers(s : String) : List<Int> {
        return s.trim().split(" ").map { Integer.parseInt(it) }
    }

    fun reduce(ints : List<Int>) : Int {
        if (ints.all { it == 0 }) return 0

        val newInts = mutableListOf<Int>()
        for (i in 1 until ints.size) {
            newInts.add(ints[i]-ints[i-1])
        }

        val prevVal = reduce(newInts)

        return prevVal + ints.last()
    }

    fun reduce2(ints : List<Int>) : Int {
        if (ints.all { it == 0 }) return 0

        val newInts = mutableListOf<Int>()
        for (i in 1 until ints.size) {
            newInts.add(ints[i]-ints[i-1])
        }

        val prevVal = reduce2(newInts)

        return ints.first() - prevVal
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (s in input) {
            sum += reduce(getNumbers(s))
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (s in input) {
            sum += reduce2(getNumbers(s))
        }
        return sum
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
