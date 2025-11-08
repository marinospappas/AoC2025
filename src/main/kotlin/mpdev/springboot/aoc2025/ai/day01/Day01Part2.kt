package mpdev.springboot.aoc2025.ai.day01

import java.io.File

fun solve012() {
    val digitWords = mapOf(
        "zero" to '0', "one" to '1', "two" to '2', "three" to '3',
        "four" to '4', "five" to '5', "six" to '6', "seven" to '7',
        "eight" to '8', "nine" to '9'
    )

    /*val input = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen"
    )*/

    val input = File("./src/main/resources/inputdata/input01.txt").readLines()

    var total = 0

    for (line in input) {
        val digits = mutableListOf<Char>()
        for (i in line.indices) {
            val c = line[i]
            if (c.isDigit()) {
                digits.add(c)
                continue
            }
            for ((word, digitChar) in digitWords) {
                if (line.startsWith(word, i)) {
                    digits.add(digitChar)
                    break
                }
            }
        }

        if (digits.isNotEmpty()) {
            val value = "${digits.first()}${digits.last()}".toInt()
            total += value
        }
    }

    println("Total calibration value: $total")
}
