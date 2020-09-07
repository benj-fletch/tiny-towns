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
        assertThat(board.locations.values).allMatch { it == null }

        val possibleCoordinates = (0 until 4).flatMap { x -> (0 until 4).map { y -> Location(x, y) } }
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

    @Test
    fun `Can place Resource at provided location`() {
        val board = Board()
        val resource = Resource.BRICK
        board.place(Location(0, 0), resource)

        assertThat(board.locations[Location(0, 0)]).isEqualTo(resource)
    }

    @Test
    fun `Errors when placing to a which location is not on the board`() {
        val board = Board()
        val resource = Resource.BRICK
        assertThrowsBoardException("Cannot place ${resource.pieceName} at -1:0. Out of bounds.") { board.place(Location(-1, 0), resource) }
        assertThrowsBoardException("Cannot place ${resource.pieceName} at 0:-1. Out of bounds.") { board.place(Location(0, -1), resource) }
        assertThrowsBoardException("Cannot place ${resource.pieceName} at -1:-1. Out of bounds.") { board.place(Location(-1, -1), resource) }
        assertThrowsBoardException("Cannot place ${resource.pieceName} at 5:0. Out of bounds.") { board.place(Location(5, 0 ), resource) }
        assertThrowsBoardException("Cannot place ${resource.pieceName} at 0:5. Out of bounds.") { board.place(Location(0, 5), resource) }
        assertThrowsBoardException("Cannot place ${resource.pieceName} at 5:5. Out of bounds.") { board.place(Location(5, 5), resource) }
    }

    @Test
    fun `Errors when placing an object on an occupied location`() {
        val board = Board()
        val resource = Resource.BRICK
        board.place(Location(0, 0), resource)

        assertThrowsBoardException("Cannot place ${resource.pieceName} at 0:0. Place occupied.") { board.place(Location(0, 0), resource) }
    }

    private fun assertThrowsBoardException(message: String, code: () -> Unit) {
        assertThatCode { code.invoke() }
                .isInstanceOf(BoardException::class.java)
                .hasMessage(message)

    }
}
