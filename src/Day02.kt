
fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
//        only 12 red cubes, 13 green cubes, and 14 blue cubes?
        for (s in input) {
            var gameNum = 0;
            var i = 5;
            while (i < s.length) {
                if (s[i]==':') {
                    gameNum = Integer.parseInt(s.subSequence(5,i).toString())
                    break
                }
                i++
            }

            var red = 0;
            var blue = 0;
            var green = 0;

            //start of the game is i
            i+=2 //:_
            var j = i//left
            while (i < s.length) {
                if (s[i] == ' ') {
                    if (s[i+1] == 'r') {
                        val gameRed = Integer.parseInt(s.subSequence(j,i).toString())
                        if (red < gameRed) red = gameRed
                        i+=3;
                    } else if (s[i+1] == 'b') {
                        val gameBlue = Integer.parseInt(s.subSequence(j,i).toString())
                        if (blue < gameBlue) blue = gameBlue
                        i+=4
                    } else if (s[i+1] == 'g') {
                        val gameGreen = Integer.parseInt(s.subSequence(j,i).toString())
                        if(green < gameGreen) green = gameGreen
                        i+=5
                    }
                    i+=3
                    j=i
                }
                i++
            }
            if (red < 13 && green < 14 && blue < 15) sum+=gameNum
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
//        only 12 red cubes, 13 green cubes, and 14 blue cubes?
        for (s in input) {
            var gameNum = 0;
            var i = 5;
            while (i < s.length) {
                if (s[i]==':') {
                    gameNum = Integer.parseInt(s.subSequence(5,i).toString())
                    break
                }
                i++
            }

            var red = 0;
            var blue = 0;
            var green = 0;

            //start of the game is i
            i+=2 //:_
            var j = i//left
            while (i < s.length) {
                if (s[i] == ' ') {
                    if (s[i+1] == 'r') {
                        val gameRed = Integer.parseInt(s.subSequence(j,i).toString())
                        if (red < gameRed) red = gameRed
                        i+=3;
                    } else if (s[i+1] == 'b') {
                        val gameBlue = Integer.parseInt(s.subSequence(j,i).toString())
                        if (blue < gameBlue) blue = gameBlue
                        i+=4
                    } else if (s[i+1] == 'g') {
                        val gameGreen = Integer.parseInt(s.subSequence(j,i).toString())
                        if(green < gameGreen) green = gameGreen
                        i+=5
                    }
                    i+=3
                    j=i
                }
                i++
            }
            sum+=red*blue*green
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
