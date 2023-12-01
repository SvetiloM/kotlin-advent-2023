fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (s in input) {
            var i = 0
            var j = s.length - 1

            var left = false
            var right = false;

            while (i < s.length && !left) {
                if (s[i] in '0'..'9') left = true
                else i++
            }

            while (j >= 0 && !right) {
                if (s[j] in '0'..'9') right = true
                else j--
            }
            sum += Integer.parseInt("${s[i]}${s[j]}")

        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0;
        for (s in input) {
            var i = 0

            var left = 'a'
            var right = 'a'

            while (i < s.length && left=='a') {
                if (i > 2) {
                    //just want to have fun and no headache
                    if (s.subSequence(i-3, i) == "one") {
                        left = '1'
                        break
                    }
                    if (s.subSequence(i-3, i) == "two") {
                        left = '2'
                        break
                    }
                    if (s.subSequence(i-3, i) == "six") {
                        left = '6'
                        break
                    }
                }
                if (i > 3) {
                    if (s.subSequence(i-4, i) == "four") {
                        left = '4'
                        break
                    }
                    if (s.subSequence(i-4, i) == "five") {
                        left = '5'
                        break
                    }
                    if (s.subSequence(i-4, i) == "nine") {
                        left = '9'
                        break
                    }
                }

                if (i>4) {
                    if (s.subSequence(i-5, i) == "three") {
                        left = '3'
                        break
                    }
                    if (s.subSequence(i-5, i) == "seven") {
                        left = '7'
                        break
                    }
                    if (s.subSequence(i-5, i) == "eight") {
                        left = '8'
                        break
                    }
                }

                if (s[i] in '0'..'9') left = s[i]
                else i++
            }

            i = s.length-1
            while (i >= 0 && right=='a') {
                if (i > 1) {
                    if (s.subSequence(i - 2, i+1) == "one") {
                        right = '1'
                        break
                    }
                    if (s.subSequence(i - 2, i+1) == "two") {
                        right = '2'
                        break
                    }
                    if (s.subSequence(i - 2, i+1) == "six") {
                        right = '6'
                        break
                    }
                }
                if (i > 2) {
                    if (s.subSequence(i-3, i+1) == "four") {
                        right = '4'
                        break
                    }
                    if (s.subSequence(i-3, i+1) == "five") {
                        right = '5'
                        break
                    }
                    if (s.subSequence(i-3, i+1) == "nine") {
                        right = '9'
                        break
                    }
                }

                if (i>3) {
                    if (s.subSequence(i-4, i+1) == "three") {
                        right = '3'
                        break
                    }
                    if (s.subSequence(i-4, i+1) == "seven") {
                        right = '7'
                        break
                    }
                    if (s.subSequence(i-4, i+1) == "eight") {
                        right = '8'
                        break
                    }
                }
                if (s[i] in '0'..'9') right = s[i]
                else i--
            }
            sum += Integer.parseInt("$left$right")
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
