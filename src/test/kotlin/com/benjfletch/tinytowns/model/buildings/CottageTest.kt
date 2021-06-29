package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.WHEAT
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class CottageTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(Cottage.Fed) {
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
                    row(NONE, BRICK)
                }
            }))
        }
    }
}
