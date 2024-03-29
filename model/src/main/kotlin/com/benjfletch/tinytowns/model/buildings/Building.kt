package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.BuildingException
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.ResourceMatrix
import com.benjfletch.tinytowns.model.Shape

/** Specification of [GamePiece] for a constructed building. */
interface Building: GamePiece {
    /** Name of the building */
    override val pieceName: String
    /** Building type which the building belongs to */
    val buildingType: BuildingType
    /** Effect text on the card */
    val text: String
    /** Required resource configuration to build */
    val shape: Shape
    /** Determines whether the building can be built anywhere*/
    val canBeBuiltAnywhere: Boolean
        get() = false

    /**
     * Helper function to check if a given [ResourceMatrix] matches the [Shape] of the current building.
     * @throws BuildingException when [resourceMatrix] is not valid to build this [Building] type
     */
    fun matrixMatches(resourceMatrix: ResourceMatrix) {
        if(!shape.matches(resourceMatrix)) {
            throw BuildingException("Resources $resourceMatrix in this configuration cannot be used to build $pieceName")
        }
    }
}

enum class BuildingType {
    ATTRACTION,
    COTTAGE,
    FOOD_PRODUCER,
    GOODS_HANDLER,
    MONUMENT,
    PLACE_OF_WORSHIP,
    RESTAURANT,
    SHOP,
}