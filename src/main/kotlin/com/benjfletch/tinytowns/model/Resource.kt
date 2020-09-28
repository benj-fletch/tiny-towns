package com.benjfletch.tinytowns.model

enum class Resource(override val pieceName: String): GamePiece {
    BRICK("brick"),
    GLASS("glass"),
    STONE("stone"),
    WOOD("wood"),
    WHEAT("wheat"),
    NONE("none"),
}
