package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.buildings.Building
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

abstract class AccumulativeScoringTest : ScoringTest() {
    companion object {
        @JvmStatic
        fun accumulativeScoreParameters(toScore: Building, scores: List<Int>): Stream<Arguments> {
            val arguments = mutableListOf<Arguments>()

            val statefulBoard = Board()
            statefulBoard.gameGrid.keys
                    .take(scores.size)
                    .forEachIndexed { index, location ->
                        val board = Board()
                        statefulBoard.place(location, toScore)
                        board.gameGrid.putAll(statefulBoard.gameGrid)
                        arguments.add(Arguments.of(toScore, location, board.gameGrid, scores[index]))
                    }
            return arguments.stream()
        }
    }
}
