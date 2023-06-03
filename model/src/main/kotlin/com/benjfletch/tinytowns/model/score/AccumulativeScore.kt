package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.buildings.BuildingType
import kotlin.reflect.KClass

/**
 * Interface to represent a [ScoringPiece] who's score is based on the number of a given building on the board
 */
interface AccumulativeScore : ScoringPiece

interface AccumulativeConstructedScore: ScoringPiece {
    val types: Iterable<KClass<out Building>>
    val scores: Map<Int, Int>
    val maxScore: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val matchedBuildings = gameGrid.count { (_, piece) -> types.any { it.isInstance(piece) } }
        return scores.getOrDefault(matchedBuildings, maxScore)
    }
}

interface AccumulativeTypeScore : ScoringPiece {
    val types: Iterable<BuildingType>
    // How many points each found type is worth
    val scorePerType: Int
    // Score to add to when a type is found
    val initialScore: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val matchedBuildingScore = gameGrid.values
            .mapNotNull { if (it is Building) it.buildingType else null }
            .filter { types.contains(it) }
            .distinct()
            .sumOf { scorePerType }
        return initialScore + matchedBuildingScore
    }
}