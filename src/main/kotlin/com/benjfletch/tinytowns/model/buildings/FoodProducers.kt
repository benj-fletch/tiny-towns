package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.MutableGameGrid
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.col
import com.benjfletch.tinytowns.model.contiguousGroupsOf
import com.benjfletch.tinytowns.model.row
import com.benjfletch.tinytowns.model.surroundingSpaces

/** Super interface for all "Food Producers" (Red) implemented in the game */
interface FoodProducer: Building, Feeder

object Orchard: FoodProducer {
    override val pieceName = "Orchard"
    override val text = "Feeds all (FeedableBuilding) in the same row and column as (FoodProducer)."
    override val shape = Shape(listOf(
            listOf(STONE, WHEAT),
            listOf(WHEAT, WOOD)))

    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        gameGrid.row(location)
                .plus(gameGrid.col(location))
                .filterValues { it is Cottage.Unfed }
                .forEach { (loc, _) -> gameGrid[loc] = Cottage.Fed }
    }
}

object Granary: FoodProducer {
    override val pieceName = "Granary"
    override val text = "Feeds all (FeedableBuilding) buildings in the 8 squares surrounding (FoodProducer)."
    override val shape = Shape(listOf(
            listOf(WHEAT, WHEAT),
            listOf(WOOD, BRICK)))

    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        gameGrid.surroundingSpaces(location)
                .filterValues { it is Cottage.Unfed }
                .forEach { (loc, _) -> gameGrid[loc] = Cottage.Fed }
    }
}

object Farm: FoodProducer {
    override val pieceName = "Farm"
    override val text = "Feeds 4 (FeedableBuilding) buildings anywhere in your town."
    override val shape = Shape(listOf(
            listOf(WHEAT, WHEAT),
            listOf(WOOD, WOOD)))

    /**
     * TODO this will feed the "first" 4 unfed [FeedableBuilding]s starting bottom left. This might not be optimal for scoring purposes.
     */
    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        gameGrid.filterValues { it is Cottage.Unfed }
                .keys.take(4)
                .forEach { gameGrid[it] = Cottage.Fed }
    }
}

object Greenhouse: FoodProducer {
    override val pieceName = "Greenhouse"
    override val text = "Feeds 1 contiguous group of (FeedableBuilding) buildings anywhere in your town."
    override val shape = Shape(listOf(
            listOf(WHEAT, GLASS),
            listOf(WOOD, WOOD)))

    override fun feed(location: Location, gameGrid: MutableGameGrid) {
        val contiguousUnfedCottages = gameGrid.contiguousGroupsOf(Cottage.Unfed)
        contiguousUnfedCottages.map { it.count() to it }
                .sortedBy { (count, _) ->  count }
                .takeLast(1)
                .flatMap { (_, cottageLocs) -> cottageLocs }
                .forEach { loc -> gameGrid[loc] = Cottage.Fed }
    }
}
