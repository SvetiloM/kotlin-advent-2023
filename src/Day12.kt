fun isPossiblyBroken(line: String, i: Int, j: Int): Boolean {
    if (j > line.length) return false //too big
    if (j == line.length) return (i until j).all { line[it] != '.' } //??? is pb ?#? is pb ### is pb
    return (i until j).all { line[it] != '.' } && line[j] != '#' // ?#??? can suit into
}

fun countBroken(line: String, brokens: List<Int>, cache: Array<LongArray>, i: Int, j: Int): Long {
    if (j == brokens.size) {
        return 0
    }
    val end = i + brokens[j]

    if (!isPossiblyBroken(line, i, end)) {
        return 0 //clear
    }

    if (end == line.length) { //its should be the last group
        return if (j == brokens.size - 1) 1 else 0
    }
    //step
    return count(line, brokens, cache, end + 1, j + 1)
}

fun count(line: String, brokens: List<Int>, cache: Array<LongArray>, i: Int, j: Int): Long {
    if (i == line.length) {
        return if (j == brokens.size) 1 else 0
    }

    if (cache[i][j] != -1L) {
        return cache[i][j]
    }
    if (line[i] == '.') return count(line, brokens, cache, i + 1, j).also { cache[i][j] = it }
    if (line[i] == '#') return countBroken(line, brokens, cache, i, j).also { cache[i][j] = it }
    return (count(line, brokens, cache, i + 1, j) + countBroken(line, brokens, cache, i, j)).also { cache[i][j] = it }
}

fun main() {

    fun part1(input: List<String>): Long {
        return input.map { line ->
            Pair(line.substringBefore(' '),
                line.substringAfter(' ').split(',').map { it.toInt() })
        }.sumOf { (f, s) ->
            count(f, s, Array(f.length) {
                LongArray(s.size + 1) { -1L }
            }, 0, 0)
        }
    }

    fun part2(input: List<String>): Long {
        return input.map { line ->
            Pair(line.substringBefore(' '),
                line.substringAfter(' ').split(',').map { it.toInt() })
        }
            .map { p ->
                Pair((0 until 5).joinToString("?") { p.first },
                    (0 until 5).flatMap { p.second })
            }.sumOf { (row, counts) ->
                count(row, counts, Array(row.length) {
                    LongArray(counts.size + 1) { -1L }
                }, 0, 0)
            }
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
