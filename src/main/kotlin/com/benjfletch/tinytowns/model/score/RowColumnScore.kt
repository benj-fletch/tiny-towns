package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import kotlin.reflect.KClass

/**
 * Interface to represent a [ScoringPiece] who's score depends on the contents of specific rows and / or columns on the
 * board
 */
interface RowColumnScore: ScoringPiece {
    /** Collection of [Buildings][Building] which this [ScoringPiece] requires to be in rows / columns to score  */
    val types: List<KClass<out Building>>

    /** Helper method to get the contents of a row on the board that contains [location] */
    fun row(location: Location, pieces: Map<Location, GamePiece>) = pieces.filter { it.key.y == location.y }
    /** Helper method to get the contents of a column on the board that contains [location] */
    fun col(location: Location, pieces: Map<Location, GamePiece>) = pieces.filter { it.key.x == location.x }
}

/**
 * Specification of [RowColumnScore] which gives this [ScoringPiece] accumulative score based on [types] in the same
 * row OR column as this [ScoringPiece]
 */
interface RowOrColumnScore: RowColumnScore {
    /** Score per matching piece in this row OR column */
    val scorePerPiece: Int

    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val rowScore = row(pieceLocation, pieces)
                .count { piece -> types.any { it.isInstance(piece.value) } } * scorePerPiece
        val colScore = col(pieceLocation, pieces)
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
