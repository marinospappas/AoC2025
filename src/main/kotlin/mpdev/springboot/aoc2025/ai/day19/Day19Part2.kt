package mpdev.springboot.aoc2025.ai.day19

import java.io.File

data class Rule(val category: String?, val op: String?, val value: Int?, val target: String)
data class Range(val x: IntRange, val m: IntRange, val a: IntRange, val s: IntRange)

fun solve192() {
    val input = File("./src/main/resources/inputdata/input19.txt").readLines()

    val workflows = mutableMapOf<String, List<Rule>>()

    for (line in input) {
        val name = line.substringBefore('{')
        val rulesRaw = line.substringAfter('{').removeSuffix("}")
        val rules = rulesRaw.split(",").map { ruleText ->
            if (':' in ruleText) {
                val (cond, target) = ruleText.split(":")
                val category = cond[0].toString()
                val op = cond[1].toString()
                val value = cond.substring(2).toInt()
                Rule(category, op, value, target)
            } else {
                Rule(null, null, null, ruleText)
            }
        }
        workflows[name] = rules
    }

    fun volume(r: Range): Long {
        return r.x.count().toLong() * r.m.count() * r.a.count() * r.s.count()
    }

    fun countAccepted(range: Range, workflow: String): Long {
        val rules = workflows[workflow] ?: return 0
        var currentRange = range
        var total = 0L

        for (rule in rules) {
            if (rule.category == null) {
                return when (rule.target) {
                    "A" -> volume(currentRange)
                    "R" -> 0
                    else -> countAccepted(currentRange, rule.target)
                }
            }

            val categoryRange = when (rule.category) {
                "x" -> currentRange.x
                "m" -> currentRange.m
                "a" -> currentRange.a
                "s" -> currentRange.s
                else -> error("Unknown category")
            }

            val (passRange, failRange) = when (rule.op) {
                "<" -> {
                    val pass = categoryRange.first..(rule.value!! - 1)
                    val fail = maxOf(rule.value!!, categoryRange.first)..categoryRange.last
                    pass to fail
                }
                ">" -> {
                    val pass = (rule.value!! + 1)..categoryRange.last
                    val fail = categoryRange.first..minOf(rule.value!!, categoryRange.last)
                    pass to fail
                }
                else -> error("Unknown operator")
            }

            fun updateRange(base: Range, newRange: IntRange): Range {
                return when (rule.category) {
                    "x" -> base.copy(x = newRange)
                    "m" -> base.copy(m = newRange)
                    "a" -> base.copy(a = newRange)
                    "s" -> base.copy(s = newRange)
                    else -> base
                }
            }

            val passSubrange = updateRange(currentRange, passRange)
            val failSubrange = updateRange(currentRange, failRange)

            total += countAccepted(passSubrange, rule.target)
            currentRange = failSubrange
        }

        return total
    }

    val fullRange = Range(1..4000, 1..4000, 1..4000, 1..4000)
    val result = countAccepted(fullRange, "in")
    println("Accepted combinations: $result")
}
