package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.score.NotAdjacentScore

interface PlaceOfWorship: Building

object Abbey: PlaceOfWorship, NotAdjacentScore {
    override val pieceName = "Abbey"
    override val text = "3 (point) if not adjacent to (GoodsHandler), (Restaurant) or (Shop) "
    override val canBeBuiltAnywhere = false
    override val shape = Shape(listOf(
            listOf(NONE, NONE, GLASS),
            listOf(BRICK, STONE, STONE)
    ))

    override val scoreWhenNotAdjacent = 3
    override val adjacentTypes = listOf(GoodsHandler::class, Restaurant::class, Shop::class)
}
