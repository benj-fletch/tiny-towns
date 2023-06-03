package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.FeedableBuilding

interface IfFedScore: ScoringPiece, FeedableBuilding {
    val scoreWhenFed: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        return if(this.isFed) this.scoreWhenFed else 0
    }
}