package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.score.FixedOnBuildScore

class ShrineOfTheElderTree : Monument, FixedOnBuildScore {
    override val pieceName = "Shrine of the Elder Tree"
    override val text = "(Point) based on the number of buildings in your town when constructed."
    override val shape = Shape(listOf(
        listOf(BRICK(), WHEAT(), STONE()),
        listOf(WOOD(), GLASS(), WOOD())
    ))

    override var fixedScore = 0
    override fun fixScore(gameGrid: GameGrid) {
        val buildingsOnConstruction = gameGrid.values.filterIsInstance<Building>().count()
        fixedScore = when (buildingsOnConstruction) {
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> 4
            5 -> 5
            else -> 8
        }
    }

    override fun onBuild(gameGrid: GameGrid) {
        fixScore(gameGrid)
    }
}