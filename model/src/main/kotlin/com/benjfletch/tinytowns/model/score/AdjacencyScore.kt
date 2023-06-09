package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.adjacentPieces
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.buildings.BuildingType

/**
 * Interface to represent a [ScoringPiece] who's score is dependent on the Building which surround it.
 */
interface AdjacencyScore : ScoringPiece {
    /** Collection of [Buildings][Building] which this [ScoringPiece] will gain points for being next to */
    val adjacentTypes: Iterable<BuildingType>

    /**
     * Helper method to determine if a [location] has any of [adjacentTypes] adjacent (up, down, left, right) to it
     */
    fun isAdjacent(location: Location, gameGrid: GameGrid): Boolean {
        return gameGrid.adjacentPieces(location)
            .filterIsInstance<Building>()
            .any { adjacentTypes.contains(it.buildingType) }
    }

    fun adjacentBuildings(location: Location, gameGrid: GameGrid): List<Building> {
        return gameGrid.adjacentPieces(location).filterIsInstance<Building>()
    }

    /**
     * Helper method to calculate the number of [adjacentTypes] buildings adjacent to [location]
     */
    fun numberOfAdjacent(location: Location, gameGrid: GameGrid): Int {
        return adjacentBuildings(location, gameGrid).count { adjacentTypes.contains(it.buildingType) }
    }
}

/**
 * Specification of [AdjacencyScore] which scores a given [ScoringPiece] based on if it is adjacent to at least one
 * [adjacentTypes].
 */
interface IfAdjacentScore: AdjacencyScore {
    /** Score to assign this [ScoringPiece] when adjacent to at least one [adjacentTypes] */
    val scoreWhenAdjacent: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        return if(isAdjacent(pieceLocation, gameGrid)) scoreWhenAdjacent else 0
    }
}

/**
 * Specification of [AdjacencyScore] which scores a given [ScoringPiece] based on whether it is not adjacent to any of
 * [adjacentTypes]
 */
interface NotAdjacentScore: AdjacencyScore {
    val scoreWhenNotAdjacent: Int
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        return if(!isAdjacent(pieceLocation, gameGrid)) scoreWhenNotAdjacent else 0
    }
}

/**
 * Specification of [AdjacencyScore] where a given score is applied for each [adjacentTypes] adjacent to this piece.
 */
interface AccumulativeAdjacencyScore: AdjacencyScore {
    /** Score to accumulate for each of the valid adjacent [Buildings][Building] */
    val scorePerAdjacent: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        return numberOfAdjacent(pieceLocation, gameGrid) * scorePerAdjacent
    }
}

interface UniqueAdjacencyScore: AdjacencyScore {
    val scorePerAdjacent: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        return adjacentBuildings(pieceLocation, gameGrid)
            .distinct()
            .sumOf { scorePerAdjacent }
    }
}
