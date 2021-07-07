package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location

/**
 * Interface to represent a [GamePiece] which is possible to have a score at the end of the game.
 */
interface ScoringPiece : GamePiece {
    /**
     * Determine the score of this [GamePiece] based on the state of the board
     *
     * @param pieceLocation The location of the [GamePiece] to score
     * @param gameGrid The board state to use for score calculation
     */
    fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int
}
