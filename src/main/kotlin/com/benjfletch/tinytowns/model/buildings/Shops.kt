package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.score.IfAdjacentScore

interface Shop: Building

object Bakery: Shop, IfAdjacentScore {
    override val pieceName = "Bakery"
    override val text = "3 (Point) if adjacent to (FoodProducer) or (GoodsHandler)."
    override val canBeBuiltAnywhere = false
    override val shape = Shape(listOf(
            listOf(NONE, WHEAT, NONE),
            listOf(BRICK, GLASS, BRICK)))

    override val adjacentTypes = listOf(FoodProducer::class, GoodsHandler::class)
    override val scoreWhenAdjacent = 3
}
