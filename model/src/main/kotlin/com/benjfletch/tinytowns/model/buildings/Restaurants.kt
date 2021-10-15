package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.score.AccumulativeConstructedScore
import com.benjfletch.tinytowns.model.score.MoreBuildingTypeThanOtherPlayerScore
import com.benjfletch.tinytowns.model.score.NotRowColumnScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KClass

/** Super interface for all Restaurants (Green) implemented in the game */
interface Restaurant: Building

@Serializable
@SerialName("almshouse")
data class Almshouse(
    override val pieceName: String = "Almshouse",
    override val text: String = "(Point) based on your constructed (Restaurant)",
    override val shape: Shape = Shape(listOf(
        listOf(STONE(), STONE(), GLASS()))),

    @Transient override val types: List<KClass<Restaurant>> = listOf(Restaurant::class),
    override val scores: Map<Int, Int> = mapOf(0 to 0, 1 to -1, 2 to 5, 3 to -3, 4 to 15, 5 to -5, 6 to 26),
    override val maxScore: Int = 26,
): Restaurant, AccumulativeConstructedScore

@Serializable
@SerialName("feastHall")
data class FeastHall(
    override val pieceName: String = "Feast Hall",
    override val text: String = "2 (Point). +1 (Point) if you have more (Restaurant) than the player on your right.",
    override val shape: Shape = Shape(listOf(
        listOf(WOOD(), WOOD(), GLASS()))),

    override val baseScore: Int = 2,
    @Transient override val buildingType: KClass<out Building> = FeastHall::class,
    override val bonusIfPlayerHasMore: Int = 1,
): Restaurant, MoreBuildingTypeThanOtherPlayerScore

@Serializable
@SerialName("inn")
data class Inn(
    override val pieceName: String = "Inn",
    override val text: String = "3 (Point) if not in a row or column with another (Restaurant).",
    override val shape: Shape = Shape(listOf(
        listOf(WHEAT(), STONE(), WOOD()))),

    override val scoreWhenNotInRowOrCol: Int = 3,
    @Transient override val types: List<KClass<Restaurant>> = listOf(Restaurant::class),
): Restaurant, NotRowColumnScore

@Serializable
@SerialName("tavern")
data class Tavern(
    override val pieceName: String = "Tavern",
    override val text: String = "(Point) based on your constructed (Restaurant).",
    override val shape: Shape = Shape(listOf(
        listOf(BRICK(), BRICK(), GLASS()))),

    @Transient override val types: List<KClass<Restaurant>> = listOf(Restaurant::class),
    override val scores: Map<Int, Int> = mapOf(0 to 0, 1 to 2, 2 to 5, 3 to 9, 4 to 14, 5 to 20),
    override val maxScore: Int = 20,
): Restaurant, AccumulativeConstructedScore
