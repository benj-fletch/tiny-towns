package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.score.AccumulativeAdjacencyScore
import com.benjfletch.tinytowns.model.score.FlatScore
import com.benjfletch.tinytowns.model.score.IfAdjacentScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KClass

/** Super interface for all Attraction (Grey) implemented in the game */
interface Attraction : Building {
    override val buildingType: BuildingType
        get() = BuildingType.ATTRACTION
}

@Serializable
@SerialName("fountain")
data class Fountain(
    override val pieceName: String = "Fountain",
    override val text: String = "2 (Point) if adjacent to a (Attraction).",
    override val shape: Shape = Shape(listOf(listOf(WOOD(), STONE()))),

    @Transient override val adjacentTypes: Iterable<KClass<out Building>> = listOf(Attraction::class),
    override val scoreWhenAdjacent: Int = 2,
) : Attraction, IfAdjacentScore

@Serializable
@SerialName("millstone")
data class Millstone(
    override val pieceName: String = "Millstone",
    override val text: String = "2 (Point) if adjacent to a (FoodProducer) or (Shop).",
    override val shape: Shape = Shape(listOf(listOf(WOOD(), STONE()))),

    @Transient override val adjacentTypes: List<KClass<out Building>> = listOf(FoodProducer::class, Shop::class),
    override val scoreWhenAdjacent: Int = 2,
) : Attraction, IfAdjacentScore

@Serializable
@SerialName("shed")
data class Shed(
    override val pieceName: String = "Shed",
    override val text: String = "1 (Point). May be constructed on any empty square in your town",
    override val shape: Shape = Shape(listOf(listOf(WOOD(), STONE()))),

    override val canBeBuiltAnywhere: Boolean = true,
    override val score: Int = 1,
) : Attraction, FlatScore

@Serializable
@SerialName("well")
data class Well(
    override val pieceName: String = "Well",
    override val text: String = "1 (Point) for each adjacent (Cottage)",
    override val shape: Shape = Shape(listOf(listOf(WOOD(), STONE()))),

    @Transient override val adjacentTypes: List<KClass<out Building>> = listOf(Cottage::class),
    override val scorePerAdjacent: Int = 1,
) : Attraction, AccumulativeAdjacencyScore
