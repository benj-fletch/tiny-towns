package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.BuildingType
import com.benjfletch.tinytowns.model.score.UniqueAdjacencyScore
import kotlinx.serialization.Serializable

@Serializable
class MandrasPalace: Monument, UniqueAdjacencyScore {
    override val pieceName = "Mandras Palace"
    override val text = "2 (Point) for each unique adjacent building type."
    override val shape = Shape(
        listOf(
            listOf(WHEAT(), GLASS()),
            listOf(BRICK(), WOOD())
        )
    )

    override val adjacentTypes = enumValues<BuildingType>().filter { it !== BuildingType.MONUMENT }.asIterable()
    override val scorePerAdjacent = 2
}