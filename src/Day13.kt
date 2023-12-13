fun readPattern(input: List<String>): List<List<String>> {
    val res = mutableListOf<List<String>>()
    var pattern = mutableListOf<String>()
    for (s in input) {
        if (s != "") pattern.add(s)
        else res.add(pattern).also { pattern = mutableListOf() }
    }
    res.add(pattern)
    return res
}

fun verticalMirror(input: List<String>): Int {
    val mirrors = mutableListOf<Int>()

    for (i in input[0].indices) {
        if (checkPalindrome(input[0], i)) mirrors.add(i)
    }

    return (mirrors.firstOrNull { i -> input.all { checkPalindrome(it, i) } } ?: -1).plus(1)
}

fun verticalMirror2(input: List<String>): Int {
    val n = input[0].length - 1 //последний индекс не проверяем
    for (i in 0 until n) {
        var count = 0
        for (s in input) {
            count+=checkPalindrome2(s,i)
        }
        if (count == 1) return i+1
    }
    return 0
}

fun horizontalMirror(input: List<String>): Int {
    val mirrors = mutableListOf<Int>()

    for (i in input.indices) {
        if (checkPalindrome(input, i, 0)) mirrors.add(i)
    }
    return (mirrors.firstOrNull { i -> input[0].indices.all { checkPalindrome(input, i, it) } } ?: -1).plus(1)
}

fun horizontalMirror2(input: List<String>): Int {
    val n = input[0].length
    for (i in 0 until input.size-1) {
        var count = 0
        for (j in 0 until n) {
            count+=checkPalindrome2(input, i, j)
        }
        if (count == 1) return i+1
    }
    return 0
}


fun checkPalindrome(s: List<String>, m: Int, c: Int): Boolean {
    if (m == s.size-1) return false
    var j = m + 1
    var i = m
    while (i >= 0 && j < s.size) {
        if (s[i][c] != s[j][c]) return false
        i--
        j++
    }
    return true
}

fun checkPalindrome2(s: List<String>, m: Int, c: Int): Int {
    if (m == s.size-1) return -1
    var j = m + 1
    var i = m
    var count = 0;
    while (i >= 0 && j < s.size) {
        if (s[i][c] != s[j][c]) count++
        i--
        j++
    }
    return count
}

// i = 3 means 3 and 4 should be the same like the mirror is right between 3 and 4
fun checkPalindrome(s: String, m: Int): Boolean {
    if (m == s.length-1) return false
    var j = m + 1
    var i = m
    while (i >= 0 && j < s.length) {
        if (s[i] != s[j]) return false
        i--
        j++
    }
    return true
}

fun checkPalindrome2(s: String, m: Int): Int {
    if (m == s.length-1) return -1
    var j = m + 1
    var i = m
    var count = 0
    while (i >= 0 && j < s.length) {
        if (s[i] != s[j]) count++
        i--
        j++
    }
    return count
}

fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        val patterns = readPattern(input)
        for (pattern in patterns) {
            sum+=verticalMirror(pattern)
            sum+=horizontalMirror(pattern).times(100)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val patterns = readPattern(input)
        for (pattern in patterns) {
            sum+=verticalMirror2(pattern)
            sum+=horizontalMirror2(pattern).times(100)
        }
        return sum
    }

    val input = readInput("Day13")
//    part1(input).println()
    part2(input).println()
}
