package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Abbey
import com.benjfletch.tinytowns.model.buildings.Chapel
import com.benjfletch.tinytowns.model.buildings.Cloister
import com.benjfletch.tinytowns.model.buildings.FedCottage
import com.benjfletch.tinytowns.model.buildings.Temple
import com.benjfletch.tinytowns.model.buildings.TestGoodsHandler
import com.benjfletch.tinytowns.model.buildings.TestPlaceOfWorship
import com.benjfletch.tinytowns.model.buildings.TestRestaurant
import com.benjfletch.tinytowns.model.buildings.TestShop
import com.benjfletch.tinytowns.model.buildings.UnfedCottage
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class AbbeyScoringTest : AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(3, 0, 0, 0, 0)

            val goodsHandlerAdjacent = fullAdjacencyParameters(Abbey(), TestGoodsHandler, scores)
            val restaurantAdjacent = fullAdjacencyParameters(Abbey(), TestRestaurant, scores)
            val shopAndGHAdjacent = adjacencyParameters(Abbey(), 0, TestGoodsHandler, TestShop)

            return restaurantAdjacent.plus(goodsHandlerAdjacent).plusElement(shopAndGHAdjacent).stream()
        }
    }
}

class CloisterScoringTest : ScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val arguments = mutableListOf<Arguments>()
            val randomSpace = Location(2, 3)
            val cornerLocations = listOf(
                    Location(1, 1),
                    Location(1, 4),
                    Location(4, 1),
                    Location(4, 4))

            val emptyBoard = Board()
            emptyBoard.place(randomSpace, Cloister())
            arguments.add(Arguments.of(Cloister(), randomSpace, emptyBoard.gameGrid, 0))

            val statefulBoard = Board()
            statefulBoard.place(randomSpace, Cloister())

            cornerLocations.forEachIndexed { index, location ->
                val board = Board()
                statefulBoard.place(location, TestPlaceOfWorship)
                board.gameGrid.putAll(statefulBoard.gameGrid)
                arguments.add(Arguments.of(Cloister(), randomSpace, board.gameGrid, index + 1))
            }
            return arguments.stream()
        }
    }
}

class ChapelScoringTest : ScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val arguments = mutableListOf<Arguments>()
            val randomSpace = Location(2, 3)
            val fedCottageLocations = listOf(
                    Location(1, 1),
                    Location(1, 2),
                    Location(3, 1),
                    Location(4, 4))

            val emptyBoard = Board()
            emptyBoard.place(randomSpace, Chapel())
            arguments.add(Arguments.of(Chapel(), randomSpace, emptyBoard.gameGrid, 0))

            val statefulBoard = Board()
            statefulBoard.place(randomSpace, Chapel())

            fedCottageLocations.forEachIndexed { index, location ->
                val board = Board()
                statefulBoard.place(location, FedCottage())
                board.gameGrid.putAll(statefulBoard.gameGrid)
                arguments.add(Arguments.of(Chapel(), randomSpace, board.gameGrid, index + 1))
            }
            return arguments.stream()
        }
    }
}

class TempleScoringTest : AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val fedScores = listOf(0, 0, 4, 4, 4)
            val fedCottagesAdjacent = fullAdjacencyParameters(Temple(), FedCottage(), fedScores)

            val unfedScores = listOf(0, 0, 0, 0, 0)
            val unfedCottagesAdjacent = fullAdjacencyParameters(Temple(), UnfedCottage(), unfedScores)

            return fedCottagesAdjacent.plus(unfedCottagesAdjacent).stream()
        }
    }
}
