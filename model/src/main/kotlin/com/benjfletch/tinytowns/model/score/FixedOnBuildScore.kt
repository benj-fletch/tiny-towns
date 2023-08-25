package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.GameGrid

interface FixedOnBuildScore {
    var fixedScore: Int

    fun fixScore(gameGrid: GameGrid)
}