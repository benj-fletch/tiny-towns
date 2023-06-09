package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.adjacentPieces
import com.benjfletch.tinytowns.model.buildings.BuildingType.*
import com.benjfletch.tinytowns.model.cornerSpaces
import com.benjfletch.tinytowns.model.score.AccumulativeScore
import com.benjfletch.tinytowns.model.score.IfAdjacentScore
import com.benjfletch.tinytowns.model.score.NotAdjacentScore
import com.benjfletch.tinytowns.model.score.SpecifiedPositionScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/** Super interface for all "Places of Worship" (Orange) implemented in the game */
interface PlaceOfWorship: Building {
    override val buildingType: BuildingType
        get() = PLACE_OF_WORSHIP
}

@Serializable
@SerialName("abbey")
data class Abbey(
    override val pieceName: String = "Abbey",
    override val text: String = "3 (point) if not adjacent to (GoodsHandler), (Restaurant) or (Shop) ",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), NONE(), GLASS()),
        listOf(BRICK(), STONE(), STONE())
    )),

    override val scoreWhenNotAdjacent: Int = 3,
    @Transient override val adjacentTypes: List<BuildingType> = listOf(GOODS_HANDLER, RESTAURANT, SHOP),
): PlaceOfWorship, NotAdjacentScore

@Serializable
@SerialName("cloister")
data class Cloister(
    override val pieceName: String = "Cloister",
    override val text: String = "1 (point) for each (PlaceOfWorship) in a corner of your town",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), NONE(), GLASS()),
        listOf(WOOD(), BRICK(), STONE()))),
): PlaceOfWorship, SpecifiedPositionScore {
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val scorePerBuilding = 1
        val cornerPieces = gameGrid.cornerSpaces()
        return scorePerBuilding * cornerPieces.count { PlaceOfWorship::class.isInstance(it.value) }
    }
}
@Serializable
@SerialName("chapel")
data class Chapel(
    override val pieceName: String = "Chapel",
    override val text: String = "1 (point) for each fed (Cottage).",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), NONE(), GLASS()),
        listOf(STONE(), GLASS(), STONE()))),
): PlaceOfWorship, AccumulativeScore {
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        return gameGrid.values
                .filterIsInstance<Cottage>()
                .count { it.isFed }
    }
}

@Serializable
@SerialName("temple")
data class Temple(
    override val pieceName: String = "Temple",
    override val text: String = "4 (point) if adjacent to 2 or more fed (Cottage). ",
    override val shape: Shape = Shape(listOf(
        listOf(NONE(), NONE(), GLASS()),
        listOf(BRICK(), BRICK(), STONE()))),

    @Transient override val adjacentTypes: List<BuildingType> = listOf(COTTAGE),
    override val scoreWhenAdjacent: Int = 4,
): PlaceOfWorship, IfAdjacentScore {
    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val fedCottages = gameGrid.adjacentPieces(pieceLocation)
                .filterIsInstance<Cottage>()
                .count { it.isFed }
        return when(fedCottages) {
            in 0..1 -> 0
            else -> scoreWhenAdjacent
        }
    }
}
