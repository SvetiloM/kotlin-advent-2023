fun main() {

    fun getPattern(s: String): List<Int> {
        return s.map { if (it == 'L') 0 else 1 }
    }

    fun getKeyValue(s: String): Pair<String, Pair<String, String>> {
        val split = s.split("=")
        val key = split[0].trim()
        val value = split[1].trim().subSequence(1, split[1].length - 2).split(",")
        return Pair(key, Pair(value[0].trim(), value[1].trim()))
    }

    fun getTree(input: List<String>): Map<String, Pair<String, String>> {
        return input.drop(2).associate { getKeyValue(it) }
    }

    fun part1(input: List<String>): Int {

        val pattern = getPattern(input[0])
        val tree = getTree(input)

        var node = "AAA"
        var i = 0
        var steps = 0

        while (node != "ZZZ") {
            if (i == pattern.size) i = 0

            node = if (pattern[i] == 0) tree[node]!!.first else tree[node]!!.second

            i++
            steps++
        }

        return steps
    }

    fun ghostNodes(input: List<String>): List<String> {
        return input.drop(2).filter { it.split("=")[0].trim()[2] == 'A' }.map { it.split("=")[0].trim() }
    }

    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    fun part2(input: List<String>): Long {

        val pattern = getPattern(input[0])
        val tree = getTree(input)
        val gNodes = ghostNodes(input).toTypedArray()
        val result = LongArray(gNodes.size)

        var i = 0
        var steps = 1L
        var stop = true

        while (stop) {
            if (i == pattern.size) i = 0

            for (j in gNodes.indices) {
                if (result[j] != 0L) continue
                gNodes[j] = if (pattern[i] == 0) tree[gNodes[j]]!!.first else tree[gNodes[j]]!!.second
                if (gNodes[j][2] == 'Z') result[j] = steps
            }

            if (result.all { it != 0L }) stop = false

            i++
            steps++
        }

        return result.reduce{a,b -> lcm(a, b)}
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
