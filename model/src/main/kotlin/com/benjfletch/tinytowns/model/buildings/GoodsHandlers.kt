package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.GoodsHandlerException
import com.benjfletch.tinytowns.model.Resource
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.masterbuildereffect.ConsumeResourceEffect
import com.benjfletch.tinytowns.model.masterbuildereffect.SwitchResourceEffect
import com.benjfletch.tinytowns.model.masterbuildereffect.RestrictedResourcesEffect
import com.benjfletch.tinytowns.model.score.FlatScore

/** Super interface for all "Goods Handlers" (Black) implemented in the game */
interface GoodsHandler: Building {
    val resourcesHeld: MutableList<Resource>
    val maxResources: Int

    fun holdResource(resource: Resource) {
        if(resourcesHeld.size == maxResources) {
            throw GoodsHandlerException("Cannot hold ${resource.pieceName} in ${this::class.simpleName}. Max capacity of $maxResources already reached")
        }
        resourcesHeld.add(resource)
    }

    fun removeResource(resource: Resource) {
        if(resourcesHeld.contains(resource)) {
            resourcesHeld.remove(resource)
        }
        else {
            throw GoodsHandlerException("Cannot remove ${resource.pieceName}. ${this::class.simpleName} does not currently contain ${resource.pieceName}")
        }
    }
}

class Bank: GoodsHandler, FlatScore, RestrictedResourcesEffect {
    override val pieceName = "Bank"
    override val text = "4 (point). When constructed, place a resource on this building that is not on another (GoodsHandler) in your town. As Master Builder, you can no longer name the resource on your (GoodsHandler)."
    override val shape = Shape(listOf(
            listOf(WHEAT, WHEAT, NONE),
            listOf(WOOD, GLASS, BRICK)
    ))

    override val score = 4

    override val resourcesHeld = mutableListOf<Resource>()
    override val maxResources: Int = 1

    override val restrictedResources: Set<Resource>
        get() = resourcesHeld.toSet()
}

class Factory: GoodsHandler, FlatScore, SwitchResourceEffect {
    override val pieceName = "Factory"
    override val text = "When constructed, place 1 of the 5 resources on (GoodsHandler). When another player names this resource, you place a different resource instead."
    override val shape = Shape(listOf(
            listOf(WOOD, NONE, NONE, NONE),
            listOf(BRICK, STONE, STONE, BRICK)
    ))

    override val resourcesHeld = mutableListOf<Resource>()
    override val maxResources = 1

    override val score = 0

    override val switchable: Set<Resource>
        get() = resourcesHeld.toSet()
    override val switchableFor = Resource.basicResources()
}

class TradingPost: GoodsHandler, FlatScore, Resource() {
    // TODO affect build time to allow TradingPost to be part of a building
    override val pieceName = "Trading Post"
    override val text = "1 (point). You may treat (GoodsHandler) as a wild resource for future buildings."
    override val shape = Shape(listOf(
            listOf(STONE, WOOD, NONE),
            listOf(STONE, WOOD, BRICK)
    ))

    override val resourcesHeld = mutableListOf<Resource>()
    override val maxResources = 0

    override val score = 1

    override fun actsAsAnyResource() = true
    override fun removeAfterBuild() = false
}

class Warehouse: GoodsHandler, FlatScore, SwitchResourceEffect, ConsumeResourceEffect {
    override val pieceName = "Warehouse"
    override val text = "-1 (point) for each resource on (GoodsHandler). Each (GoodsHandler) can store 3 resources. When another player names a resource, you may place that resource on (GoodsHandler) or swap it with another resource on (GoodsHandler)."
    override val shape = Shape(listOf(
            listOf(WHEAT, WOOD, WHEAT),
            listOf(BRICK, NONE, BRICK)
    ))

    override val resourcesHeld = mutableListOf<Resource>()
    override val maxResources = 3

    override val score: Int
        get() = resourcesHeld.size * -1

    override val switchable = Resource.basicResources()
    override val switchableFor: Set<Resource>
        get() = resourcesHeld.toSet()

    override fun consume(resource: Resource) {
        holdResource(resource)
    }
}
