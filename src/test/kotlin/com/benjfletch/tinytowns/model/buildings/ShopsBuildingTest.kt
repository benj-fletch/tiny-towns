package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
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
