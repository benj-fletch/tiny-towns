package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import kotlin.reflect.KClass

/**
 * Interface to represent a [ScoringPiece] who's score is based on the number of a given building on the board
 */
interface AccumulativeScore : ScoringPiece

interface AccumulativeConstructedScore: ScoringPiece {
    val types: Iterable<KClass<out Building>>
    val scores: Map<Int, Int>
    val maxScore: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid): Int {
        val matchedBuildings = gameGrid.count { piece -> types.any { it.isInstance(piece) } }
        return scores.getOrDefault(matchedBuildings, maxScore)
    }
}
