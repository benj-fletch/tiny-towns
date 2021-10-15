package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.countPieces
import kotlin.reflect.KClass

/** Super interface for all Scoring calculations which are based on another players board status */
interface PlayerBasedScore: ScoringPiece

/** Specification of [PlayerBasedScore] which scores a given building type based on whether the current player has more
 * of a given [Building] than some other player */
interface MoreBuildingTypeThanOtherPlayerScore: PlayerBasedScore {
    val baseScore: Int
    val buildingType: KClass<out Building>
    val bonusIfPlayerHasMore: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val thisPlayerBuildingCount = gameGrid.countPieces(buildingType)
        val otherPlayerBuildingCount = otherPlayerGrid?.countPieces(buildingType) ?: 0
        return baseScore +
                if (thisPlayerBuildingCount > otherPlayerBuildingCount) bonusIfPlayerHasMore else 0
    }
}
