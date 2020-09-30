package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource
import com.benjfletch.tinytowns.model.Shape

object TestBuilding: Building {
    val resourceMatrix = listOf(listOf(Resource.WOOD, Resource.STONE), listOf(Resource.NONE, Resource.GLASS))

    override val pieceName = "test"
    override val category = BuildingCategory.COTTAGE
    override val text = "A test building"
    override val canBeBuiltAnywhere = false
    override val shape = Shape(resourceMatrix)

    override fun score(board: Map<Location, GamePiece>): Int {
        return 0
    }
}

object TestAnywhereBuilding: Building {
    override val pieceName = "anywhereBuilding"
    override val category = BuildingCategory.COTTAGE
    override val text = ""
    override val shape = Shape(listOf(listOf(Resource.GLASS)))
    override val canBeBuiltAnywhere = true

    override fun score(board: Map<Location, GamePiece>): Int {
        return 0
    }
}
