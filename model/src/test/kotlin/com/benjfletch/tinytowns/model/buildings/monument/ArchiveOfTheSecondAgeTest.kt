package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.BuildingInstanceTest
import com.benjfletch.tinytowns.model.buildings.Chapel
import com.benjfletch.tinytowns.model.buildings.FedCottage
import com.benjfletch.tinytowns.model.buildings.buildingOrientations
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class ArchiveOfTheSecondAgeBuildingTest: BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(ArchiveOfTheSecondAge()) {
                orientation {
                    row(WHEAT(), WHEAT())
                    row(BRICK(), GLASS())
                }
                orientation {
                    row(BRICK(), WHEAT())
                    row(GLASS(), WHEAT())
                }
                orientation {
                    row(GLASS(), BRICK())
                    row(WHEAT(), WHEAT())
                }
                orientation {
                    row(WHEAT(), GLASS())
                    row(WHEAT(), BRICK())
                }
            }))
        }
    }
}

class ArchiveOfTheSecondAgeTest {
    @Test
    fun `scores one point for every unique building except monument`() {
        val board = Board()
        val archiveLocation = Location(1, 3)
        val archive = ArchiveOfTheSecondAge()
        board.place(Location(1, 1), FedCottage())
        board.place(Location(1, 2), Chapel())
        board.place(archiveLocation, archive)
        assertThat(archive.score(archiveLocation, board.gameGrid, emptyMap())).isEqualTo(2)
    }

    @Test
    fun `only scores each building once when there are duplicates`() {
        val board = Board()
        val archiveLocation = Location(1, 3)
        val archive = ArchiveOfTheSecondAge()
        board.place(Location(1, 1), Chapel())
        board.place(Location(1, 2), Chapel())
        board.place(archiveLocation, archive)
        assertThat(archive.score(archiveLocation, board.gameGrid, emptyMap())).isEqualTo(1)
    }
}