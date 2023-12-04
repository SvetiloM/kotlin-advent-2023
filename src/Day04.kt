
fun main() {
    val digits = '0' .. '9'

    fun getWinningNumbers(s: String, n : Int) : IntArray {
        val res = IntArray(n)

        var i = 0;
        while (i<s.length && s[i] !=':') i++
        i+=2

        var num = ""
        var j = 0
        while (i<s.length && s[i] != '|') {
            if (s[i] in digits) num = "$num${s[i]}"
            else if (num != "") {
                res[j] = Integer.parseInt(num)
                j++
                num = ""
            }
            i++
        }
        return res;
    }

    fun getNumbers(s : String, m : Int) : IntArray {
        val res = IntArray(m)

        var i = 0;
        while (i<s.length && s[i] !='|') i++
        i+=2

        var num = ""
        var j = 0
        while (i<s.length) {
            if (s[i] in digits) num = "$num${s[i]}"
            else if (num != "") {
                res[j] = Integer.parseInt(num)
                j++
                num = ""
            }
            i++
        }
        if (num != "") {
            res[j] = Integer.parseInt(num)
        }

        return res;
    }
    fun part1(input: List<String>): Int {
        var sum = 0
        //get sizes
        var n = 0;

        var i = 0;
        while (i < input[0].length && input[0][i] != ':') i++
        i+=2
        while (i < input[0].length && input[0][i] != '|') {
            if (input[0][i] == ' ' && input[0][i-1] in digits) n++
            i++
        }

        var m = 1

        i+=2
        while (i < input[0].length) {
            if (input[0][i] == ' ' && input[0][i-1] in digits) m++
            i++
        }

        for (s in input) {
            val winning = getWinningNumbers(s, n)
            val numbers = getNumbers(s, m)

            var m = 0.0;

            for (n in numbers) {
                for (w in winning) {
                    if (n == w) m++
                }
            }

            sum += Math.pow(2.0, m - 1).toInt()

        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        //get sizes
        var n = 0;

        var i = 0;
        while (i < input[0].length && input[0][i] != ':') i++
        i+=2
        while (i < input[0].length && input[0][i] != '|') {
            if (input[0][i] == ' ' && input[0][i-1] in digits) n++
            i++
        }

        var m = 1

        i+=2
        while (i < input[0].length) {
            if (input[0][i] == ' ' && input[0][i-1] in digits) m++
            i++
        }

        val p = IntArray(input.size){1}
        var j = 0

        for (s in input) {
            val winning = getWinningNumbers(s, n)
            val numbers = getNumbers(s, m)

            var m = 0

            for (n in numbers) {
                for (w in winning) {
                    if (n == w) m++
                }
            }

            for(k in 1 .. m) {
                if (j+k < p.size) p[j+k]+=p[j]
            }

            sum += p[j]
            j++

        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
