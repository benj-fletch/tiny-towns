package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `Populates an empty board on initialisation`() {
        val board = Board()
        assertThat(board.locations).hasSize(16)
        assertThat(board.locations.values).allMatch { it == Content(null) }

        val possibleCoordinates = (1..4).flatMap { x -> (1..4).map { y -> x to y} }
        assertThat(board.locations.keys).containsExactlyInAnyOrderElementsOf(possibleCoordinates)
    }

    @Test
    fun `Has configurable size`() {
        val oneByOneBoard = Board(1)
        assertThat(oneByOneBoard.locations).hasSize(1)

        val tenByTenBoard = Board(10)
        assertThat(tenByTenBoard.locations).hasSize(100)
    }

    @Test
    fun `Handles 0 size board`() {
        assertThatCode { Board(0) }
                .isInstanceOf(BoardException::class.java)
                .hasMessage("Board size 0 is invalid. Must be > 1.")
    }

    @Test
    fun `Handles negative size board`() {
        assertThatCode { Board(-1) }
                .isInstanceOf(BoardException::class.java)
                .hasMessage("Board size -1 is invalid. Must be > 1.")

        assertThatCode { Board(-100) }
                .isInstanceOf(BoardException::class.java)
                .hasMessage("Board size -100 is invalid. Must be > 1.")
    }
}