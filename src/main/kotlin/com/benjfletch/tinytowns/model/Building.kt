package com.benjfletch.tinytowns.model

/** Specification of [GamePiece] for a constructed building. */
interface Building: GamePiece {
    /** Name of the building */
    override val pieceName: String
    /** Effect text on the card */
    val text: String
    /** Required resource configuration to build */
    val shape: Shape
    /** Determines whether the building can be built anywhere*/
    val canBeBuiltAnywhere: Boolean
}
