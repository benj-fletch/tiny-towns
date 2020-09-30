package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GamePiece

enum class BuildingCategory(override val pieceName: String): GamePiece {
    ATTRACTION("Attraction"),
    COTTAGE("Cottage"),
    SHOP("Shop"),
    FOOD_PRODUCER("Food Producer"),
}