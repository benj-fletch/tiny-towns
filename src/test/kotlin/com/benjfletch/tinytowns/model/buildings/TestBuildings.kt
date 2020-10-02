package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WOOD
import com.benjfletch.tinytowns.model.Shape

object TestBuilding: Building {
    val resourceMatrix = listOf(listOf(WOOD, STONE), listOf(NONE, GLASS))

    override val pieceName = "test"
    override val text = "A test building"
    override val canBeBuiltAnywhere = false
    override val shape = Shape(resourceMatrix)
}

object TestAnywhereBuilding: Building {
    override val pieceName = "anywhereBuilding"
    override val text = ""
    override val shape = Shape(listOf(listOf(GLASS)))
    override val canBeBuiltAnywhere = true
}

object TestAttraction: Attraction {
    override val pieceName = "testAttraction"
    override val text = "A test attraction"
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = false
}

object TestShop: Shop {
    override val pieceName = "testShop"
    override val text = "A test shop"
    override val shape = Shape(listOf(listOf(GLASS)))
    override val canBeBuiltAnywhere = false
}

object TestFoodProducer: FoodProducer {
    override val pieceName = "testFoodProducer"
    override val text = "A test food producer"
    override val shape = Shape(listOf(listOf(GLASS)))
    override val canBeBuiltAnywhere = false
}

object TestGoodsHandler: GoodsHandler {
    override val pieceName = "testGoodsHandler"
    override val text = "A test goods handler"
    override val shape = Shape(listOf(listOf(GLASS)))
    override val canBeBuiltAnywhere = false
}
