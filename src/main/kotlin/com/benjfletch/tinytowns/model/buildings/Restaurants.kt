package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.score.AccumulativeConstructedScore
import com.benjfletch.tinytowns.model.score.NotRowColumnScore

/** Super interface for all Restaurants (Green) implemented in the game */
interface Restaurant: Building

object Inn: Restaurant, NotRowColumnScore {
    override val pieceName = "Inn"
    override val text = "3 (point) if not in a row or column with another (Restaurant)."
    override val shape = Shape(listOf(
            listOf(WHEAT, STONE, WOOD)))

    override val scoreWhenNotInRowOrCol = 3
    override val types = listOf(Restaurant::class)
}

object Tavern: Restaurant, AccumulativeConstructedScore {
    override val pieceName = "Tavern"
    override val text = "(point) based on your constructed (Restaurant)."
    override val shape = Shape(listOf(
            listOf(BRICK, BRICK, GLASS)))

    override val types = listOf(Restaurant::class)
    override val scores = mapOf(0 to 0, 1 to 2, 3 to 9, 4 to 14, 5 to 20)
    override val maxScore = 20
}

object Almshouse: Restaurant, AccumulativeConstructedScore {
    override val pieceName = "Almshouse"
    override val text = "{point} based on your constructed (Restaurant)"
    override val shape = Shape(listOf(
            listOf(STONE, STONE, GLASS)))

    override val types = listOf(Restaurant::class)
    override val scores = mapOf(0 to 0, 1 to -1, 2 to 5, 3 to -3, 4 to 15, 5 to -5, 6 to 26)
    override val maxScore = 26
}
