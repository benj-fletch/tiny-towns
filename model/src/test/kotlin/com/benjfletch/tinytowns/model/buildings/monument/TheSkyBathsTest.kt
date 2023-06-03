package com.benjfletch.tinytowns.model.buildings.monument

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.buildings.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TheSkyBathsTest {
    @Test
    fun `scores 0 when every type of building is present`() {
        val board = Board()
        board.place("1:1", TestAttraction)
        board.place("1:2", TestFoodProducer)
        board.place("1:3", TestGoodsHandler)
        board.place("1:4", TestPlaceOfWorship)
        board.place("2:1", TestRestaurant)
        board.place("2:2", TestShop)

        val skyBaths = TheSkyBaths()
        val skyBathsLocation = Location(4, 4)
        board.place(skyBathsLocation, skyBaths)
        assertThat(skyBaths.score(skyBathsLocation, board.gameGrid, emptyMap())).isEqualTo(0)
    }

    @Test
    fun `scores 12 when no other type of building is present`() {
        val board = Board()
        val skyBaths = TheSkyBaths()
        val skyBathsLocation = Location(4, 4)
        board.place(skyBathsLocation, skyBaths)
        assertThat(skyBaths.score(skyBathsLocation, board.gameGrid, emptyMap())).isEqualTo(12)
    }

}