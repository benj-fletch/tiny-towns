package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException
import com.benjfletch.tinytowns.model.buildings.Cottage
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `Populates an empty board on initialisation`() {
        val board = Board()
        assertThat(board.spaces).hasSize(16)
        assertThat(board.spaces.values).allMatch { it is EmptySpace }

        val possibleCoordinates = (0 until 4).flatMap { x -> (0 until 4).map { y -> Location(x, y) } }
        assertThat(board.spaces.keys).containsExactlyInAnyOrderElementsOf(possibleCoordinates)
    }

    @Test
    fun `Has configurable size`() {
        val oneByOneBoard = Board(1)
        assertThat(oneByOneBoard.spaces).hasSize(1)

        val tenByTenBoard = Board(10)
        assertThat(tenByTenBoard.spaces).hasSize(100)
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
    fun `Places Resource at provided location`() {
        val board = Board()
        val resource = Resource.BRICK
        board.place(Location(0, 0), resource)

        assertThat(board.spaces[Location(0, 0)]).isEqualTo(resource)
    }

    @Test
    fun `Throws Exception when placing to a which location is not on the board`() {
        val board = Board()
        val resource = Resource.BRICK
        assertThrowsBoardException("-1:0 is out of bounds.") { board.place(Location(-1, 0), resource) }
        assertThrowsBoardException("0:-1 is out of bounds.") { board.place(Location(0, -1), resource) }
        assertThrowsBoardException("-1:-1 is out of bounds.") { board.place(Location(-1, -1), resource) }
        assertThrowsBoardException("5:0 is out of bounds.") { board.place(Location(5, 0 ), resource) }
        assertThrowsBoardException("0:5 is out of bounds.") { board.place(Location(0, 5), resource) }
        assertThrowsBoardException("5:5 is out of bounds.") { board.place(Location(5, 5), resource) }
    }

    @Test
    fun `Throws Exception when placing an object on an occupied location`() {
        val board = Board()
        val resource = Resource.BRICK
        board.place(Location(0, 0), resource)

        assertThrowsBoardException("0:0 is occupied by ${resource.pieceName}.") { board.place(Location(0, 0), resource) }
    }

    @Test
    fun `builds`() {
        val components = mapOf(Location(5, 3) to Resource.GLASS, Location(5, 4) to Resource.BRICK, Location(6, 3) to Resource.WHEAT)
        val components2 = mapOf(Location(3, 3) to Resource.GLASS, Location(3, 4) to Resource.BRICK)
        Board(7).build(components, Location(5, 3), Cottage)
        Board(5).build(components2, Location(3, 3), Cottage)
    }


    private fun assertThrowsBoardException(message: String, code: () -> Unit) {
        assertThatCode { code.invoke() }
                .isInstanceOf(BoardException::class.java)
                .hasMessage(message)

    }
}
