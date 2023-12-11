import kotlin.math.max
import kotlin.math.min

fun main() {

    fun getGalaxies(input: List<String>): List<Pair<Int, Int>> {
        val res = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices)
            for (j in input[i].indices) {
                if (input[i][j] != '.') res.add(Pair(i, j))
            }

        return res
    }

    fun getSpace(n: Int, m: Int, galaxies: List<Pair<Int, Int>>): Pair<List<Int>, List<Int>> {
        return (0..n).filter { i -> galaxies.all { it.first != i } } to
        (0..m).filter { i -> galaxies.all { it.second != i } }
    }

    fun getShortPath(a: Pair<Int, Int>, b: Pair<Int, Int>, spaces: Pair<List<Int>, List<Int>>): Int {
        var y = Math.abs(a.first - b.first) //2
        var x = Math.abs(a.second - b.second) //3
        y+=spaces.first.filter { it in a.first .. b.first }.count() //+2
        x+=spaces.second.filter { it in min(a.second, b.second) .. max(a.second, b.second) }.count() //+1
        return x + y
    }

    fun getShortPath2(a: Pair<Int, Int>, b: Pair<Int, Int>, spaces: Pair<List<Int>, List<Int>>): ULong {
        var y: ULong = Math.abs(a.first - b.first).toULong() //2
        var x: ULong = Math.abs(a.second - b.second).toULong() //3
        y+= spaces.first.filter { it in a.first .. b.first }.count().toULong().times(1000000U-1U) //+2
        x+=spaces.second.filter { it in min(a.second, b.second) .. max(a.second, b.second) }.count().toULong().times(1000000U-1U) //+1
        return x + y
    }


    fun part1(input: List<String>): Int {
        var sum = 0
        val galaxies = getGalaxies(input)
        val spaces = getSpace(input.size-1, input[0].length-1, galaxies)
        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                sum += getShortPath(galaxies[i], galaxies[j], spaces)
            }
        }

        return sum
    }

    fun part2(input: List<String>): ULong {
        var sum = 0UL
        val galaxies = getGalaxies(input)
        val spaces = getSpace(input.size-1, input[0].length-1, galaxies)
        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                sum += getShortPath2(galaxies[i], galaxies[j], spaces)
            }
        }

        return sum
    }

    val input = readInput("Day11")
//    part1(input).println()
    part2(input).println()
}
