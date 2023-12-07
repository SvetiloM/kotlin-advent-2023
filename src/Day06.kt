fun main() {

    fun getTimes(s: String): List<Int> {
        return s.substringAfter("Time:")
            .trim()
            .split(" ")
            .filter(String::isNotEmpty)
            .map { it.toInt() }
    }

    fun getTimes2(s: String): ULong {
        return s.substringAfter("Time:")
            .trim()
            .replace(" ", "")
            .toULong()
    }

    fun getDistances(s: String): List<Int> {
        return s.substringAfter("Distance:")
            .trim()
            .split(" ")
            .filter(String::isNotEmpty)
            .map { it.toInt() }
    }

    fun getDistances2(s: String): ULong {
        return s.substringAfter("Distance:")
            .trim()
            .replace(" ", "")
            .toULong()
    }


    fun part1(input: List<String>): Int {
        var mul = 1
        val times = getTimes(input[0])
        val distances = getDistances(input[1])

        for (i in times.indices) {
            var speed = 1
            var number = 0
            while (speed != times[i]) {
                if (distances[i] < (times[i] - speed) * speed) number++
                speed++
            }
            mul *= number
        }

        return mul
    }


    fun part2(input: List<String>): ULong {
        val time = getTimes2(input[0])
        val distance = getDistances2(input[1])

        var speed : ULong = 1u
        while (speed != time) {
            if (distance < (time - speed) * speed) {
                return time.plus(1u) - speed.times(2u)
            }
            speed++
        }

        return 0u
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
