package mpdev.springboot.aoc2025.solutions.day25

import mpdev.springboot.aoc2025.model.PuzzlePartSolution
import mpdev.springboot.aoc2025.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day25: PuzzleSolver() {

    var result = 0

    final override fun setDay() {
        day = 25
    }

    init {
        setDay()
    }

    override fun initSolver(): Pair<Long, String> {
        val elapsed = measureNanoTime {
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            result = 1234567890
        }
        return PuzzlePartSolution(1, result.toString(), elapsed, "milli-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        return PuzzlePartSolution(2, "MERRY CHRISTMAS!!", 0, "milli-sec")
    }
}