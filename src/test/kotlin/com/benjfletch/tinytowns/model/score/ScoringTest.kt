package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

abstract class ScoringTest {
    @ParameterizedTest
    @MethodSource("scores")
    fun `Accurately calculates score`(scoringPiece: ScoringPiece, inputLocation: Location, playerGameGrid: GameGrid, expected: Int) {
        val score = scoringPiece.score(inputLocation, playerGameGrid, emptyMap())
        assertThat(score)
                .withFailMessage("Expected score of $expected from ${scoringPiece.pieceName} but got $score")
                .isEqualTo(expected)
    }
}

abstract class PlayerBasedScoringTest {
    @ParameterizedTest
    @MethodSource("scores")
    fun `Accurately calculates score`(scoringPiece: ScoringPiece, inputLocation: Location, playerGameGrid: GameGrid, expected: Int, otherPlayerGameGrid: GameGrid) {
        val score = scoringPiece.score(inputLocation, playerGameGrid, otherPlayerGameGrid)
        assertThat(score)
                .withFailMessage("Expected score of $expected from ${scoringPiece.pieceName} but got $score")
                .isEqualTo(expected)
    }
}
