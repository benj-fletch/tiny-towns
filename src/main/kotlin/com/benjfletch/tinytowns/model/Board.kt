package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException

/** Representation of the game board, with a configurable size */
data class Board(private val size: Int = 4) {
    val locations: MutableMap<Location, GamePiece?> = mutableMapOf()

    init {
        if(size < 1) throw BoardException("Board size $size is invalid. Must be > 1.")
        (0 until size)
                .flatMap { x -> (0 until size).map { y -> Location(x, y) } }
                .map { it to null }
                .toMap(locations)
    }

    /**
     * Place a [GamePiece] on to the board. This is only valid if:
     *
     * * [location] is a valid space on the board
     * * [location] is not already occupied by a [GamePiece]
     *
     * in which case a [BoardException] is thrown.
     */
    fun place(location: Location, piece: GamePiece) {
        if(!locations.containsKey(location)) {
            throw BoardException("Cannot place ${piece.pieceName} at $location. Out of bounds.")
        }
        if(locations[location] != null) {
            throw BoardException("Cannot place ${piece.pieceName} at $location. Place occupied.")
        }

        locations[location] = piece
    }
}
