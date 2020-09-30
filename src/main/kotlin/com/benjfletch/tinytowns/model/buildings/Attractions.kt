package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WOOD
import com.benjfletch.tinytowns.model.Shape

object Fountain: Building {
    override val pieceName = "Fountain"
    override val category = BuildingCategory.ATTRACTION
    override val text = "2 (Point) if adjacent to a (Attraction)."
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = false
    override fun score(board: Map<Location, GamePiece>): Int {
        return 0
    }
}

object Millstone: Building {
    override val pieceName = "Millstone"
    override val category = BuildingCategory.ATTRACTION
    override val text = "2 (Point) if adjacent to a (FoodProducer) or (Shop)."
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = false
    override fun score(board: Map<Location, GamePiece>): Int {
        return 0
    }
}

object Shed: Building {
    override val pieceName = "Shed"
    override val category = BuildingCategory.ATTRACTION
    override val text = "1 (Point). May be constructed on any empty square in your town"
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = true
    override fun score(board: Map<Location, GamePiece>): Int {
        return 0
    }
}

object Well: Building {
    override val pieceName = "Well"
    override val category = BuildingCategory.ATTRACTION
    override val text = "1 (Point) for each adjacent (Cottage)"
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = false
    override fun score(board: Map<Location, GamePiece>): Int {
        return 0
    }
}
