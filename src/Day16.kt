import java.lang.Integer.max

lateinit var grid: List<List<Char>>
lateinit var visited: MutableList<MutableList<Char>>


enum class Direction(val c: Char) {
    UP('^'), DOWN('v'), RIGHT('>'), LEFT('<')
}

fun Char.getDirection(): Direction {
    return when (this) {
        '<' -> Direction.LEFT
        '>' -> Direction.RIGHT
        'v' -> Direction.DOWN
        else -> Direction.UP //^
    }
}

fun Char.isVisited(): Boolean {
    return this == '<' || this == '>' || this == '^' || this == 'v'
}

fun Pair<Int, Int>.next(d: Direction): Pair<Int, Int> {
    return when (d) {
        Direction.UP -> Pair(this.first - 1, this.second)
        Direction.DOWN -> Pair(this.first + 1, this.second)
        Direction.LEFT -> Pair(this.first, this.second - 1)
        else -> Pair(this.first, this.second + 1)
    }
}


fun traverse(visited: MutableList<MutableList<Char>>, p: Pair<Int, Int>, d: Direction) {
    if (p.first == grid.size || p.first == -1) return
    if (p.second == grid[p.first].size || p.second == -1) return
    val c = grid[p.first][p.second]
    val v = visited[p.first][p.second]

    if (v.isVisited() && v.getDirection() == d) return //loop

    if (c == '.') {
        visited[p.first][p.second] = d.c
        traverse(visited, p.next(d), d) //go next
    }

    if (c == '-') {
        visited[p.first][p.second] = d.c
        if (d == Direction.DOWN || d == Direction.UP) {
            traverse(visited, p.next(Direction.RIGHT), Direction.RIGHT)
            traverse(visited, p.next(Direction.LEFT), Direction.LEFT)
        } else traverse(visited, p.next(d), d)
    }

    if (c == '|') {
        visited[p.first][p.second] = d.c
        if (d == Direction.LEFT || d == Direction.RIGHT) {
            traverse(visited, p.next(Direction.UP), Direction.UP)
            traverse(visited, p.next(Direction.DOWN), Direction.DOWN)
        } else traverse(visited, p.next(d), d)
    }

    if (c == '\\') {
        visited[p.first][p.second] = d.c
        when (d) {
            Direction.RIGHT -> {
                traverse(visited, p.next(Direction.DOWN), Direction.DOWN)
            }

            Direction.LEFT -> {
                traverse(visited, p.next(Direction.UP), Direction.UP)
            }

            Direction.UP -> {
                traverse(visited, p.next(Direction.LEFT), Direction.LEFT)
            }

            Direction.DOWN -> {
                traverse(visited, p.next(Direction.RIGHT), Direction.RIGHT)
            }
        }
    }

    if (c == '/') {
        visited[p.first][p.second] = d.c
        when (d) {
            Direction.RIGHT -> {
                traverse(visited, p.next(Direction.UP), Direction.UP)
            }

            Direction.LEFT -> {
                traverse(visited, p.next(Direction.DOWN), Direction.DOWN)
            }

            Direction.UP -> {
                traverse(visited, p.next(Direction.RIGHT), Direction.RIGHT)
            }

            Direction.DOWN -> {
                traverse(visited, p.next(Direction.LEFT), Direction.LEFT)
            }
        }
    }
}

fun Pair<Int, Int>.inBounds(): Boolean {
    return this.first >= 0 && this.second >= 0 && this.first < grid.size && this.second < grid[0].size
}

fun iterativeTraverse(p: Pair<Int, Int>, d: Direction): Int {
    val seen = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
    val queue = ArrayDeque<Pair<Pair<Int, Int>, Direction>>().apply { add(Pair(p, d)) }

    fun add(p: Pair<Int, Int>, newDir : Direction) {
        val pn = p.next(newDir)
        val next = pn to newDir
        if (pn.inBounds() && next !in seen) {
            queue.add(next)
            seen.add(next)
        }
    }
    while (queue.isNotEmpty()) {
        val f = queue.removeFirst()
        val c = grid[f.first.first][f.first.second]
        if (c == '.') add(f.first, f.second)
        if (c == '-') {
            if (f.second == Direction.DOWN || f.second == Direction.UP) {
                add(f.first,Direction.RIGHT)
                add(f.first,Direction.LEFT)
            } else add(f.first,f.second)
        }
        if (c == '|') {
            if (f.second == Direction.LEFT || f.second == Direction.RIGHT) {
                add(f.first,Direction.UP)
                add(f.first,Direction.DOWN)
            } else add(f.first,f.second)
        }
        if (c == '\\') when (f.second) {
            Direction.RIGHT -> add(f.first,Direction.DOWN)
            Direction.LEFT -> add(f.first,Direction.UP)
            Direction.UP -> add(f.first,Direction.LEFT)
            Direction.DOWN -> add(f.first,Direction.RIGHT)
        }

        if (c == '/') when (f.second) {
            Direction.RIGHT -> add(f.first,Direction.UP)
            Direction.LEFT -> add(f.first,Direction.DOWN)
            Direction.UP -> add(f.first,Direction.RIGHT)
            Direction.DOWN -> add(f.first,Direction.LEFT)
        }
    }

    return seen.map { it.first }.toSet().size
}

fun main() {

    fun part1(input: List<String>): Int {
        grid = input.map { s -> s.toList() }
        val visited = mutableListOf<MutableList<Char>>()

        for (chars in grid) {
            visited.add(mutableListOf())
            for (c in chars) {
                visited.last().add(' ')
            }
        }


        traverse(visited, Pair(0, 0), Direction.RIGHT)

        return visited.sumOf { r -> r.filter { c -> c.isVisited() }.count() }
    }

    fun part2(input: List<String>): Int {
        grid = input.map { s -> s.toList() }


        var max = 0

        for (i in grid.indices) {
            max = Math.max(max, iterativeTraverse(Pair(i,0),Direction.RIGHT))
            max = Math.max(max, iterativeTraverse(Pair(i,grid[0].size-1),Direction.LEFT))
        }

        for (j in grid[0].indices) {
            max = Math.max(max, iterativeTraverse(Pair(0,j),Direction.DOWN))
            max = Math.max(max, iterativeTraverse(Pair(grid.size-1, j),Direction.UP))
        }

        return max
    }

    val input = readInput("Day16")
//    part1(input).println()
    part2(input).println()
}
