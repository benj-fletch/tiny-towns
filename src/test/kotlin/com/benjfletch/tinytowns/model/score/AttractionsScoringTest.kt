package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.buildings.Cottage
import com.benjfletch.tinytowns.model.buildings.Fountain
import com.benjfletch.tinytowns.model.buildings.Millstone
import com.benjfletch.tinytowns.model.buildings.Shed
import com.benjfletch.tinytowns.model.buildings.TestAttraction
import com.benjfletch.tinytowns.model.buildings.TestFoodProducer
import com.benjfletch.tinytowns.model.buildings.TestShop
import com.benjfletch.tinytowns.model.buildings.Well
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class FountainScoringTest : AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(0, 2, 2, 2, 2)
            return fullAdjacencyParameters(Fountain, TestAttraction, scores).stream()
        }
    }
}

class MillstoneScoringTest: AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(0, 2, 2, 2, 2)
            val shopAdjacent = fullAdjacencyParameters(Millstone, TestShop, scores)
            val foodProducerAdjacent = fullAdjacencyParameters(Millstone, TestFoodProducer, scores)
            val shopAndFoodProducerAdjacent = adjacencyParameters(Millstone, 2, TestShop, TestFoodProducer)
            return shopAdjacent.plus(foodProducerAdjacent).plusElement(shopAndFoodProducerAdjacent).stream()
        }
    }
}

class ShedScoringTest: FlatScoreTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(scores(Shed, 1))
        }
    }
}

class WellScoringTest: AdjacencyScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            val scores = listOf(0, 1, 2, 3, 4)
            return fullAdjacencyParameters(Well, Cottage.Unfed, scores).stream()
        }
    }
}
