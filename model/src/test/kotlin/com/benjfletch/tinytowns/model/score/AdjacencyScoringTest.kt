package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import org.junit.jupiter.params.provider.Arguments

abstract class AdjacencyScoringTest: ScoringTest() {
    companion object {
        @JvmStatic
        fun fullAdjacencyParameters(toScore: Building, adjacentBuilding: Building, scores: List<Int>): List<Arguments> {
            return IntRange(0, 4)
                    .map {adjacencyParameters(toScore, it, adjacentBuilding, scores[it])}
                    .toList()
        }

        @JvmStatic
        fun adjacencyParameters(toScore: Building, numberOfAdjacent: Int, adjacentBuilding: Building, expectedScore: Int): Arguments {
            val board = Board(3)
            val centre = Location(2, 2)
            board.place(centre, toScore)

            (0 until numberOfAdjacent).forEach {
                board.place(centre.adjacent()[it], adjacentBuilding)
            }
            return Arguments.of(toScore, centre, board.gameGrid, expectedScore)
        }

        @JvmStatic
        fun adjacencyParameters(toScore: Building, expectedScore: Int, vararg adjacentBuilding: Building): Arguments {
            val board = Board(3)
            val centre = Location(2, 2)
            board.place(centre, toScore)
            placeAdjacent(board, centre, *adjacentBuilding)
            return Arguments.of(toScore, centre, board.gameGrid, expectedScore)
        }

        @JvmStatic
        fun placeAdjacent(board: Board, toPlaceAround: Location, vararg toPlace: Building) {
            toPlace.forEachIndexed { index, building ->
                board.place(toPlaceAround.adjacent()[index], building)
            }
        }
    }
}
