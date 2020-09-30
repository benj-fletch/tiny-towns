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

enum class Resource(override val pieceName: String): GamePiece {
    BRICK("brick"),
    GLASS("glass"),
    STONE("stone"),
    WOOD("wood"),
    WHEAT("wheat"),
    NONE("none"),
}
