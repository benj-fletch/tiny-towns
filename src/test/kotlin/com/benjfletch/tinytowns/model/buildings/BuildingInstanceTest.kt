package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BuildingInstanceTest {
    @ParameterizedTest
    @MethodSource("buildingOrientations")
    fun `Building instance matches all orientations`(buildingOrientations: BuildingOrientations) {
        buildingOrientations.applyAssertions()
    }

    @ParameterizedTest
    @MethodSource("scores")
    fun `Accurately calculates score`(building: Building, input: Map<Location, GamePiece>, expected: Int) {
        assertThat(1).isEqualTo(1)
        // TODO when scoring exists, add support for this
//        val score = building.score(input)
//        assertThat(score)
//                .withFailMessage("Expected score of $expected from ${building.pieceName} but got $score")
//                .isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun adjacencyScoreTests(scoringBuilding: Building, adjacent: Building, adjacencyScore: Int): List<Arguments> {
            val args = mutableListOf<Arguments>()
            val board = Board(3)
            val adjacentLocations = listOf(Location(1,2), Location(2,3), Location(3,2), Location(2,1))
            val cornerLocations = listOf(Location(1,1), Location(1,3), Location(3,3), Location(3,1))

            board.place(Location(2, 2), scoringBuilding)
            args.add(Arguments.of(scoringBuilding, board.copy().spaces, adjacencyScore * 0))

            adjacentLocations.forEach {
                val copyBoard = board.copy()
                copyBoard.place(it, adjacent)
                args.add(Arguments.of(scoringBuilding, copyBoard.spaces, adjacencyScore))
            }

            cornerLocations.forEach {
                val copyBoard = board.copy()
                copyBoard.place(it, adjacent)
                args.add(Arguments.of(scoringBuilding, copyBoard.spaces, 0))
            }

            return args
        }

        @JvmStatic
        fun accumulativeAdjacencyScoreTests(scoringBuilding: Building, adjacent: Building, scorePerAdjacent: Int): List<Arguments> {
            val args = mutableListOf<Arguments>()
            val board = Board(3)
            val adjacentLocations = listOf(Location(1,2), Location(2,3), Location(3,2), Location(2,1))
            val cornerLocations = listOf(Location(1,1), Location(1,3), Location(3,3), Location(3,1))

            board.place(Location(2, 2), scoringBuilding)
            args.add(Arguments.of(scoringBuilding, board.copy().spaces, scorePerAdjacent * 0))

            adjacentLocations.forEachIndexed { index, location ->
                board.place(location, adjacent)
                args.add(Arguments.of(scoringBuilding, board.copy().spaces, scorePerAdjacent * (index + 1)))

                board.place(cornerLocations[index], adjacent)
                args.add(Arguments.of(scoringBuilding, board.copy().spaces, scorePerAdjacent * (index + 1)))
            }

            return args
        }
    }
}






class Orientation {
    val rows = arrayListOf<List<Resource>>()
    fun row(vararg resources: Resource) {
        this.rows.add(resources.toList())
    }

    override fun toString(): String {
        return rows.joinToString(",") { it.toString() }
    }
}

class BuildingOrientations(private val building: Building) {
    private val orientations = arrayListOf<Orientation>()

    fun orientation(init: Orientation.() -> Unit): Orientation {
        val orientation = Orientation()
        orientation.init()
        orientations.add(orientation)
        return orientation
    }

    fun applyAssertions() {
        assertThatCode { orientations.forEach { building.matrixMatches(it.rows) } }
                .withFailMessage("Could not find match for ${building.shape.matrix} in any of $orientations")
                .doesNotThrowAnyException()
    }
}

fun buildingOrientations(building: Building, init: BuildingOrientations.() -> Unit): BuildingOrientations {
    val buildingOrientations = BuildingOrientations(building)
    buildingOrientations.init()
    return buildingOrientations
}
