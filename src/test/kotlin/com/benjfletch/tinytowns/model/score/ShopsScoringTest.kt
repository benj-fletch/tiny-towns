package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.buildings.Bakery
import com.benjfletch.tinytowns.model.buildings.TestFoodProducer
import com.benjfletch.tinytowns.model.buildings.TestGoodsHandler
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class BakeryScoringTest : AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(0, 3, 3, 3, 3)
            val foodProducerAdjacent = fullAdjacencyParameters(Bakery, TestFoodProducer, scores)
            val goodsHandlerAdjacent = fullAdjacencyParameters(Bakery, TestGoodsHandler, scores)
            val fPAndGHAdjacent = adjacencyParameters(Bakery, 3, TestGoodsHandler, TestFoodProducer)
            return foodProducerAdjacent.plus(goodsHandlerAdjacent).plusElement(fPAndGHAdjacent).stream()
        }
    }
}
