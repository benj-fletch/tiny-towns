package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException
import com.benjfletch.tinytowns.model.buildings.Cottage
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setupBoard() {
        board = Board()
    }

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
    fun `Converts IntRanges of same sizes to Locations`() {
        val x = IntRange(1, 2)
        val y = IntRange(1, 2)
        val expected = listOf(Location(1, 1), Location(1, 2), Location(2, 1), Location(2, 2))
        val actual = board.rangesAsLocations(x, y)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun `Converts IntRanges of different sizes to Locations`() {
        val x = IntRange(1, 1)
        val y = IntRange(1, 2)
        val expected = listOf(Location(1, 1), Location(1, 2))
        val actual = board.rangesAsLocations(x, y)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun `Places Resource at specified location`() {
        val board = Board()
        val resource = Resource.BRICK
        board.place(Location(0, 0), resource)

        assertThat(board.spaces[Location(0, 0)]).isEqualTo(resource)
    }

    @Test
    fun `Places Building at specified location`() {
        val board = Board()
        val building = Cottage
        board.place(Location(0, 0), Cottage)

        assertThat(board.spaces[Location(0, 0)]).isEqualTo(building)
    }

    @Test
    fun `Throws Exception when placing to a which location is not on the board`() {
        val board = Board()
        val resource = Resource.BRICK
        assertThrowsBoardException("-1:0 is out of bounds.") { board.place(Location(-1, 0), resource) }
        assertThrowsBoardException("0:-1 is out of bounds.") { board.place(Location(0, -1), resource) }
        assertThrowsBoardException("-1:-1 is out of bounds.") { board.place(Location(-1, -1), resource) }
        assertThrowsBoardException("5:0 is out of bounds.") { board.place(Location(5, 0), resource) }
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
    fun `Removes single GamePiece from specified location`() {
        fail("Not Implemented")
    }

    @Test
    fun `Removes multiple GamePieces from specified Locations`() {
        fail("Not Implemented")
    }

    @Test
    fun `Remove handles space already being empty`() {
        fail("Not Implemented")
    }

    @Test
    fun `Throws Exception when trying to remove from an out of bounds location`() {
        fail("Not Implemented")
    }

    @Test
    fun `Builds a building`() {
        fail("Not Implemented")
    }

    @Test
    fun `Throws exception when trying to build building at out of bounds location`() {
        fail("Not Implemented")
    }

    @Test
    fun `Throws Exception when Matrix does not match Building Shape`() {
        fail("Not Implemented")
    }

    @Test
    fun `Removes all resources used for building when build is valid`() {
        fail("Not Implemented")
    }

    @Test
    fun `Places building at targetLocation when build is valid`() {
        fail("Not Implemented")
    }

    @Test
    fun `Can convert from Location-Resource map to Resource Matrix`() {
        fail("Not Implemented")
    }

    @Test
    fun `Throws exception when at least one component location is out of bounds`() {
        fail("Not Implemented")
    }

    @Test
    fun `Throws Exception when Target location is not in componentLocations list`() {
        fail("Not Implemented")
    }

    @Test
    fun `Considers build valid when targetLocation is in componentLocations`() {
        fail("Not Implemented")
    }

    @Test
    fun `Considers build valid when targetLocation is not in componentLocations and Building can be built anywhere`() {
        fail("Not Implemented")
    }

    @Test
    fun `Check location is on board does not throw exception when location is on board`() {
        fail("Not Implemented")
    }

    @Test
    fun `Check location is on board throws exception when location is not on board`() {
        fail("Not Implemented")
    }

    @Test
    fun `Check location is occupied does not throw exception when location is unoccupied`() {
        fail("Not Implemented")
    }

    @Test
    fun `Check location is occupied throws exception when location is occupied`() {
        fail("Not Implemented")
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
