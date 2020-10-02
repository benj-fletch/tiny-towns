package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Resource.WOOD
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.buildings.monument.Monument
import com.benjfletch.tinytowns.model.score.IfAdjacentScore
import com.benjfletch.tinytowns.model.score.RowAndColumnScore
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

object Theater: Shop, RowAndColumnScore {
    override val pieceName = "Theater"
    override val text = "1 (point) for each other unique building type in the same row and column as (Shop)."
    override val shape = Shape(listOf(
            listOf(NONE, STONE, NONE),
            listOf(WOOD, GLASS, WOOD)
    ))
    override val canBeBuiltAnywhere = false

    override val scorePerPiece = 1
    override val types = listOf(Cottage::class, Attraction::class, GoodsHandler::class,
            FoodProducer::class, PlaceOfWorship::class, Restaurant::class, Monument::class)

    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val uniquePieces = row(pieceLocation, pieces).values
                .plus(col(pieceLocation, pieces).values)
                .distinct()
                .count { piece -> types.any { it.isInstance(piece) } }
        return uniquePieces * scorePerPiece
    }
}
