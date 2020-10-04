package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.adjacentPieces
import com.benjfletch.tinytowns.model.buildings.Building
import kotlin.reflect.KClass

/**
 * Interface to represent a [ScoringPiece] who's score is dependant on the Building which surround it.
 */
interface AdjacencyScore : ScoringPiece {
    /** Collection of [Buildings][Building] which this [ScoringPiece] will gain points for being next to */
    val adjacentTypes: Iterable<KClass<out Building>>
}

/**
 * Specification of [AdjacencyScore] which scores a given [ScoringPiece] based on if it is adjacent to at least one
 * [adjacentTypes].
 */
interface IfAdjacentScore: AdjacencyScore {
    /** Score to assign this [ScoringPiece] when adjacent to at least one [adjacentTypes] */
    val scoreWhenAdjacent: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid): Int {
        val isAdjacent = gameGrid.adjacentPieces(pieceLocation).any { piece -> adjacentTypes.any { it.isInstance(piece) } }
        return if(isAdjacent) scoreWhenAdjacent else 0
    }
}

/**
 * Specification of [AdjacencyScore] where a given score is applied for each [adjacentTypes] adjacent to this piece.
 */
interface AccumulativeAdjacencyScore: AdjacencyScore {
    /** Score to accumulate for each of the valid adjacent [Buildings][Building] */
    val scorePerAdjacent: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid): Int {
        val adjacentScoringPieces = gameGrid.adjacentPieces(pieceLocation)
                .count { piece -> adjacentTypes.any { it.isInstance(piece) } }
        return adjacentScoringPieces * scorePerAdjacent
    }
}
