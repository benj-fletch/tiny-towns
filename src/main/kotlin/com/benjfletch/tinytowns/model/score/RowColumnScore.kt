package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import kotlin.reflect.KClass

interface RowColumnScore: ScoringPiece {
    val types: List<KClass<out Building>>

    fun row(location: Location, pieces: Map<Location, GamePiece>) = pieces.filter { it.key.y == location.y }
    fun col(location: Location, pieces: Map<Location, GamePiece>) = pieces.filter { it.key.x == location.x }
}

interface RowOrColumnScore: RowColumnScore {
    val scorePerPiece: Int

    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        val rowScore = row(pieceLocation, pieces)
                .count { piece -> types.any { it.isInstance(piece.value) } } * scorePerPiece
        val colScore = col(pieceLocation, pieces)
                .count { piece -> types.any { it.isInstance(piece.value) } } * scorePerPiece

        return maxOf(rowScore, colScore)
    }
}
