package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.buildings.Bank
import com.benjfletch.tinytowns.model.buildings.Factory
import com.benjfletch.tinytowns.model.buildings.TradingPost
import com.benjfletch.tinytowns.model.buildings.Warehouse
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class BankScoringTest: FlatScoreTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(scores(Bank(),4))
        }
    }
}

class FactoryScoringTest: FlatScoreTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(scores(Factory(),0))
        }
    }
}

class TradingPostScoringTest: FlatScoreTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(scores(TradingPost(),1))
        }
    }
}

class WarehouseScoringTest: FlatScoreTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val warehouseOneResource = Warehouse()
            warehouseOneResource.holdResource(WHEAT)
            val warehouseTwoResources = Warehouse()
            warehouseTwoResources.holdResource(WHEAT)
            warehouseTwoResources.holdResource(WHEAT)
            val warehouseThreeResources = Warehouse()
            warehouseThreeResources.holdResource(WHEAT)
            warehouseThreeResources.holdResource(WHEAT)
            warehouseThreeResources.holdResource(WHEAT)
            return Stream.of(
                    scores(warehouseOneResource,-1),
                    scores(warehouseTwoResources,-2),
                    scores(warehouseThreeResources,-3)
            )
        }
    }
}
