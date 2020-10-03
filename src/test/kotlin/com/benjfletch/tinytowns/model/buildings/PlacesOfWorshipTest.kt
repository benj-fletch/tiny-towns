package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class AbbeyBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(
                    buildingOrientations(Abbey) {
                        orientation {
                            row(NONE, NONE, GLASS)
                            row(BRICK, STONE, STONE)
                        }
                        orientation {
                            row(BRICK, NONE)
                            row(STONE, NONE)
                            row(STONE, GLASS)
                        }
                        orientation {
                            row(STONE, STONE, BRICK)
                            row(GLASS, NONE, NONE)
                        }
                        orientation {
                            row(GLASS, STONE)
                            row(NONE, STONE)
                            row(NONE, BRICK)
                        }
                    }
            ))
        }
    }
}
