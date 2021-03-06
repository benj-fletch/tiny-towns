package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.buildings.monument.Monument
import com.benjfletch.tinytowns.model.centerSpaces
import com.benjfletch.tinytowns.model.col
import com.benjfletch.tinytowns.model.row
import com.benjfletch.tinytowns.model.score.IfAdjacentScore
import com.benjfletch.tinytowns.model.score.RowAndColumnScore
import com.benjfletch.tinytowns.model.score.RowOrColumnScore
import com.benjfletch.tinytowns.model.score.SpecifiedPositionScore

/** Super interface for all Shops (Yellow) implemented in the game */
interface Shop: Building

object Bakery: Shop, IfAdjacentScore {
    override val pieceName = "Bakery"
    override val text = "3 (Point) if adjacent to (FoodProducer) or (GoodsHandler)."
    override val shape = Shape(listOf(
            listOf(NONE, WHEAT, NONE),
            listOf(BRICK, GLASS, BRICK)))

    override val adjacentTypes = listOf(FoodProducer::class, GoodsHandler::class)
    override val scoreWhenAdjacent = 3
}

object Market: Shop, RowOrColumnScore {
    override val pieceName = "Market"
    override val text = "1 (point) for each (Shop) in the same row or column (not both) as (Shop)"
    override val shape = Shape(listOf(
            listOf(NONE, WOOD, NONE),
            listOf(STONE, GLASS, STONE)
    ))

    override val scorePerPiece = 1
    override val types = listOf(Shop::class)
}

object Tailor: Shop, SpecifiedPositionScore {
    override val pieceName = "Tailor"
    override val text = "1 (point). +1 (point) for each (Shop) in the 4 center squares in your town"
    override val shape = Shape(listOf(
            listOf(NONE, WHEAT, NONE),
            listOf(STONE, GLASS, STONE)))

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val baseScore = 1
        return baseScore + gameGrid.centerSpaces().count { Shop::class.isInstance(it.value) }
    }
}

object Theater: Shop, RowAndColumnScore {
    override val pieceName = "Theater"
    override val text = "1 (point) for each other unique building type in the same row and column as (Shop)."
    override val shape = Shape(listOf(
            listOf(NONE, STONE, NONE),
            listOf(WOOD, GLASS, WOOD)
    ))

    override val scorePerPiece = 1
    override val types = listOf(Cottage::class, Attraction::class, GoodsHandler::class,
            FoodProducer::class, PlaceOfWorship::class, Restaurant::class, Monument::class)

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val uniquePieces = gameGrid.row(pieceLocation).values
                .plus(gameGrid.col(pieceLocation).values)
                .distinct()
                .count { piece -> types.any { it.isInstance(piece) } }
        return uniquePieces * scorePerPiece
    }
}
