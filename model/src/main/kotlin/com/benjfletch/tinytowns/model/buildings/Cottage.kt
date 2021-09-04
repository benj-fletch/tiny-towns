package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.Shape

abstract class Cottage : FeedableBuilding {
    override val pieceName = "Cottage"
    override val text = "3 (Points) if this building is fed"
    override val shape = Shape(listOf(
            listOf(NONE, WHEAT),
            listOf(BRICK, GLASS)))

    object Unfed: Cottage() {
        override val isFed = false
    }

    object Fed : Cottage() {
        override val isFed = true
    }
}
