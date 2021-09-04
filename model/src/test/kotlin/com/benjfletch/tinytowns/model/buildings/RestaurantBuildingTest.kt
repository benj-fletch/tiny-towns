package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class InnBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Inn) {
                orientation {
                    row(WHEAT, STONE, WOOD)
                }
                orientation {
                    row(WHEAT)
                    row(STONE)
                    row(WOOD)
                }
                orientation {
                    row(WOOD, STONE, WHEAT)
                }
                orientation {
                    row(WOOD)
                    row(STONE)
                    row(WHEAT)
                }
            }))
        }
    }
}

class TavernBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Tavern) {
                orientation {
                    row(BRICK, BRICK, GLASS)
                }
                orientation {
                    row(BRICK)
                    row(BRICK)
                    row(GLASS)
                }
                orientation {
                    row(GLASS, BRICK, BRICK)
                }
                orientation {
                    row(GLASS)
                    row(BRICK)
                    row(BRICK)
                }
            }))
        }
    }
}

class AlmshouseBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Almshouse) {
                orientation {
                    row(STONE, STONE, GLASS)
                }
                orientation {
                    row(STONE)
                    row(STONE)
                    row(GLASS)
                }
                orientation {
                    row(GLASS, STONE, STONE)
                }
                orientation {
                    row(GLASS)
                    row(STONE)
                    row(STONE)
                }
            }))
        }
    }
}

class FeastHallBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(FeastHall) {
                orientation {
                    row(WOOD, WOOD, GLASS)
                }
                orientation {
                    row(WOOD)
                    row(WOOD)
                    row(GLASS)
                }
                orientation {
                    row(GLASS, WOOD, WOOD)
                }
                orientation {
                    row(GLASS)
                    row(WOOD)
                    row(WOOD)
                }
            }))
        }
    }
}
