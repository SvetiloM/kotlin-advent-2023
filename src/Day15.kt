fun main() {

    fun String.hash17(): Int {
        var sum = 0
        this.map {
            sum += it.toInt()
            sum *= 17
            sum %= 256
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        return input.map { s ->
            s.split(',').map { it.hash17() }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        var boxes = Array<LinkedHashMap<String, Int>>(256) { LinkedHashMap() }

        for (s in input[0].split(',')) {
            if (s.contains('=')) {
                val split = s.split('=')
                val i = split[0].hash17()

                if (boxes[i].contains(split[0])) {
                    boxes[i][split[0]] = split[1].toInt()
                } else {
                    boxes[i].put(split[0], split[1].toInt())
                }
            } else { //contains -
                val split = s.split('-')
                val i = split[0].hash17()

                if (boxes[i].contains(split[0])) {
                    boxes[i].remove(split[0])
                }
            }
        }

        return boxes.mapIndexed { i, linkedHashMap ->
            var ssum = 0
            linkedHashMap.onEachIndexed { j, entry -> ssum+=entry.value*(j+1) }
            ssum*=(i+1)
            ssum}.sum()
    }

    val input = readInput("Day15")
//    part1(input).println()
    part2(input).println()
}
