package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.BuildingType
import com.benjfletch.tinytowns.model.score.AccumulativeTypeScore

class TheSkyBaths : Monument, AccumulativeTypeScore {
    override val pieceName = "The Sky Baths"
    override val text = "2 (Point) for each building type your town is missing."
    override val shape = Shape(
        listOf(
            listOf(NONE(), WHEAT(), NONE()),
            listOf(STONE(), GLASS(), WOOD()),
            listOf(BRICK(), NONE(), BRICK())
        )
    )

    override val types = enumValues<BuildingType>().asIterable()
    override val initialScore = 14
    override val scorePerType = -2
}