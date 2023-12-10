import kotlin.math.absoluteValue

fun main() {

    fun getS(input: List<String>): Pair<Int, Int> {
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 'S') return Pair(i, j)
            }
        }
        return Pair(0, 0)
    }

    val north = -1 to 0
    val east = 0 to 1
    val south = 1 to 0
    val west = 0 to -1

    fun getGrid(input: List<String>): Map<Pair<Int, Int>, List<Pair<Int, Int>>> {
        val map = mutableMapOf<Pair<Int, Int>, List<Pair<Int, Int>>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                map.put(
                    Pair(i,j) , when (input[i][j]) {
                    'L' -> listOf(north, east)
                    '|' -> listOf(north, south)
                    'J' -> listOf(north, west)
                    'F' -> listOf(east, south)
                    '-' -> listOf(east, west)
                    '7' -> listOf(south, west)
                    'S' -> listOf(north, east, south, west)
                    else -> emptyList()
                }.map { (y, x) -> y + i to x + j })
            }
        }
        return map
    }

    fun getPath(grid : Map<Pair<Int, Int>, List<Pair<Int, Int>>> , s : Pair<Int, Int>, hare : Pair<Int, Int>) : List<Pair<Int, Int>> {
        return generateSequence(Pair(s, hare)) { (from, to) ->
            when (to) {
                s -> null //stop
                else -> Pair(to, grid.getValue(to).minus(from).first())
            }
        }.map { it.first }.toList()
    }

    fun part1(input: List<String>): Int {
        var s = getS(input)
        val grid = getGrid(input)

        val hare = grid.getValue(s).first { from -> grid.getValue(from).any { it == s } }

        return getPath(grid, s, hare).size/2
    }

    fun part2(input: List<String>): Int {
        var s = getS(input)
        val grid = getGrid(input)
        val hare = grid.getValue(s).first { from -> grid.getValue(from).any { it == s } }
        val path = getPath(grid, s, hare)

        return path.plus(path.first())
            .zipWithNext { (y1, x1), (_, x2) -> (x2 - x1) * y1 }
            .sum().absoluteValue - part1(input) + 1
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
