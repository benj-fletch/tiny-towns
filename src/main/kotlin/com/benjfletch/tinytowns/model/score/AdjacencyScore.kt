package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.EmptySpace
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import kotlin.reflect.KClass

interface AdjacencyScore : ScoringPiece {
    val adjacentTypes: Iterable<KClass<out Building>>

    fun adjacentPieces(buildingLocation: Location, otherLocations: Map<Location, GamePiece>): List<GamePiece> {
        return buildingLocation.adjacent().map { otherLocations.getOrDefault(it, EmptySpace) }
    }
}

interface IfAdjacentScore: AdjacencyScore {
    val scoreWhenAdjacent: Int
    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val isAdjacent = adjacentPieces(pieceLocation, pieces).any { piece -> adjacentTypes.any { it.isInstance(piece) } }
        return if(isAdjacent) scoreWhenAdjacent else 0
    }
}

interface AccumulativeAdjacencyScore: AdjacencyScore {
    val scorePerAdjacent: Int

    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val adjacentScoringPieces = adjacentPieces(pieceLocation, pieces)
                .count { piece -> adjacentTypes.any { it.isInstance(piece) } }
        return adjacentScoringPieces * scorePerAdjacent
    }
}
