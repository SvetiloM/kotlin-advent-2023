
fun main() {
    val digits = '0' .. '9'

    fun Char.isSymbol() : Boolean {
        return this !in digits && this != '.'
    }

    fun isAdj(input: List<String>, i: Int, j:Int ) : Boolean {
        if (j > 0 && i > 0 && input[j-1][i-1].isSymbol()) return true
        if (j > 0 && input[j-1][i].isSymbol()) return true
        if (j > 0 && i < input[j-1].length - 1 && input[j-1][i+1].isSymbol()) return true

        if (i > 0 && input[j][i-1].isSymbol()) return true
        if (i < input[j].length - 1 && input[j][i+1].isSymbol()) return true

        if (j < input.size - 1 && i > 0 && input[j+1][i-1].isSymbol()) return true
        if (j < input.size - 1  && input[j+1][i].isSymbol()) return true
        if (j < input.size - 1 && i < input[j+1].length - 1 && input[j+1][i+1].isSymbol()) return true

        return false
    }

    fun getGearNumber(s: String, i: Int) : Int {
        var k = i
        var num = ""
        while(k >= 0 && s[k] in digits) {
            num = "${s[k]}$num"
            k--
        }
        k = i + 1
        while (k < s.length && s[k] in digits) {
            num = "$num${s[k]}"
            k++
        }

        return if (num == "") 1 else Integer.parseInt(num)
    }

    fun getGearRatio(input: List<String>, i: Int, j:Int ) : Int {
        if (input[j][i] == '*') {
            var nums = 0;
            var mult = 1;
            if (j > 0 && i > 0 && input[j-1][i-1] in digits && input[j-1][i] !in digits) {
                nums++
                mult *= getGearNumber(input[j-1], i-1)
            } // diagonal end
            if (j > 0 && input[j-1][i] in digits) {
                nums++
                mult *= getGearNumber(input[j-1], i)
            }
            if (j > 0 && i < input[j-1].length - 1 && input[j-1][i+1] in digits && input[j-1][i] !in digits) {
                nums++
                if (nums > 2) return 0
                mult *= getGearNumber(input[j-1], i+1)
            }//diagonal start

            if (i > 0 && input[j][i-1] in digits) {
                nums++
                if (nums > 2) return 0
                mult *= getGearNumber(input[j], i-1)
            }
            if (i < input[j].length - 1 && input[j][i+1] in digits) {
                nums++
                if (nums > 2) return 0
                mult *= getGearNumber(input[j], i+1)
            }

            if (j < input.size - 1 && i > 0 && input[j+1][i-1] in digits && input[j+1][i] !in digits) {
                nums++
                if (nums > 2) return 0
                mult *= getGearNumber(input[j+1], i-1)
            }
            if (j < input.size - 1  && input[j+1][i] in digits) {
                nums++
                if (nums > 2) return 0
                mult *= getGearNumber(input[j+1], i)
            }
            if (j < input.size - 1 && i < input[j+1].length - 1 && input[j+1][i+1] in digits && input[j+1][i] !in digits) {
                nums++
                if (nums > 2) return 0
                mult *= getGearNumber(input[j+1], i+1)
            }

            return if (nums == 2) mult else 0
        }
        return 0
    }
    fun part1(input: List<String>): Int {
        var sum = 0
        var j = 0
        while (j < input.size) {
            val s = input[j]
            var num = ""
            var add = false
            var i = 0
            while (i < s.length) {
                if (s[i] in digits) {
                    num = "$num${s[i]}"
                    if (!add && isAdj(input, i, j)) add = true
                } else {
                    if (add) sum += Integer.parseInt(num)
                    num = ""
                    add = false
                }
                i++
            }
            if (add) sum += Integer.parseInt(num)
            j++
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        var j = 0
        while (j < input.size) {
            var i = 0
            while (i < input[j].length) {
                sum+=getGearRatio(input, i, j)
                i++
            }
            j++
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
