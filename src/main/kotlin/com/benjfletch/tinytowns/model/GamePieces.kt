package com.benjfletch.tinytowns.model

/** Ultimate parent for anything that can be placed on the [Board] */
interface GamePiece {
    /** Name of the [GamePiece] */
    val pieceName: String
}

data class EmptySpace(override val pieceName: String = "empty"): GamePiece

enum class Resource(override val pieceName: String): GamePiece {
    BRICK("brick"),
    GLASS("glass"),
    STONE("stone"),
    WOOD("wood"),
    WHEAT("wheat"),
    NONE("none"),
}
