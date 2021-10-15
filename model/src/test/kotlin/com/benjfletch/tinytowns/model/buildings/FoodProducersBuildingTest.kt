package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class OrchardBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Orchard()) {
                orientation {
                    row(STONE(), WHEAT())
                    row(WHEAT(), WOOD())
                }
                orientation {
                    row(WHEAT(), STONE())
                    row(WOOD(), WHEAT())
                }
                orientation {
                    row(WOOD(), WHEAT())
                    row(WHEAT(), STONE())
                }
                orientation {
                    row(WHEAT(), WOOD())
                    row(STONE(), WHEAT())
                }
            }))
        }
    }
}

class GranaryBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Granary()) {
                orientation {
                    row(WHEAT(), WHEAT())
                    row(WOOD(), BRICK())
                }
                orientation {
                    row(WOOD(), WHEAT())
                    row(BRICK(), WHEAT())
                }
                orientation {
                    row(BRICK(), WOOD())
                    row(WHEAT(), WHEAT())
                }
                orientation {
                    row(WHEAT(), BRICK())
                    row(WHEAT(), WOOD())
                }
            }))
        }
    }
}

class FarmBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Farm()) {
                orientation {
                    row(WHEAT(), WHEAT())
                    row(WOOD(), WOOD())
                }
                orientation {
                    row(WOOD(), WHEAT())
                    row(WOOD(), WHEAT())
                }
                orientation {
                    row(WOOD(), WOOD())
                    row(WHEAT(), WHEAT())
                }
                orientation {
                    row(WHEAT(), WOOD())
                    row(WHEAT(), WOOD())
                }
            }))
        }
    }
}

class GreenhouseBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Greenhouse()) {
                orientation {
                    row(WHEAT(), GLASS())
                    row(WOOD(), WOOD())
                }
                orientation {
                    row(WOOD(), WHEAT())
                    row(WOOD(), GLASS())
                }
                orientation {
                    row(WOOD(), WOOD())
                    row(GLASS(), WHEAT())
                }
                orientation {
                    row(GLASS(), WOOD())
                    row(WHEAT(), WOOD())
                }
            }))
        }
    }
}
