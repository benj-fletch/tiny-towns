package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.MutableGameGrid
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.col
import com.benjfletch.tinytowns.model.contiguousGroupsOf
import com.benjfletch.tinytowns.model.row
import com.benjfletch.tinytowns.model.surroundingSpaces
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Super interface for all "Food Producers" (Red) implemented in the game */
interface FoodProducer : Building, Feeder

@Serializable
@SerialName("orchard")
data class Orchard(
    override val pieceName: String = "Orchard",
    override val text: String = "Feeds all (FeedableBuilding) in the same row and column as (FoodProducer).",
    override val shape: Shape = Shape(
        listOf(
            listOf(STONE(), WHEAT()),
            listOf(WHEAT(), WOOD())))
) : FoodProducer {
    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        gameGrid.row(location)
            .plus(gameGrid.col(location))
            .filterValues { it is UnfedCottage }
            .forEach { (loc, _) -> gameGrid[loc] = FedCottage() }
    }
}

@Serializable
@SerialName("granary")
data class Granary(
    override val pieceName: String = "Granary",
    override val text: String = "Feeds all (FeedableBuilding) buildings in the 8 squares surrounding (FoodProducer).",
    override val shape: Shape = Shape(
        listOf(
            listOf(WHEAT(), WHEAT()),
            listOf(WOOD(), BRICK())))
) : FoodProducer {
    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        gameGrid.surroundingSpaces(location)
            .filterValues { it is UnfedCottage }
            .forEach { (loc, _) -> gameGrid[loc] = FedCottage() }
    }
}

@Serializable
@SerialName("farm")
data class Farm(
    override val pieceName: String = "Farm",
    override val text: String = "Feeds 4 (FeedableBuilding) buildings anywhere in your town.",
    override val shape: Shape = Shape(
        listOf(
            listOf(WHEAT(), WHEAT()),
            listOf(WOOD(), WOOD())))
) : FoodProducer {
    /**
     * TODO this will feed the "first" 4 unfed [FeedableBuilding]s starting bottom left. This might not be optimal for scoring purposes.
     */
    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        gameGrid.filterValues { it is UnfedCottage }
            .keys.take(4)
            .forEach { gameGrid[it] = FedCottage() }
    }
}

@Serializable
@SerialName("greenhouse")
data class Greenhouse(
    override val pieceName: String = "Greenhouse",
    override val text: String = "Feeds 1 contiguous group of (FeedableBuilding) buildings anywhere in your town.",
    override val shape: Shape = Shape(
        listOf(
            listOf(WHEAT(), GLASS()),
            listOf(WOOD(), WOOD())))
) : FoodProducer {
    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        val contiguousUnfedCottages = gameGrid.contiguousGroupsOf(UnfedCottage())
        contiguousUnfedCottages.map { it.count() to it }
            .sortedBy { (count, _) -> count }
            .takeLast(1)
            .flatMap { (_, cottageLocs) -> cottageLocs }
            .forEach { loc -> gameGrid[loc.toString()] = FedCottage() }
    }
}
