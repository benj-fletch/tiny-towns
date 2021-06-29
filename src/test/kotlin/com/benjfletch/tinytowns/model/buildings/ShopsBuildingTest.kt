package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class BakeryBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            val bakeryOrientations = buildingOrientations(Bakery) {
                orientation {
                    row(NONE, WHEAT, NONE)
                    row(BRICK, GLASS, BRICK)
                }
                orientation {
                    row(BRICK, NONE)
                    row(GLASS, WHEAT)
                    row(BRICK, NONE)
                }
                orientation {
                    row(BRICK, GLASS, BRICK)
                    row(NONE, WHEAT, NONE)
                }
                orientation {
                    row(NONE, BRICK)
                    row(WHEAT, GLASS)
                    row(NONE, BRICK)
                }
            }
            return Stream.of(Arguments.of(bakeryOrientations))
        }
    }
}

class MarketBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            val bakeryOrientations = buildingOrientations(Market) {
                orientation {
                    row(NONE, WOOD, NONE)
                    row(STONE, GLASS, STONE)
                }
                orientation {
                    row(STONE, NONE)
                    row(GLASS, WOOD)
                    row(STONE, NONE)
                }
                orientation {
                    row(STONE, GLASS, STONE)
                    row(NONE, WOOD, NONE)
                }
                orientation {
                    row(NONE, STONE)
                    row(WOOD, GLASS)
                    row(NONE, STONE)
                }
            }
            return Stream.of(Arguments.of(bakeryOrientations))
        }
    }
}

class TailorBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            val bakeryOrientations = buildingOrientations(Tailor) {
                orientation {
                    row(NONE, WHEAT, NONE)
                    row(STONE, GLASS, STONE)
                }
                orientation {
                    row(STONE, NONE)
                    row(GLASS, WHEAT)
                    row(STONE, NONE)
                }
                orientation {
                    row(STONE, GLASS, STONE)
                    row(NONE, WHEAT, NONE)
                }
                orientation {
                    row(NONE, STONE)
                    row(WHEAT, GLASS)
                    row(NONE, STONE)
                }
            }
            return Stream.of(Arguments.of(bakeryOrientations))
        }
    }
}

class TheaterBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            val bakeryOrientations = buildingOrientations(Theater) {
                orientation {
                    row(NONE, STONE, NONE)
                    row(WOOD, GLASS, WOOD)
                }
                orientation {
                    row(WOOD, NONE)
                    row(GLASS, STONE)
                    row(WOOD, NONE)
                }
                orientation {
                    row(WOOD, GLASS, WOOD)
                    row(NONE, STONE, NONE)
                }
                orientation {
                    row(NONE, WOOD)
                    row(STONE, GLASS)
                    row(NONE, WOOD)
                }
            }
            return Stream.of(Arguments.of(bakeryOrientations))
        }
    }
}
