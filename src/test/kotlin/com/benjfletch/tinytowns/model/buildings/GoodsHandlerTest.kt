package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.BoardException
import com.benjfletch.tinytowns.GoodsHandlerException
import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.Shape
import com.benjfletch.tinytowns.model.WHEAT
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GoodsHandlerTest {
    private lateinit var board: Board

    private val origin = Location(1, 1)
    private val oneOne = Location(2, 2)

    private val cottageResources = mapOf(Location(1, 1) to BRICK, Location(2, 1) to GLASS, Location(2, 2) to WHEAT)
    private val cottageTargetLoc = oneOne

    @BeforeEach
    fun setupBoard() {
        board = Board()
    }

    @Test
    fun `Goods Handler can hold single resource`() {
        val goodsHandler = TestGoodsHandlerInstance()
        assertThat(goodsHandler.resourcesHeld).isEmpty()
        goodsHandler.holdResource(WHEAT)
        assertThat(goodsHandler.resourcesHeld).containsExactly(WHEAT)
    }

    @Test
    fun `Goods Handler can hold multiple resources of different types`() {
        val goodsHandler = TestGoodsHandlerInstance()
        goodsHandler.holdResource(WHEAT)
        goodsHandler.holdResource(STONE)
        assertThat(goodsHandler.resourcesHeld).containsExactly(WHEAT, STONE)
    }

    @Test
    fun `Goods Handler can hold multiple resources of the same type`() {
        val goodsHandler = TestGoodsHandlerInstance()
        goodsHandler.holdResource(WHEAT)
        goodsHandler.holdResource(WHEAT)
        assertThat(goodsHandler.resourcesHeld).containsExactly(WHEAT, WHEAT)
    }

    @Test
    fun `Goods Handler throws exception when holding maximum resources and another is added`() {
        val goodsHandler = TestGoodsHandlerInstance()
        goodsHandler.holdResource(WHEAT)
        goodsHandler.holdResource(WHEAT)
        assertThatCode { goodsHandler.holdResource(WHEAT) }
                .isInstanceOf(GoodsHandlerException::class.java)
                .hasMessageContaining("wheat")
                .hasMessageContaining("${goodsHandler.maxResources}")
    }

    @Test
    fun `GoodsHandler can remove a resource`() {
        val goodsHandler = TestGoodsHandlerInstance()
        assertThat(goodsHandler.resourcesHeld).isEmpty()
        goodsHandler.holdResource(WHEAT)
        assertThat(goodsHandler.resourcesHeld).containsExactly(WHEAT)
        goodsHandler.removeResource(WHEAT)
        assertThat(goodsHandler.resourcesHeld).isEmpty()
    }

    @Test
    fun `GoodsHandler does not remove all of the same resource in one call`() {
        val goodsHandler = TestGoodsHandlerInstance()
        goodsHandler.holdResource(WHEAT)
        goodsHandler.holdResource(WHEAT)
        assertThat(goodsHandler.resourcesHeld).containsExactly(WHEAT, WHEAT)
        goodsHandler.removeResource(WHEAT)
        assertThat(goodsHandler.resourcesHeld).containsExactly(WHEAT)
    }

    @Test
    fun `Goods handler throws exception when trying to remove a resource that is not being held`() {
        val goodsHandler = TestGoodsHandlerInstance()
        goodsHandler.holdResource(STONE)
        assertThatCode { goodsHandler.removeResource(WHEAT) }
                .isInstanceOf(GoodsHandlerException::class.java)
                .hasMessageContaining("wheat")
                .hasMessageContaining("${goodsHandler::class.simpleName}")
    }

    @Test
    fun `Goods handler throws exception when holding no resources and one is removed`() {
        val goodsHandler = TestGoodsHandlerInstance()
        assertThatCode { goodsHandler.removeResource(WHEAT) }
                .isInstanceOf(GoodsHandlerException::class.java)
                .hasMessageContaining("wheat")
                .hasMessageContaining("${goodsHandler::class.simpleName}")
    }


    @Test
    fun `TradingPost can act as any resource to build building`() {
        for (tradingPostLocation in cottageResources.keys) {
            val cottageResourcesWithTradingPost = cottageResources.toMutableMap()
            cottageResourcesWithTradingPost.replace(tradingPostLocation, TradingPost())
            cottageResourcesWithTradingPost.forEach { board.place(it.key, it.value) }
            if(cottageTargetLoc != tradingPostLocation) {
                board.build(cottageResourcesWithTradingPost, cottageTargetLoc, Cottage.Unfed)
                assertThat(board.gameGrid[cottageTargetLoc]).isEqualTo(Cottage.Unfed)
            }
            board.clear()
        }
    }

    @Test
    fun `TradingPost is not removed when used to build a building`() {
        for (tradingPostLocation in cottageResources.keys) {
            val cottageResourcesWithTradingPost = cottageResources.toMutableMap()
            cottageResourcesWithTradingPost.replace(tradingPostLocation, TradingPost())
            cottageResourcesWithTradingPost.forEach { board.place(it.key, it.value) }
            if(cottageTargetLoc != tradingPostLocation) {
                board.build(cottageResourcesWithTradingPost, cottageTargetLoc, Cottage.Unfed)
                assertThat(board.gameGrid[tradingPostLocation]).isEqualTo(TradingPost())
            }
            board.clear()
        }
    }

    @Test
    fun `TradingPost cannot be built on top of when used to build a building`() {
        val tradingPostLocation = cottageTargetLoc
        val cottageResourcesWithTradingPost = cottageResources.toMutableMap()

        cottageResourcesWithTradingPost.replace(tradingPostLocation, TradingPost())
        cottageResourcesWithTradingPost.forEach { board.place(it.key, it.value) }

        assertThatCode { board.build(cottageResourcesWithTradingPost, cottageTargetLoc, Cottage.Unfed) }
                .isInstanceOf(BoardException::class.java)
                .hasMessageContaining(("$cottageTargetLoc is occupied by ${TradingPost().pieceName}."))
    }
}

class TestGoodsHandlerInstance: GoodsHandler {
    override val pieceName = "Test Goods Handler"
    override val text = ""
    override val shape = Shape(listOf(listOf(WHEAT)))

    override val maxResources = 2
    override val resourcesHeld = mutableListOf<Resource>()
}
