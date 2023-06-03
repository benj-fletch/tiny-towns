package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.BuildingInstanceTest
import com.benjfletch.tinytowns.model.buildings.Chapel
import com.benjfletch.tinytowns.model.score.IfFedScoringTest
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream
import com.benjfletch.tinytowns.model.buildings.buildingOrientations
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class BarrettCastleScoringTest: IfFedScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(
                scores(UnfedBarrettCastle(), 0),
                scores(FedBarrettCastle(), 5)
            )
        }
    }
}

class BarrettCastleBuildingTest: BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(FedBarrettCastle()) {
                orientation {
                    row(WHEAT(), NONE(), NONE(), STONE())
                    row(WOOD(), GLASS(), GLASS(), BRICK())
                }
                orientation {
                    row(WOOD(), WHEAT())
                    row(GLASS(), NONE())
                    row(GLASS(), NONE())
                    row(BRICK(), STONE())
                }
                orientation {
                    row(BRICK(), GLASS(), GLASS(), WOOD())
                    row(STONE(), NONE(), NONE(), WHEAT())
                }
                orientation {
                    row(STONE(), BRICK())
                    row(NONE(), GLASS())
                    row(NONE(), GLASS())
                    row(WHEAT(), WOOD())
                }
            }))
        }
    }
}

class BarrettCastleTest {
    @Test
    @Disabled("Disabled until functionality implemented")
    fun `counts as two cottages for scoring of other buildings`() {
        val board = Board()
        val origin = Location(1, 1)
        val chapel = Chapel()
        board.place(origin, chapel)
        board.place(Location(1, 2), FedBarrettCastle())
        assertThat(chapel.score(origin, board.gameGrid, emptyMap())).isEqualTo(2)
    }
}