package mpdev.springboot.aoc2025.ai.day19

import java.io.File

data class Part(val x: Int, val m: Int, val a: Int, val s: Int)
data class RuleP1(val category: String?, val op: String?, val value: Int?, val target: String)

fun solve191() {
    val input = File("./src/main/resources/inputdata/input19.txt").readLines()

    val workflows = mutableMapOf<String, List<RuleP1>>()
    val parts = mutableListOf<Part>()

    var parsingParts = false
    for (line in input) {
        if (line.isBlank()) {
            parsingParts = true
            continue
        }

        if (!parsingParts) {
            val name = line.substringBefore('{')
            val rulesRaw = line.substringAfter('{').removeSuffix("}")
            val rules = rulesRaw.split(",").map { rule ->
                if (':' in rule) {
                    val (cond, target) = rule.split(":")
                    val category = cond[0].toString()
                    val op = cond[1].toString()
                    val value = cond.substring(2).toInt()
                    RuleP1(category, op, value, target)
                } else {
                    RuleP1(null, null, null, rule)
                }
            }
            workflows[name] = rules
        } else {
            val values = Regex("""\d+""").findAll(line).map { it.value.toInt() }.toList()
            parts.add(Part(values[0], values[1], values[2], values[3]))
        }
    }

    fun evaluate(part: Part, workflowName: String): Boolean {
        var current = workflowName
        while (true) {
            val rules = workflows[current] ?: return false
            for (rule in rules) {
                if (rule.category == null) {
                    if (rule.target == "A") return true
                    if (rule.target == "R") return false
                    current = rule.target
                    break
                }

                val value = when (rule.category) {
                    "x" -> part.x
                    "m" -> part.m
                    "a" -> part.a
                    "s" -> part.s
                    else -> error("Unknown category")
                }

                val conditionMet = when (rule.op) {
                    ">" -> value > rule.value!!
                    "<" -> value < rule.value!!
                    else -> false
                }

                if (conditionMet) {
                    if (rule.target == "A") return true
                    if (rule.target == "R") return false
                    current = rule.target
                    break
                }
            }
        }
    }

    val acceptedSum = parts.filter { evaluate(it, "in") }
        .sumOf { it.x + it.m + it.a + it.s }

    println("Total rating sum of accepted parts: $acceptedSum")
}
