package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Resource.WOOD
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.score.IfAdjacentScore
import com.benjfletch.tinytowns.model.score.RowOrColumnScore

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

object Market: Shop, RowOrColumnScore {
    override val pieceName = "Market"
    override val text = "1 (point) for each (Shop) in the same row or column (not both) as (Shop)"
    override val canBeBuiltAnywhere = false
    override val shape = Shape(listOf(
            listOf(NONE, WOOD, NONE),
            listOf(STONE, GLASS, STONE)
    ))

    override val scorePerPiece = 1
    override val types = listOf(Shop::class)
}
