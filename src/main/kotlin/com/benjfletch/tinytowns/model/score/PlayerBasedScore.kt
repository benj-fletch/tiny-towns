package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.countPieces

interface PlayerBasedScore: ScoringPiece

interface MoreBuildingTypeThanOtherPlayerScore: PlayerBasedScore {
    val baseScore: Int
    val buildingType: Building
    val bonusIfPlayerHasMore: Int
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val thisPlayerBuildingCount = gameGrid.countPieces(buildingType)
        val otherPlayerBuildingCount = otherPlayerGrid?.countPieces(buildingType) ?: 0
        return baseScore +
                if (thisPlayerBuildingCount > otherPlayerBuildingCount) bonusIfPlayerHasMore else 0
    }
}
