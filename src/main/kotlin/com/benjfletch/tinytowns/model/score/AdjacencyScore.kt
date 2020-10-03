package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.EmptySpace
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import kotlin.reflect.KClass

/**
 * Interface to represent a [ScoringPiece] who's score is dependant on the Building which surround it.
 */
interface AdjacencyScore : ScoringPiece {
    /** Collection of [Buildings][Building] which this [ScoringPiece] will gain points for being next to */
    val adjacentTypes: Iterable<KClass<out Building>>

    /** determine what [GamePieces][GamePiece] are adjacent to [buildingLocation] */
    fun adjacentPieces(buildingLocation: Location, otherLocations: Map<Location, GamePiece>): List<GamePiece> {
        return buildingLocation.adjacent().map { otherLocations.getOrDefault(it, EmptySpace) }
    }
}

/**
 * Specification of [AdjacencyScore] which scores a given [ScoringPiece] based on if it is adjacent to at least one
 * [adjacentTypes].
 */
interface IfAdjacentScore: AdjacencyScore {
    /** Score to assign this [ScoringPiece] when adjacent to at least one [adjacentTypes] */
    val scoreWhenAdjacent: Int

    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val isAdjacent = adjacentPieces(pieceLocation, pieces).any { piece -> adjacentTypes.any { it.isInstance(piece) } }
        return if(isAdjacent) scoreWhenAdjacent else 0
    }
}

/**
 * Specification of [AdjacencyScore] where a given score is applied for each [adjacentTypes] adjacent to this piece.
 */
interface AccumulativeAdjacencyScore: AdjacencyScore {
    /** Score to accumulate for each of the valid adjacent [Buildings][Building] */
    val scorePerAdjacent: Int

    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val adjacentScoringPieces = adjacentPieces(pieceLocation, pieces)
                .count { piece -> adjacentTypes.any { it.isInstance(piece) } }
        return adjacentScoringPieces * scorePerAdjacent
    }
}
