package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.buildings.monument.Monument
import com.benjfletch.tinytowns.model.centerSpaces
import com.benjfletch.tinytowns.model.col
import com.benjfletch.tinytowns.model.row
import com.benjfletch.tinytowns.model.score.IfAdjacentScore
import com.benjfletch.tinytowns.model.score.RowAndColumnScore
import com.benjfletch.tinytowns.model.score.RowOrColumnScore
import com.benjfletch.tinytowns.model.score.SpecifiedPositionScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KClass

/** Super interface for all Shops (Yellow) implemented in the game */
interface Shop: Building {
    override val buildingType: BuildingType
        get() = BuildingType.SHOP
}

@Serializable
@SerialName("bakery")
data class Bakery(
    override val pieceName: String = "Bakery",
    override val text: String = "3 (Point) if adjacent to (FoodProducer) or (GoodsHandler).",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), WHEAT(), NONE()),
        listOf(BRICK(), GLASS(), BRICK()))),

    @Transient override val adjacentTypes: List<KClass<out Building>> = listOf(FoodProducer::class, GoodsHandler::class),
    override val scoreWhenAdjacent: Int = 3,
): Shop, IfAdjacentScore

@Serializable
@SerialName("market")
data class Market(
    override val pieceName: String = "Market",
    override val text: String = "1 (point) for each (Shop) in the same row or column (not both) as (Shop)",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), WOOD(), NONE()),
        listOf(STONE(), GLASS(), STONE())
    )),

    override val scorePerPiece: Int = 1,
    @Transient override val types: List<KClass<Shop>> = listOf(Shop::class),
): Shop, RowOrColumnScore

@Serializable
@SerialName("tailor")
data class Tailor(
    override val pieceName: String = "Tailor",
    override val text: String = "1 (point). +1 (point) for each (Shop) in the 4 center squares in your town",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), WHEAT(), NONE()),
        listOf(STONE(), GLASS(), STONE()))),
): Shop, SpecifiedPositionScore {
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val baseScore = 1
        return baseScore + gameGrid.centerSpaces().count { Shop::class.isInstance(it.value) }
    }
}

@Serializable
@SerialName("theater")
data class Theater(
    override val pieceName: String = "Theater",
    override val text: String = "1 (point) for each other unique building type in the same row and column as (Shop).",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), STONE(), NONE()),
        listOf(WOOD(), GLASS(), WOOD())
    )),

    override val scorePerPiece: Int = 1,
    @Transient override val types: List<KClass<out Building>> = listOf(Cottage::class, Attraction::class,
        GoodsHandler::class, FoodProducer::class, PlaceOfWorship::class, Restaurant::class, Monument::class),
): Shop, RowAndColumnScore {
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val uniquePieces = gameGrid.row(pieceLocation).values
                .plus(gameGrid.col(pieceLocation).values)
                .distinct()
                .count { piece -> types.any { it.isInstance(piece) } }
        return uniquePieces * scorePerPiece
    }
}
