package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException

/** Container for the status of a given Location on the [Board] */
data class Content(val gamePiece: GamePiece?) {
    companion object {
        val NONE = Content(null)
    }
}

/** Representation of the game board, with a configurable size */
data class Board(private val size: Int = 4) {
    val locations: Map<Pair<Int, Int>, Content>

    init {
        if(size < 1) throw BoardException("Board size $size is invalid. Must be > 1.")
        locations = (1..size)
                .flatMap { x -> (1..size).map { y -> x to y } }
                .map { it to Content.NONE }
                .toMap()
    }
}
