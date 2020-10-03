package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Bakery
import com.benjfletch.tinytowns.model.buildings.Cottage
import com.benjfletch.tinytowns.model.buildings.Market
import com.benjfletch.tinytowns.model.buildings.Tailor
import com.benjfletch.tinytowns.model.buildings.TestAttraction
import com.benjfletch.tinytowns.model.buildings.TestFoodProducer
import com.benjfletch.tinytowns.model.buildings.TestGoodsHandler
import com.benjfletch.tinytowns.model.buildings.TestMonument
import com.benjfletch.tinytowns.model.buildings.TestPlaceOfWorship
import com.benjfletch.tinytowns.model.buildings.TestRestaurant
import com.benjfletch.tinytowns.model.buildings.TestShop
import com.benjfletch.tinytowns.model.buildings.Theater
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class BakeryScoringTest : AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(0, 3, 3, 3, 3)
            val foodProducerAdjacent = fullAdjacencyParameters(Bakery, TestFoodProducer, scores)
            val goodsHandlerAdjacent = fullAdjacencyParameters(Bakery, TestGoodsHandler, scores)
            val fPAndGHAdjacent = adjacencyParameters(Bakery, 3, TestGoodsHandler, TestFoodProducer)
            return foodProducerAdjacent.plus(goodsHandlerAdjacent).plusElement(fPAndGHAdjacent).stream()
        }
    }
}

class MarketScoringTest : RowColumnScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(1, 2, 3, 1, 2, 3, 1, 2, 3)
            return fullRowColParameters(Market, TestShop, scores).stream()
        }
    }
}

class TailorScoringTest : ScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val arguments = mutableListOf<Arguments>()
            val origin = Location(1, 1)
            val centreLocations = listOf(
                    Location(2, 2),
                    Location(2, 3),
                    Location(3, 2),
                    Location(3, 3))

            val emptyBoard = Board()
            emptyBoard.place(origin, Tailor)
            arguments.add(Arguments.of(Tailor, origin, emptyBoard.spaces, 1))

            val statefulBoard = Board()
            statefulBoard.place(origin, Tailor)

            centreLocations.forEachIndexed { index, location ->
                val board = Board()
                statefulBoard.place(location, TestShop)
                board.spaces.putAll(statefulBoard.spaces)
                arguments.add(Arguments.of(Tailor, origin, board.spaces, index + 1 + 1))
            }
            return arguments.stream()
        }
    }
}

class TheaterScoringTest : RowColumnScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val arguments = mutableListOf<Arguments>()
            val origin = Location(1, 1)
            val rowAndColLocations = listOf(
                    Location(1, 2),
                    Location(1, 3),
                    Location(1, 4),
                    Location(2, 1),
                    Location(3, 1),
                    Location(4, 1),
            )
            val types = listOf(Cottage, TestAttraction, TestGoodsHandler, TestFoodProducer,
                    TestPlaceOfWorship, TestRestaurant, TestMonument)

            val emptyBoard = Board()
            emptyBoard.place(origin, Theater)
            arguments.add(Arguments.of(Theater, origin, emptyBoard.spaces, 0))

            val statefulBoard = Board()
            statefulBoard.place(origin, Theater)

            rowAndColLocations.forEachIndexed { index, location ->
                val board = Board()
                statefulBoard.place(location, types[index])
                board.spaces.putAll(statefulBoard.spaces)
                arguments.add(Arguments.of(Theater, origin, board.spaces, index + 1))
            }
            return arguments.stream()
        }
    }
}
