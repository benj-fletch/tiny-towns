package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location

interface FlatScore : ScoringPiece {
    val score: Int
    override fun score(pieceLocation: Location, pieces: Map<Location, GamePiece>): Int {
        return score
    }
}
