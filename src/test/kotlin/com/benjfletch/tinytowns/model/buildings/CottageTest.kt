package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.fail
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
                    row(NONE, BRICK)
                }
            }

            return Stream.of(Arguments.of(orientations))
        }

        @JvmStatic
        fun scores(): Stream<Arguments> {
            // TODO when scoring exists, add support for this
            return Stream.of(Arguments.of(Cottage, emptyMap<Location, GamePiece>(), 0))
        }
    }
}
