package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException
import com.benjfletch.tinytowns.BuildingException
import com.benjfletch.tinytowns.model.buildings.TestAnywhereBuilding
import com.benjfletch.tinytowns.model.buildings.TestBuilding
import com.benjfletch.tinytowns.model.buildings.UnfedCottage
import com.benjfletch.tinytowns.model.buildings.monument.Monument
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board

    private val origin = Location(1, 1)
    private val oneOne = Location(2, 2)
    private val originString = Location(1, 1).toString()
    private val oneOneString = Location(2, 2).toString()
    private val outOfBounds = Location(100, 100)

    private val cottageResources = mapOf(Location(1, 1) to BRICK(), Location(2, 1) to GLASS(), Location(2, 2) to WHEAT())
    private val cottageTargetLoc = oneOne


    @BeforeEach
    fun setupBoard() {
        board = Board()
    }

    @Test
    fun `Populates an empty board on initialisation`() {
        assertThat(board.gameGrid).hasSize(16)
        assertThat(board.gameGrid.values).allMatch { it is EmptySpace }

        val possibleCoordinates = (1 .. 4).flatMap { x -> (1 .. 4).map { y -> Location(x, y).toString() } }
        assertThat(board.gameGrid.keys).containsExactlyInAnyOrderElementsOf(possibleCoordinates)
    }

    @Test
    fun `Has configurable size`() {
        val oneByOneBoard = Board(1)
        assertThat(oneByOneBoard.gameGrid).hasSize(1)

        val tenByTenBoard = Board(10)
        assertThat(tenByTenBoard.gameGrid).hasSize(100)
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
    fun `Places Resource at specified location`() {
        val resource = BRICK()
        board.place(origin, resource)

        assertThat(board.gameGrid[originString]).isEqualTo(resource)
    }

    @Test
    fun `Places Building at specified location`() {
        val building = UnfedCottage()
        board.place(origin, UnfedCottage())

        assertThat(board.gameGrid[originString]).isEqualTo(building)
    }

    @Test
    fun `Throws Exception when placing to a which location is not on the board`() {
        val resource = BRICK()
        assertThrowsBoardException("-1:0 is out of bounds.") { board.place(Location(-1, 0), resource) }
        assertThrowsBoardException("0:-1 is out of bounds.") { board.place(Location(0, -1), resource) }
        assertThrowsBoardException("-1:-1 is out of bounds.") { board.place(Location(-1, -1), resource) }
        assertThrowsBoardException("5:0 is out of bounds.") { board.place(Location(5, 0), resource) }
        assertThrowsBoardException("0:5 is out of bounds.") { board.place(Location(0, 5), resource) }
        assertThrowsBoardException("5:5 is out of bounds.") { board.place(Location(5, 5), resource) }
    }

    @Test
    fun `Throws Exception when placing an object on an occupied location`() {
        val resource = BRICK()
        board.place(origin, resource)

        assertThrowsBoardException("$origin is occupied by ${resource.pieceName}.") {
            board.place(origin, resource)
        }
    }

    @Test
    fun `Removes single GamePiece from specified location`() {
        assertThat(board.gameGrid[originString]).isEqualTo(EmptySpace())

        board.place(origin, BRICK())
        assertThat(board.gameGrid[originString]).isEqualTo(BRICK())

        board.remove(origin)
        assertThat(board.gameGrid[originString]).isEqualTo(EmptySpace())
    }

    @Test
    fun `Removes multiple GamePieces from specified Locations`() {
        assertThat(board.gameGrid[originString]).isEqualTo(EmptySpace())
        assertThat(board.gameGrid[oneOneString]).isEqualTo(EmptySpace())

        board.place(origin, BRICK())
        board.place(oneOne, GLASS())
        assertThat(board.gameGrid[originString]).isEqualTo(BRICK())
        assertThat(board.gameGrid[oneOneString]).isEqualTo(GLASS())

        board.remove(listOf(origin, oneOne))
        assertThat(board.gameGrid[originString]).isEqualTo(EmptySpace())
        assertThat(board.gameGrid[oneOneString]).isEqualTo(EmptySpace())
    }

    @Test
    fun `Remove handles space already being empty`() {
        assertThat(board.gameGrid[originString]).isEqualTo(EmptySpace())
        assertThatCode { board.remove(origin) }.doesNotThrowAnyException()
        assertThat(board.gameGrid[originString]).isEqualTo(EmptySpace())
    }

    @Test
    fun `Throws Exception when removing from an out of bounds location`() {
        assertThrowsBoardException("100:100 is out of bounds.") { board.remove(outOfBounds) }
    }

    @Test
    fun `Builds a building`() {
        buildCottage()
    }

    @Test
    fun `Removes all resources used for building when build is valid`() {
        cottageResources
                .filter { it.key != cottageTargetLoc }
                .forEach { assertThat(board.gameGrid[it.key.toString()]).isEqualTo(EmptySpace()) }
    }

    @Test
    fun `Places building at targetLocation when build is valid`() {
        buildCottage()
        assertThat(board.gameGrid[cottageTargetLoc.toString()]).isEqualTo(UnfedCottage())
    }

    @Test
    fun `Throws exception when trying to build building at out of bounds location`() {
        assertThrowsBoardException("100:100 is out of bounds.") {
            board.build(emptyMap(), outOfBounds, TestBuilding)
        }
    }

    @Test
    fun `Throws Exception when Matrix does not match Building Shape`() {
        val nonMatchingMatrix = mapOf(Location(1, 1) to WHEAT())
        val targetLoc = Location(1, 1)
        assertThatCode { board.build(nonMatchingMatrix, targetLoc, UnfedCottage()) }
                .isInstanceOf(BuildingException::class.java)
                .hasMessageContaining("Resources [[WHEAT(pieceName=wheat)]] in this configuration cannot be used to build Cottage")
    }

    @Test
    fun `Throws exception when at least one component location is out of bounds`() {
        val components = mapOf(outOfBounds to GLASS())
        assertThrowsBoardException("100:100 is out of bounds") {
            board.build(components, origin, TestBuilding)
        }
    }

    @Test
    fun `Throws Exception when Target location is not in componentLocations list`() {
        assertThrowsBoardException("Cannot build at 3:3. Location has to be one of 1:1,2:1,2:2") {
            board.build(cottageResources, Location(3, 3), TestBuilding)
        }
    }

    @Test
    fun `Considers build valid when targetLocation is not in componentLocations and Building can be built anywhere`() {
        val targetLoc = Location(2, 2)

        assertThatCode { board.build(mapOf(origin to GLASS()), targetLoc, TestAnywhereBuilding) }
                .doesNotThrowAnyException()
        assertThat(board.gameGrid[targetLoc.toString()]).isEqualTo(TestAnywhereBuilding)
    }

    @Test
    fun `Throws exception when building can be built anywhere but target is occupied`() {
        val targetLoc = Location(2, 2)
        board.place(targetLoc, GLASS())

        assertThatCode { board.build(mapOf(origin to GLASS()), targetLoc, TestAnywhereBuilding) }
                .isInstanceOf(BoardException::class.java)
                .hasMessageContaining("$targetLoc is occupied by ${GLASS().pieceName}")
    }

    @Test
    fun `Clears the whole board`() {
        buildCottage()
        assertThat(board.gameGrid[cottageTargetLoc.toString()]).isEqualTo(UnfedCottage())
        board.clear()
        board.gameGrid.forEach {
            assertThat(board.gameGrid[it.key]).isEqualTo(EmptySpace())
        }
    }

    @Test
    fun `Can be serialized and deserialized`() {
        val board = Board()
        val serialized = getSerializer().encodeToString(board)
        val deserialized = getSerializer().decodeFromString<Board>(serialized)
        assertThat(board).isEqualTo(deserialized)
    }

    @Test
    fun `calls onBuild for monument constructions`() {
        val board = Board()
        val monument = object: Monument {
            override val pieceName = "test"
            override val text = ""
            override val shape = Shape(listOf(listOf(GLASS())))
            var hasBeenBuilt = false

            override fun onBuild(gameGrid: GameGrid) {
                hasBeenBuilt = true
            }
        }
        assertThat(monument.hasBeenBuilt).isFalse
        board.place(oneOne, GLASS())
        board.build(mapOf(oneOne to GLASS()), oneOne, monument)
        assertThat(monument.hasBeenBuilt).isTrue
    }

    private fun buildCottage() {
        cottageResources.forEach { board.place(it.key, it.value) }
        board.build(cottageResources, cottageTargetLoc, UnfedCottage())
    }

    private fun assertThrowsBoardException(message: String, code: () -> Unit) {
        assertThatCode { code.invoke() }
                .isInstanceOf(BoardException::class.java)
                .hasMessageContaining(message)
    }
}
