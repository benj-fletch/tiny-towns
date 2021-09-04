package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.MutableGameGrid

interface Feeder: Building {
    /**
     * Feed all the eligible [FeedableBuildings][FeedableBuilding] in the provided [MutableGameGrid]. This should be
     * accomplished by replacing the unfed version with the fed version of the same building.
     */
    fun feed(location: Location, gameGrid: MutableGameGrid)
}
