package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location

interface ScoringPiece : GamePiece {
    fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int
}
