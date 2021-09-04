package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.col
import com.benjfletch.tinytowns.model.row
import org.junit.jupiter.params.provider.Arguments

abstract class RowColumnScoringTest: ScoringTest() {
    companion object {
        @JvmStatic
        fun fullRowColParameters(toScore: RowColumnScore, rowColBuilding: Building, scores: List<Int>): List<Arguments> {
            val boardSize = 3
            val rowOnly = (0 until boardSize)
                    .map { rowColumnParameters(boardSize, toScore, rowColBuilding, it, 0, scores[it]) }

            val colOnly = (0 until boardSize)
                    .map { rowColumnParameters(boardSize, toScore, rowColBuilding, 0, it, scores[it]) }

            val rowAndCol = (0 until boardSize)
                    .map { rowColumnParameters(boardSize, toScore, rowColBuilding, it, it, scores[it]) }
            return rowOnly.plus(colOnly).plus(rowAndCol)
        }

        @JvmStatic
        fun rowColumnParameters(boardSize: Int, toScore: RowColumnScore, toPlaceInRow: Building, numberInRow: Int, numberInCol: Int, expectedScore: Int): Arguments {
            val board = Board(boardSize)
            val centre = Location(2, 2)
            board.place(centre, toScore)

            (0 until numberInRow).forEach {
                val loc = board.gameGrid.row(centre).keys
                        .filter { loc -> loc != centre }
                        .toList()[it]
                board.place(loc, toPlaceInRow)
            }
            (0 until numberInCol).forEach {
                val loc = board.gameGrid.col(centre).keys
                        .filter { loc -> loc != centre }
                        .toList()[it]
                board.place(loc, toPlaceInRow)
            }
            return Arguments.of(toScore, centre, board.gameGrid, expectedScore)
        }
    }
}
