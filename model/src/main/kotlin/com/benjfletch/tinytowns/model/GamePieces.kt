package com.benjfletch.tinytowns.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Ultimate parent for anything that can be placed on the [Board] */
interface GamePiece {
    /** Name of the [GamePiece] */
    val pieceName: String
}

/** Represents a space which is not occupied by any [GamePiece] */
@Serializable
@SerialName("empty")
data class EmptySpace(override val pieceName: String = "empty"): GamePiece
