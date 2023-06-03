package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Building
import org.junit.jupiter.params.provider.Arguments

abstract class IfFedScoringTest: ScoringTest() {
    companion object {
        @JvmStatic
        fun scores(building: Building, expected: Int): Arguments {
            return Arguments.of(building, Location(0, 0), emptyMap<Location, GamePiece>(), expected)
        }
    }
}