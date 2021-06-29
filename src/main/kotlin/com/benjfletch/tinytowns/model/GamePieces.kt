package com.benjfletch.tinytowns.model

/** Ultimate parent for anything that can be placed on the [Board] */
interface GamePiece {
    /** Name of the [GamePiece] */
    val pieceName: String
}

/** Represents a space which is not occupied by any [GamePiece] */
object EmptySpace: GamePiece {
    override val pieceName = "empty"
}
