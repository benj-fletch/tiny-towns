package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.FedCottage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ShrineOfTheElderTreeTest {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("0", "1"),
                Arguments.of("1", "2"),
                Arguments.of("2", "3"),
                Arguments.of("3", "4"),
                Arguments.of("4", "5"),
                Arguments.of("5", "8"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("scores")
    fun `Accurately calculates score`(buildingsToBuild: Int, expectedScore: Int) {
        val board = Board(4)
        val shrineOfTheElderTree = ShrineOfTheElderTree()
        repeat(buildingsToBuild) { index ->
            val location = Location((index % 4) + 1, (index / 4) + 1)
            board.place(location, FedCottage())
        }
        board.place(Location(4, 4), shrineOfTheElderTree)
        shrineOfTheElderTree.onBuild(board.gameGrid);
        assertThat(shrineOfTheElderTree.fixedScore).isEqualTo(expectedScore)
    }
}