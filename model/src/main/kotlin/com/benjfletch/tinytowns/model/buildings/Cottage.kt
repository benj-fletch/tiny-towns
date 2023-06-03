package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.score.IfFedScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("cottage")
abstract class Cottage : FeedableBuilding, IfFedScore {
    override val pieceName = "Cottage"
    override val buildingType = BuildingType.COTTAGE
    override val text = "3 (Points) if this building is fed"
    override val scoreWhenFed = 3
    override val shape = Shape(listOf(
            listOf(NONE(), WHEAT()),
            listOf(BRICK(), GLASS())))
}

@Serializable
@SerialName("unfedCottage")
data class UnfedCottage(override var isFed: Boolean = false): Cottage()

@Serializable
@SerialName("fedCottage")
data class FedCottage(override var isFed: Boolean = true) : Cottage()
