import kotlin.math.absoluteValue

fun main() {
    fun getSeeds(s : String) : List<Long> {
        return s.substringAfter("seeds:")
            .trim()
            .split(" ")
            .map {it.toLong()}
    }

    fun getMap(input : List<String>, title: String) : List<Pair<LongRange, LongRange>> {
        var i = 0
        while (i < input.size && !input[i].equals(title)) i++
        i++

        val res = mutableListOf<Pair<LongRange, LongRange>>()

        while (i < input.size && !input[i].equals("")) {
            val s = input[i]
            val strings = s.split(" ")
            val startSource = strings[1].toLong()
            val startTarget = strings[0].toLong()
            val range = strings[2].toLong()
            res.add(Pair((startSource until startSource + range) , (startTarget until startTarget + range)))
            i++
        }

        return res
    }

    fun goForward(map: List<Pair<LongRange, LongRange>>, source: Long): Long {
        val ranges = map.firstOrNull { it.first.contains(source) }
            ?: return source

        return ranges.second.first + (source - ranges.first.first).absoluteValue
    }

    fun part1(input: List<String>): Long {
        val seeds = getSeeds(input[0])
        var seedtosoil = getMap(input, "seed-to-soil map:")
        var soiltofertilizer = getMap(input, "soil-to-fertilizer map:")
        var fertilizertowater = getMap(input, "fertilizer-to-water map:")
        var watertolight = getMap(input, "water-to-light map:")
        var lighttotemperature = getMap(input, "light-to-temperature map:")
        var temperaturetohumidity = getMap(input, "temperature-to-humidity map:")
        var humiditytolocation = getMap(input, "humidity-to-location map:")

        var min = 0L

        val seedToLocationMap = mutableMapOf<Long, Long>()

        seeds.forEach { seed ->
            val soil = goForward(seedtosoil, seed)
            val fertilizer = goForward(soiltofertilizer, soil)
            val water = goForward(fertilizertowater, fertilizer)
            val light = goForward(watertolight, water)
            val temperature = goForward(lighttotemperature, light)
            val humidity = goForward(temperaturetohumidity, temperature)
            val location = goForward(humiditytolocation, humidity)
            seedToLocationMap[seed] = location
        }

        return seedToLocationMap.minBy { it.value }.value
    }

    fun getSeeds2(s : String) : List<LongRange> {
        return s.substringAfter("seeds:")
            .trim()
            .split(" ")
            .map {it.toLong()}
            .windowed(2,2)
            .map { it[0] until it[0]+it[1] }
    }

    fun backStep(map: List<Pair<LongRange, LongRange>>, source: Long): Long {
        val ranges = map.firstOrNull { it.second.contains(source) }
            ?: return source
        return ranges.first.first + (source - ranges.second.first).absoluteValue
    }

    fun part2(input: List<String>): Long {
        val seeds = getSeeds2(input[0])

        var seedtosoil = getMap(input, "seed-to-soil map:")
        var soiltofertilizer = getMap(input, "soil-to-fertilizer map:")
        var fertilizertowater = getMap(input, "fertilizer-to-water map:")
        var watertolight = getMap(input, "water-to-light map:")
        var lighttotemperature = getMap(input, "light-to-temperature map:")
        var temperaturetohumidity = getMap(input, "temperature-to-humidity map:")
        var humiditytolocation = getMap(input, "humidity-to-location map:")

        var minValue = Long.MAX_VALUE

        for (location in 1 until 100_000_000L) {
            val humidity = backStep(humiditytolocation, location)
            val temperature = backStep(temperaturetohumidity, humidity)
            val light = backStep(lighttotemperature, temperature)
            val water = backStep(watertolight, light)
            val fertilizer = backStep(fertilizertowater, water)
            val soil = backStep(soiltofertilizer, fertilizer)
            val seed = backStep(seedtosoil, soil)

            if (seeds.any { it.contains(seed) }) {
                minValue = location
                break
            }
        }
        return minValue
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
