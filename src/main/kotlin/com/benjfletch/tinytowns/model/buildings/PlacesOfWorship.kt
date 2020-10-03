package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource
import com.benjfletch.tinytowns.model.Resource.*
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.score.NotAdjacentScore
import com.benjfletch.tinytowns.model.score.SpecifiedPositionScore

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

object Cloister: PlaceOfWorship, SpecifiedPositionScore {
    override val pieceName = "Cloister"
    override val text = "1 (point) for each (PlaceOfWorship) in a corner of your town"
    override val canBeBuiltAnywhere = false
    override val shape = Shape(listOf(
            listOf(NONE, NONE, GLASS),
            listOf(WOOD, BRICK, STONE)))

    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val scorePerBuilding = 1
        val cornerPieces = cornerSpaces(pieces)
        return scorePerBuilding * cornerPieces.count { PlaceOfWorship::class.isInstance(it.value) }
    }
}
