package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.*
import com.benjfletch.tinytowns.model.buildings.*
import com.benjfletch.tinytowns.model.score.AdjacencyScoringTest
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class MandrasPalaceBuildingTest: BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(MandrasPalace()) {
                orientation {
                    row(WHEAT(), GLASS())
                    row(BRICK(), WOOD())
                }
                orientation {
                    row(GLASS(), WOOD())
                    row(WHEAT(), BRICK())
                }
                orientation {
                    row(WOOD(), BRICK())
                    row(GLASS(), WHEAT())
                }
                orientation {
                    row(BRICK(), WHEAT())
                    row(WOOD(), GLASS())
                }
            }))
        }
    }
}

class MandrasPalaceScoreTest: AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val nonUniqueScores = listOf(0, 2, 2, 2, 2)

            val nonUniqueArguments = fullAdjacencyParameters(MandrasPalace(), Well(), nonUniqueScores)
            val uniqueAdjacencyArguments = arrayOf(
                adjacencyParameters(MandrasPalace(), 2, TestShop),
                adjacencyParameters(MandrasPalace(), 4, TestShop, TestFoodProducer),
                adjacencyParameters(MandrasPalace(), 6, TestShop, TestFoodProducer, TestAttraction),
                adjacencyParameters(MandrasPalace(), 8, TestShop, TestFoodProducer, TestAttraction, TestRestaurant)
            )

            return nonUniqueArguments.plus(uniqueAdjacencyArguments).stream()
        }
    }
}