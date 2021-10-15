package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.MutableGameGrid
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.Resource
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.buildings.monument.Monument

object TestBuilding: Building {
    val resourceMatrix = listOf(listOf(WOOD(), STONE()), listOf(NONE(), GLASS()))

    override val pieceName = "test"
    override val text = "A test building"
    override val shape = Shape(resourceMatrix)
}

object TestAnywhereBuilding: Building {
    override val pieceName = "anywhereBuilding"
    override val text = ""
    override val shape = Shape(listOf(listOf(GLASS())))
    override val canBeBuiltAnywhere = true
}

object TestAttraction: Attraction {
    override val pieceName = "testAttraction"
    override val text = "A test attraction"
    override val shape = Shape(listOf(listOf(WOOD(), STONE())))
}

object TestShop: Shop {
    override val pieceName = "testShop"
    override val text = "A test shop"
    override val shape = Shape(listOf(listOf(GLASS())))
}

object TestFoodProducer: FoodProducer {
    override val pieceName = "testFoodProducer"
    override val text = "A test food producer"
    override val shape = Shape(listOf(listOf(GLASS())))
    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        return 
    }
}

object TestGoodsHandler: GoodsHandler {
    override val pieceName = "testGoodsHandler"
    override val text = "A test goods handler"
    override val shape = Shape(listOf(listOf(GLASS())))
    override val resourcesHeld = mutableListOf<Resource>()
    override val maxResources = 0
}

object TestPlaceOfWorship: PlaceOfWorship {
    override val pieceName = "testPlaceOfWorship"
    override val text = "A test PlaceOfWorship"
    override val shape = Shape(listOf(listOf(GLASS())))
}

object TestRestaurant: Restaurant {
    override val pieceName = "testRestaurant"
    override val text = "A test Restaurant"
    override val shape = Shape(listOf(listOf(GLASS())))
}

object TestMonument: Monument {
    override val pieceName = "testMonument"
    override val text = "A test Monument"
    override val shape = Shape(listOf(listOf(GLASS())))
}
