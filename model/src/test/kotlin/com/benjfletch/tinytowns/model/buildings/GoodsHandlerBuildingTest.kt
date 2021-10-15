package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class BankBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(
                    buildingOrientations(Bank()) {
                        orientation {
                            row(WHEAT(), WHEAT(), NONE())
                            row(WOOD(), GLASS(), BRICK())
                        }
                        orientation {
                            row(WOOD(), WHEAT())
                            row(GLASS(), WHEAT())
                            row(BRICK(), NONE())
                        }
                        orientation {
                            row(BRICK(), GLASS(), WOOD())
                            row(NONE(), WHEAT(), WHEAT())
                        }
                        orientation {
                            row(NONE(), BRICK())
                            row(WHEAT(), GLASS())
                            row(WHEAT(), WOOD())
                        }
                    }
            ))
        }
    }
}

class FactoryBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(
                    buildingOrientations(Factory()) {
                        orientation {
                            row(WOOD(), NONE(), NONE(), NONE())
                            row(BRICK(), STONE(), STONE(), BRICK())
                        }
                        orientation {
                            row(BRICK(), WOOD())
                            row(STONE(), NONE())
                            row(STONE(), NONE())
                            row(BRICK(), NONE())
                        }
                        orientation {
                            row(BRICK(), STONE(), STONE(), BRICK())
                            row(NONE(), NONE(), NONE(), WOOD())
                        }
                        orientation {
                            row(NONE(), BRICK())
                            row(NONE(), STONE())
                            row(NONE(), STONE())
                            row(WOOD(), BRICK())
                        }
                    }
            ))
        }
    }
}

class TradingPostBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(
                    buildingOrientations(TradingPost()) {
                        orientation {
                            row(STONE(), WOOD(), NONE())
                            row(STONE(), WOOD(), BRICK())
                        }
                        orientation {
                            row(STONE(), STONE())
                            row(WOOD(), WOOD())
                            row(BRICK(), NONE())
                        }
                        orientation {
                            row(BRICK(), WOOD(), STONE())
                            row(NONE(), WOOD(), STONE())
                        }
                        orientation {
                            row(NONE(), BRICK())
                            row(WOOD(), WOOD())
                            row(STONE(), STONE())
                        }
                    }
            ))
        }
    }
}

class WarehouseBuildingTest : BuildingInstanceTest() {
    companion object {
        @JvmStatic
        fun buildingOrientations(): Stream<Arguments> {
            return Stream.of(Arguments.of(
                    buildingOrientations(Warehouse()) {
                        orientation {
                            row(WHEAT(), WOOD(), WHEAT())
                            row(BRICK(), NONE(), BRICK())
                        }
                        orientation {
                            row(WHEAT(), BRICK())
                            row(WOOD(), NONE())
                            row(WHEAT(), BRICK())
                        }
                        orientation {
                            row(BRICK(), NONE(), BRICK())
                            row(WHEAT(), WOOD(), WHEAT())
                        }
                        orientation {
                            row(BRICK(), WHEAT())
                            row(NONE(), WOOD())
                            row(BRICK(), WHEAT())
                        }
                    }
            ))
        }
    }
}

