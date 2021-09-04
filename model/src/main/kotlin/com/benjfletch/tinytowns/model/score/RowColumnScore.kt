package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.col
import com.benjfletch.tinytowns.model.row
import kotlin.reflect.KClass

/**
 * Interface to represent a [ScoringPiece] who's score depends on the contents of specific rows and / or columns on the
 * board
 */
interface RowColumnScore: ScoringPiece {
    /** Collection of [Buildings][Building] which this [ScoringPiece] requires to be in rows / columns to score  */
    val types: List<KClass<out Building>>
}

/**
 * Specification of [RowColumnScore] which gives this [ScoringPiece] accumulative score based on [types] in the same
 * row OR column as this [ScoringPiece]
 */
interface RowOrColumnScore: RowColumnScore {
    /** Score per matching piece in this row OR column */
    val scorePerPiece: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val rowScore = gameGrid.row(pieceLocation)
                .count { piece -> types.any { it.isInstance(piece.value) } } * scorePerPiece
        val colScore = gameGrid.col(pieceLocation)
                .count { piece -> types.any { it.isInstance(piece.value) } } * scorePerPiece

        return maxOf(rowScore, colScore)
    }
}

/**
 * Specification of [RowColumnScore] which gives this [ScoringPiece] accumulative score based on [types] in the same
 * row AND column as this [ScoringPiece]
 */
interface RowAndColumnScore: RowColumnScore {
    /** Score per matching piece in this row AND column */
    val scorePerPiece: Int
}

/**
 * Specification of [RowColumnScore] which gives this [ScoringPiece] points only if it is not in a row or column with
 * any of [types]
 */
interface NotRowColumnScore: RowColumnScore {
    val scoreWhenNotInRowOrCol: Int

    override fun score(pieceLocation: Location, gameGrid: GameGrid, otherPlayerGrid: GameGrid?): Int {
        val isInRow = gameGrid.row(pieceLocation).minus(pieceLocation).any { (_, piece) -> types.any { it.isInstance(piece) } }
        val isInCol = gameGrid.col(pieceLocation).minus(pieceLocation).any { (_, piece) -> types.any { it.isInstance(piece) } }
        return when(isInRow || isInCol) {
            true -> 0
            false -> scoreWhenNotInRowOrCol
        }
    }
}
