package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location

interface PositionalScore : ScoringPiece

interface SpecifiedPositionScore : PositionalScore {
    fun centralSpaces(pieces: Map<Location, GamePiece>): Map<Location, GamePiece> {
        val minLocation = pieces.keys.minOrNull()
        val maxLocation = pieces.keys.maxOrNull()
        return pieces
                .filter { it.key.x != minLocation?.x && it.key.y != minLocation?.y }
                .filter { it.key.x != maxLocation?.x && it.key.y != maxLocation?.y }
    }
}
