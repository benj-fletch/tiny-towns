package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.buildings.Abbey
import com.benjfletch.tinytowns.model.buildings.TestGoodsHandler
import com.benjfletch.tinytowns.model.buildings.TestRestaurant
import com.benjfletch.tinytowns.model.buildings.TestShop
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class AbbeyScoringTest : AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(3, 0, 0, 0, 0)

            val goodsHandlerAdjacent = fullAdjacencyParameters(Abbey, TestGoodsHandler, scores)
            val restaurantAdjacent = fullAdjacencyParameters(Abbey, TestRestaurant, scores)
            val shopAndGHAdjacent = adjacencyParameters(Abbey, 0, TestGoodsHandler, TestShop)

            return restaurantAdjacent.plus(goodsHandlerAdjacent).plusElement(shopAndGHAdjacent).stream()
        }
    }
}
