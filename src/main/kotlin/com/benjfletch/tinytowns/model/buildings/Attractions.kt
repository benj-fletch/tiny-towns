package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WOOD
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.score.AccumulativeAdjacencyScore
import com.benjfletch.tinytowns.model.score.FlatScore
import com.benjfletch.tinytowns.model.score.IfAdjacentScore

interface Attraction: Building

object Fountain: Attraction, IfAdjacentScore {
    override val pieceName = "Fountain"
    override val text = "2 (Point) if adjacent to a (Attraction)."
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = false

    override val adjacentTypes = listOf(Attraction::class)
    override val scoreWhenAdjacent = 2
}

object Millstone: Attraction, IfAdjacentScore {
    override val pieceName = "Millstone"
    override val text = "2 (Point) if adjacent to a (FoodProducer) or (Shop)."
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = false

    override val adjacentTypes = listOf(FoodProducer::class, Shop::class)
    override val scoreWhenAdjacent = 2
}

object Shed: Attraction, FlatScore {
    override val pieceName = "Shed"
    override val text = "1 (Point). May be constructed on any empty square in your town"
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = true

    override val score = 1
}

object Well: Attraction, AccumulativeAdjacencyScore {
    override val pieceName = "Well"
    override val text = "1 (Point) for each adjacent (Cottage)"
    override val shape = Shape(listOf(listOf(WOOD, STONE)))
    override val canBeBuiltAnywhere = false

    override val adjacentTypes = listOf(Cottage::class)
    override val scorePerAdjacent = 1
}
