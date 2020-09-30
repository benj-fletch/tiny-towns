package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WOOD
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

object AttractionsTest {
    @JvmStatic
    fun attractionsOrientations(building: Building): BuildingOrientations {
        return buildingOrientations(building) {
            orientation {
                row(WOOD, STONE)
            }
            orientation {
                row(WOOD)
                row(STONE)
            }
            orientation {
                row(STONE, WOOD)
            }
            orientation {
                row(STONE)
                row(WOOD)
            }
        }
    }
}

class FountainTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(AttractionsTest.attractionsOrientations(Fountain)))
        }

        @JvmStatic
        fun scores(): Stream<Arguments> {
            return adjacencyScoreTests(Fountain, BuildingCategory.ATTRACTION, 2).stream()
        }
    }
}

class MillstoneTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(AttractionsTest.attractionsOrientations(Millstone)))
        }

        @JvmStatic
        fun scores(): Stream<Arguments> {
            return adjacencyScoreTests(Millstone, BuildingCategory.SHOP, 2)
                    .plus(adjacencyScoreTests(Millstone, BuildingCategory.FOOD_PRODUCER, 2))
                    .stream()
        }
    }
}

class ShedTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(AttractionsTest.attractionsOrientations(Shed)))
        }

        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(Arguments.of(Shed, emptyMap<Location, GamePiece>(), 1))
        }
    }
}

class WellTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(AttractionsTest.attractionsOrientations(Well)))
        }

        @JvmStatic
        fun scores(): Stream<Arguments> {
            return accumulativeAdjacencyScoreTests(Well, BuildingCategory.COTTAGE, 1).stream()
        }
    }
}