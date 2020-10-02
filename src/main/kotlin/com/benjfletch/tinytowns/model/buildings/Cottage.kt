package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Shape

/** The most basic [Building] - Cottage. */
object Cottage: Building {
    override val pieceName = "Cottage"
    override val text = "3 (Points) if this building is fed"
    override val canBeBuiltAnywhere = false
    override val shape = Shape(listOf(
        listOf(NONE, WHEAT),
        listOf(BRICK, GLASS)))
}
