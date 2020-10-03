package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location

/**
 * Interface to represent a [ScoringPiece] which is dependant on the positions of other [Buildings][Building] on the
 * board.
 */
interface PositionalScore : ScoringPiece

/**
 * Specification of [PositionalScore] where the contents of specific [Locations][Location] on the board must be occupied
 * by specific [GamePieces][GamePiece] for this [ScoringPiece] to score.
 */
interface SpecifiedPositionScore : PositionalScore {
    /** Helper method which determines the central [Locations][Location] in a given Map of Locations. */
    fun centralSpaces(pieces: Map<Location, GamePiece>): Map<Location, GamePiece> {
        val minLocation = pieces.keys.minOrNull()
        val maxLocation = pieces.keys.maxOrNull()
        return pieces
                .filter { it.key.x != minLocation?.x && it.key.y != minLocation?.y }
                .filter { it.key.x != maxLocation?.x && it.key.y != maxLocation?.y }
    }
}
