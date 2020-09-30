package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.*
import com.benjfletch.tinytowns.model.Shape

/** The most basic [Building] - Cottage. */
object Cottage: Building {
    override val pieceName = "Cottage"
    override val category = BuildingCategory.COTTAGE
    override val text = "3 (Points) if this building is fed"
    override val canBeBuiltAnywhere = false
    override val shape = Shape(listOf(
        listOf(NONE, WHEAT),
        listOf(BRICK, GLASS)))
}
