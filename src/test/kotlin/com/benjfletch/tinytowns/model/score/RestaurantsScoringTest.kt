package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.Almshouse
import com.benjfletch.tinytowns.model.buildings.FeastHall
import com.benjfletch.tinytowns.model.buildings.Inn
import com.benjfletch.tinytowns.model.buildings.Tavern
import com.benjfletch.tinytowns.model.buildings.TestRestaurant
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class AlmshouseScoringTest : AccumulativeScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(-1, 5, -3, 15, -5, 26, 26, 26)
            return accumulativeScoreParameters(Almshouse, scores)
        }
    }
}

class FeastHallBasedScoringTest: PlayerBasedScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val arguments = mutableListOf<Arguments>()

            val playerBoard = Board(3)
            val origin = Location(1, 1)
            playerBoard.place(origin, FeastHall)
            arguments.add(Arguments.of(FeastHall, origin, playerBoard.gameGrid, 3, Board().gameGrid))

            val otherPlayerBoard = Board(3)
            otherPlayerBoard.place(origin, FeastHall)
            arguments.add(Arguments.of(FeastHall, origin, playerBoard.gameGrid, 2, otherPlayerBoard.gameGrid))

            val playerBoard2 = Board(3)
            val otherPlayerBoard2 = Board(3)
            otherPlayerBoard2.place(origin, FeastHall)
            otherPlayerBoard2.place(Location(1, 2), FeastHall)
            playerBoard2.place(origin, FeastHall)
            arguments.add(Arguments.of(FeastHall, origin, playerBoard2.gameGrid, 2, otherPlayerBoard2.gameGrid))

            return arguments.stream()
        }
    }
}

class TavernScoringTest : AccumulativeScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(2, 5, 9, 14, 20, 20, 20)
            return accumulativeScoreParameters(Tavern, scores)
        }
    }
}

class InnScoringTest : RowColumnScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(3, 0, 0, 3, 0, 0, 3, 0, 0)
            return fullRowColParameters(Inn, TestRestaurant, scores).stream()
        }
    }
}
