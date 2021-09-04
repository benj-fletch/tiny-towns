package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location

/**
 * Interface to represent a score that is always the same, no matter the board state.
 */
interface FlatScore : ScoringPiece {
    /** Score to apply to this [ScoringPiece] */
    val score: Int
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        return score
    }
}
