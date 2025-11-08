package mpdev.springboot.aoc2025.ai.day01

import java.io.File

fun solve011() {
    // Replace with your actual input file path
    val lines = File("./src/main/resources/inputdata/input01.txt").readLines()

    var total = 0

    for (line in lines) {
        val digits = line.filter { it.isDigit() }
        if (digits.isNotEmpty()) {
            val first = digits.first()
            val last = digits.last()
            val value = "$first$last".toInt()
            total += value
        }
    }

    println("Total calibration value: $total")
}