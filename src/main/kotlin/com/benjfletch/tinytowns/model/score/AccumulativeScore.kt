package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building

/**
 * Interface to represent a [ScoringPiece] who's score is based on the number of a given building on the board
 */
interface AccumulativeScore : ScoringPiece {
    /** Condition which makes a [Building] eligible to be included in the Accumulation */
    val condition: (Building) -> Boolean

    override fun score(pieceLocation: Location, gameGrid: GameGrid): Int {
        return gameGrid.values
                .filterIsInstance<Building>()
                .count { condition.invoke(it) }
    }
}
