package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.BuildingType
import com.benjfletch.tinytowns.model.score.AccumulativeTypeScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("archiveOfTheSecondAge")
class ArchiveOfTheSecondAge : Monument, AccumulativeTypeScore {
    override val pieceName = "Archive of the second Age"
    override val text = "1 (Point) for each unique building type (other than (Monument)) in your town"
    override val shape = Shape(
        listOf(
            listOf(WHEAT(), WHEAT()),
            listOf(BRICK(), GLASS())
        )
    )

    override val types = enumValues<BuildingType>()
        .asIterable()
        .filter { it !== BuildingType.MONUMENT }
    override val initialScore = 0
    override val scorePerType = 1
}