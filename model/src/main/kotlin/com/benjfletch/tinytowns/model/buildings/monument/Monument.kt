package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.buildings.BuildingType

/** Super interface for all Monuments (Pink) implemented in the game */
interface Monument: Building {
    override val buildingType: BuildingType
        get() = BuildingType.MONUMENT

    fun onBuild(gameGrid: GameGrid) {
        return
    }
}
