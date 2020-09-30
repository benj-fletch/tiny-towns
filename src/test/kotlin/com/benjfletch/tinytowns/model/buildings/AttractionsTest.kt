package com.benjfletch.tinytowns.model.buildings

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
    }
}

class MillstoneTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(AttractionsTest.attractionsOrientations(Millstone)))
        }
    }
}

class ShedTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(AttractionsTest.attractionsOrientations(Shed)))
        }
    }
}

class WellTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(AttractionsTest.attractionsOrientations(Well)))
        }
    }
}