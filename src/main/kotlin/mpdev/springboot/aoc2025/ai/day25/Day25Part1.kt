package mpdev.springboot.aoc2025.ai.day25

import java.io.File
import java.util.*

fun solve251() {
    val graph = mutableMapOf<String, MutableSet<String>>()

    // Read input from file or define inline
    val lines = File("./src/main/resources/inputdata/input25.txt").readLines()

    for (line in lines) {
        val (node, neighbors) = line.split(":")
        val from = node.trim()
        val toList = neighbors.trim().split(" ").map { it.trim() }
        graph.computeIfAbsent(from) { mutableSetOf() }
        for (to in toList) {
            graph.computeIfAbsent(to) { mutableSetOf() }
            graph[from]!!.add(to)
            graph[to]!!.add(from)
        }
    }

    // Estimate edge betweenness centrality
    val edgeScores = mutableMapOf<Pair<String, String>, Int>()
    val nodes = graph.keys.toList()

    for (start in nodes) {
        val queue: Queue<String> = LinkedList()
        val visited = mutableSetOf<String>()
        val parent = mutableMapOf<String, String>()

        queue.add(start)
        visited.add(start)

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            for (neighbor in graph[current]!!) {
                if (neighbor !in visited) {
                    visited.add(neighbor)
                    parent[neighbor] = current
                    queue.add(neighbor)
                }
            }
        }

        for ((child, par) in parent) {
            val edge = if (par < child) Pair(par, child) else Pair(child, par)
            edgeScores[edge] = edgeScores.getOrDefault(edge, 0) + 1
        }
    }

    // Remove top 3 edges
    val topEdges = edgeScores.entries.sortedByDescending { it.value }.take(3).map { it.key }
    for ((a, b) in topEdges) {
        graph[a]!!.remove(b)
        graph[b]!!.remove(a)
    }

    // Compute connected components
    val visited = mutableSetOf<String>()
    val sizes = mutableListOf<Int>()

    for (node in graph.keys) {
        if (node !in visited) {
            val size = dfsSize(node, graph, visited)
            sizes.add(size)
        }
    }

    val result = sizes.sortedDescending().take(2).reduce { a, b -> a * b }
    println("Optimized product of group sizes: $result")
}

fun dfsSize(start: String, graph: Map<String, Set<String>>, visited: MutableSet<String>): Int {
    val stack = Stack<String>()
    stack.push(start)
    var count = 0

    while (stack.isNotEmpty()) {
        val node = stack.pop()
        if (node !in visited) {
            visited.add(node)
            count++
            for (neighbor in graph[node]!!) {
                if (neighbor !in visited) stack.push(neighbor)
            }
        }
    }

    return count
}
