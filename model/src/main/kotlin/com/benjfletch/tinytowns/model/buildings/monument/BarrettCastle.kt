package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.Cottage
import com.benjfletch.tinytowns.model.buildings.FeedableBuilding
import com.benjfletch.tinytowns.model.mutational.CountsAs
import com.benjfletch.tinytowns.model.score.IfFedScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("barrettCastle")
abstract class BarrettCastle : Monument, FeedableBuilding, IfFedScore, CountsAs {
    override val pieceName = "Barrett Castle"
    override val text = "5 (Points) if fed. Counts as 2 (Cottage)"
    override val shape = Shape(
        listOf(
            listOf(WHEAT(), NONE(), NONE(), STONE()),
            listOf(WOOD(), GLASS(), GLASS(), BRICK())
        )
    )

    override val scoreWhenFed = 5

    override val countsAs = Cottage::class
    override val countsAsAmount = 2
}

@Serializable
@SerialName("fedBarrettCastle")
data class FedBarrettCastle(override val isFed: Boolean = true): BarrettCastle()

@Serializable
@SerialName("unfedBarrettCastle")
data class UnfedBarrettCastle(override val isFed: Boolean = false): BarrettCastle()