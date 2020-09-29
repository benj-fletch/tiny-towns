package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class CottageTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            val orientations = buildingOrientations(Cottage) {
                orientation {
                    row(NONE, WHEAT)
                    row(BRICK, GLASS)
                }
                orientation {
                    row(BRICK, NONE)
                    row(GLASS, WHEAT)
                }
                orientation {
                    row(GLASS, BRICK)
                    row(WHEAT, NONE)
                }
                orientation {
                    row(WHEAT, GLASS)
                    row(NONE, NONE)
                }
            }

            return Stream.of(Arguments.of(orientations))
        }
    }
}