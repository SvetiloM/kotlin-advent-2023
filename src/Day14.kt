

fun sum(m: Int, n: Int, rocks: List<List<Int>>, bounds: List<List<Int>>): Int {
    var sum = 0
    val highs = IntArray(n) { -1 }
    for (i in 0 until m) {

        for (r in rocks[i]) {
            highs[r]++
            sum += m - highs[r]
        }

        for (b in bounds[i]) {
            if (highs[b] < i) highs[b] = i
        }

    }
    return sum
}



fun rotate(grid : MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
    return(0 until grid[0].size).map { j -> grid.map { it[j] }.toMutableList() }.toMutableList()
}

fun north(grid: List<List<Char>>) : MutableList<MutableList<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (chars in grid) {
        newGrid.add(chars.map { it }.toMutableList())
    }
    val rotated = rotate(newGrid)
    for (line in rotated) {
        //to the left
        for ( i in 0 until  line.size) {
            //move left
            var j = i
            while (j > 0 && line[j-1] == '.' && line[j] == 'O') {
                line[j - 1] = 'O'
                line[j] = '.'
                j--
            }
        }
    }
    return rotate(rotated)
}

fun west(grid: List<List<Char>>) : MutableList<MutableList<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (chars in grid) {
        newGrid.add(chars.map { it }.toMutableList())
    }
    for (line in newGrid) {
        //to the left
        for ( i in 0 until  line.size) {
            //move left
            var j = i
            while (j > 0 && line[j-1] == '.' && line[j] == 'O') {
                line[j - 1] = 'O'
                line[j] = '.'
                j--
            }
        }
    }
    return newGrid
}

fun south(grid: List<List<Char>>) : MutableList<MutableList<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (chars in grid) {
        newGrid.add(chars.map { it }.toMutableList())
    }
    val rotated = rotate(newGrid)
    for (line in rotated) {
        //to the right
        for ( i in line.size-1 downTo 0) {
            //move right
            var j = i
            while (j < line.size-1 && line[j+1] == '.' && line[j] == 'O') {
                line[j + 1] = 'O'
                line[j] = '.'
                j++
            }
        }
    }
    return rotate(rotated)
}

fun east(grid: List<List<Char>>) : MutableList<MutableList<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (chars in grid) {
        newGrid.add(chars.map { it }.toMutableList())
    }
    for (line in newGrid) {
        //to the right
        for ( i in line.size-1 downTo  0) {
            //move right
            var j = i
            while (j < line.size-1 && line[j+1] == '.' && line[j] == 'O') {
                line[j + 1] = 'O'
                line[j] = '.'
                j++
            }
        }
    }
    return newGrid
}

fun main() {

    fun part1(input: List<String>): Int {
        val m = input.size

        val grid = input.map { it.toList() }
        val rocks = grid.map { it.indices.filter { i -> it[i] == 'O' } }
        val bounds = grid.map { it.indices.filter { i -> it[i] == '#' } }

        return sum(m, input[0].length, rocks, bounds)
    }

    fun part2(input: List<String>): Int {
        val m = input.size

        var grid = input.map { it.toList().toMutableList() }.toMutableList()

        var cache = mutableMapOf<MutableList<MutableList<Char>>,MutableList<MutableList<Char>>>()

        var goal = 1000000000
        while (goal > -1) {
            if (!cache.containsKey(grid)) {
                val turn = east(south(west(north(grid))))
                cache[grid] = turn
                grid = turn
            } else grid = cache[grid]!!
            goal--
        }
        return 0;
    }

    val input = readInput("Day14")
//    part1(input).println()
    part2(input).println()
}
