package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.model.Resource.*

/** The most basic [Building] - Cottage. */
class Cottage: Building {
    override val pieceName = "Cottage"
    override val text = "3 (Points) if this building is fed"
    override val canBeBuiltAnywhere = false
    override val shape = Shape(arrayOf(
        arrayOf(NONE, WHEAT),
        arrayOf(BRICK, GLASS)))
}
