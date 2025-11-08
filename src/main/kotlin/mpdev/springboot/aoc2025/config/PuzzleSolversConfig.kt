package mpdev.springboot.aoc2025.config

import mpdev.springboot.aoc2025.solutions.PuzzleSolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class PuzzleSolversConfig {

    @Autowired
    lateinit var puzzleSolversList: List<PuzzleSolver>

    @Bean
    open fun puzzleSolvers(): Map<Int,PuzzleSolver>  = puzzleSolversList.associateBy { solver -> solver.day }
}