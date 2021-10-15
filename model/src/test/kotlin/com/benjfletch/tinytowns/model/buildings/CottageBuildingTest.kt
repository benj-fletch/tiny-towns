package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.WHEAT
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class CottageBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(buildingOrientations(FedCottage()) {
                        orientation {
                            row(NONE(), WHEAT())
                            row(BRICK(), GLASS())
                        }
                        orientation {
                            row(BRICK(), NONE())
                            row(GLASS(), WHEAT())
                        }
                        orientation {
                            row(GLASS(), BRICK())
                            row(WHEAT(), NONE())
                        }
                        orientation {
                            row(WHEAT(), GLASS())
                            row(NONE(), BRICK())
                        }
                    }),
                    Arguments.of(buildingOrientations(UnfedCottage()) {
                        orientation {
                            row(NONE(), WHEAT())
                            row(BRICK(), GLASS())
                        }
                        orientation {
                            row(BRICK(), NONE())
                            row(GLASS(), WHEAT())
                        }
                        orientation {
                            row(GLASS(), BRICK())
                            row(WHEAT(), NONE())
                        }
                        orientation {
                            row(WHEAT(), GLASS())
                            row(NONE(), BRICK())
                        }
                    }))
        }
    }
}
