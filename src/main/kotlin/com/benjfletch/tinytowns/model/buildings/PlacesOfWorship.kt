package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.adjacentPieces
import com.benjfletch.tinytowns.model.cornerSpaces
import com.benjfletch.tinytowns.model.score.AccumulativeScore
import com.benjfletch.tinytowns.model.score.IfAdjacentScore
import com.benjfletch.tinytowns.model.score.NotAdjacentScore
import com.benjfletch.tinytowns.model.score.SpecifiedPositionScore

interface PlaceOfWorship: Building

object Abbey: PlaceOfWorship, NotAdjacentScore {
    override val pieceName = "Abbey"
    override val text = "3 (point) if not adjacent to (GoodsHandler), (Restaurant) or (Shop) "
    override val shape = Shape(listOf(
            listOf(NONE, NONE, GLASS),
            listOf(BRICK, STONE, STONE)
    ))

    override val scoreWhenNotAdjacent = 3
    override val adjacentTypes = listOf(GoodsHandler::class, Restaurant::class, Shop::class)
}

object Cloister: PlaceOfWorship, SpecifiedPositionScore {
    override val pieceName = "Cloister"
    override val text = "1 (point) for each (PlaceOfWorship) in a corner of your town"
    override val shape = Shape(listOf(
            listOf(NONE, NONE, GLASS),
            listOf(WOOD, BRICK, STONE)))

    override fun score(pieceLocation: Location, gameGrid: GameGrid): Int {
        val scorePerBuilding = 1
        val cornerPieces = gameGrid.cornerSpaces()
        return scorePerBuilding * cornerPieces.count { PlaceOfWorship::class.isInstance(it.value) }
    }
}

object Chapel: PlaceOfWorship, AccumulativeScore {
    override val pieceName = "Chapel"
    override val text = "1 {point) for each fed (Cottage)."
    override val shape = Shape(listOf(
            listOf(NONE, NONE, GLASS),
            listOf(STONE, GLASS, STONE)))

    override fun score(pieceLocation: Location, gameGrid: GameGrid): Int {
        return gameGrid.values
                .filterIsInstance<Cottage>()
                .count { it.isFed }
    }
}

object Temple: PlaceOfWorship, IfAdjacentScore {
    override val pieceName = "Temple"
    override val text = "4 (point) if adjacent to 2 or more fed (Cottage). "
    override val shape = Shape(listOf(
            listOf(NONE, NONE, GLASS),
            listOf(BRICK, BRICK, STONE)))

    override val adjacentTypes = listOf(Cottage::class)
    override val scoreWhenAdjacent = 4

    override fun score(pieceLocation: Location, gameGrid: GameGrid): Int {
        val fedCottages = gameGrid.adjacentPieces(pieceLocation)
                .filterIsInstance<Cottage>()
                .count { it.isFed }
        return when(fedCottages) {
            in 0..2 -> 0
            else -> scoreWhenAdjacent
        }
    }
}
