package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid
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
interface SpecifiedPositionScore : PositionalScore
