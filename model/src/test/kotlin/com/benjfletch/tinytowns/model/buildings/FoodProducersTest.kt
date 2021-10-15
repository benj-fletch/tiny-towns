package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.EmptySpace
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.col
import com.benjfletch.tinytowns.model.getSerializer
import com.benjfletch.tinytowns.model.row
import com.benjfletch.tinytowns.model.surroundingSpaces
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class FoodProducersTest {
    private lateinit var size3Board: Board
    private lateinit var size4Board: Board

    @BeforeEach
    fun setupBoard() {
        size3Board = Board(3)
        size4Board = Board()
    }

    @Test
    fun `Orchard feeds all buildings in the same row and column`() {
        val orchardLocation = Location(2, 2)
        val sameRowAndColAsOrchard = rowAndColumnOf(orchardLocation)
        sameRowAndColAsOrchard.remove(orchardLocation)

        size3Board.place(orchardLocation, Orchard())
        fillEmptySpacesWithUnfedCottages(size3Board)
        size3Board.feed()
        assertLocationsHaveBeenFed(size3Board, sameRowAndColAsOrchard.map { it.toString() })
    }

    @Test
    fun `Orchard does not feed buildings in other rows and columns`() {
        val orchardLocation = Location(2, 2)

        size3Board.place(orchardLocation, Orchard())
        fillEmptySpacesWithUnfedCottages(size3Board)
        size3Board.feed()
        assertLocationsHaveNotBeenFed(size3Board, listOf(
                Location(1, 1).toString(),
                Location(3, 3).toString(),
                Location(1, 3).toString(),
                Location(3, 1).toString()))
    }

    @Test
    fun `Orchard can handle other buildings being in feedable spaces`() {
        val orchardLocation = Location(2, 2)
        val sameRowAndColAsOrchard = rowAndColumnOf(orchardLocation)
        sameRowAndColAsOrchard.remove(orchardLocation)
        sameRowAndColAsOrchard.forEach { size3Board.place(it, Shed()) }
        assertThatCode { size3Board.feed() }.doesNotThrowAnyException()
    }

    @Test
    fun `Orchard can handle not having any feedable buildings in a feedable space`() {
        size3Board.place(Location(1, 1), Orchard())
        assertThatCode { size3Board.feed() }.doesNotThrowAnyException()
    }

    @Test
    fun `Granary feeds buildings in the 8 surrounding squares`() {
        size3Board.place(Location(2, 2), Granary())
        val cottageLocations = fillEmptySpacesWithUnfedCottages(size3Board)
        size3Board.feed()
        assertLocationsHaveBeenFed(size3Board, cottageLocations.map { it.toString() })
    }

    @Test
    fun `Granary does not feed buildings in squares not surrounding it`() {
        val granaryLocation = Location(1, 1)
        val spacesSurroundingGranary = size3Board.gameGrid.surroundingSpaces(granaryLocation).keys
        val spacesNotSurroundingGranary = rowAndColumnOf(Location(3, 3))

        size3Board.place(granaryLocation, Granary())
        fillEmptySpacesWithUnfedCottages(size3Board)
        size3Board.feed()
        assertLocationsHaveBeenFed(size3Board, spacesSurroundingGranary)
        assertLocationsHaveNotBeenFed(size3Board, spacesNotSurroundingGranary.map { it.toString() })
    }

    @Test
    fun `Granary can handle other buildings being in feedable spaces`() {
        size3Board.place(Location(1, 1), Granary())
        size3Board.place(Location(1, 2), Shed())
        size3Board.place(Location(2, 1), UnfedCottage())
        assertThatCode { size3Board.feed() }.doesNotThrowAnyException()
        assertLocationsHaveBeenFed(size3Board, listOf(Location(2, 1).toString()))
    }

    @Test
    fun `Granary can handle not having any feedable buildings in a feedable space`() {
        size3Board.place(Location(1, 1), Granary())
        assertThatCode { size3Board.feed() }.doesNotThrowAnyException()
    }

    @Test
    fun `Farm feeds 4 buildings in any squares on the board`() {
        val farmLocation = Location(1, 1)
        val cottageLocations = listOf(
                Location(3, 1),
                Location(3, 2),
                Location(3, 3),
                Location(2, 1))
        size3Board.place(farmLocation, Farm())
        cottageLocations.forEach { size3Board.place(it, UnfedCottage()) }
        size3Board.feed()
        assertLocationsHaveBeenFed(size3Board, cottageLocations.map { it.toString() })
    }

    @Test
    fun `Farm does not feed more than 4 buildings`() {
        val farmLocation = Location(1, 1)
        size3Board.place(farmLocation, Farm())
        fillEmptySpacesWithUnfedCottages(size3Board)
        size3Board.feed()
        val numberOfFed = size3Board.gameGrid
                .filter { (_, piece) -> piece is FedCottage }
                .count()
        assertThat(numberOfFed).isEqualTo(4)
    }

    @Test
    fun `2 farms can feed 8 buildings`() {
        val size5Board = Board(5)
        val farmLocations = listOf(Location(1, 1), Location(1, 2))
        farmLocations.forEach { size5Board.place(it, Farm()) }
        fillEmptySpacesWithUnfedCottages(size5Board)
        size5Board.feed()
        val numberOfFed = size5Board.gameGrid
                .filter { (_, piece) -> piece is FedCottage }
                .count()
        assertThat(numberOfFed).isEqualTo(8)
    }

    @Test
    fun `Farm can handle not having any feedable buildings on the board`() {
        size3Board.place(Location(1, 1), Farm())
        assertThatCode { size3Board.feed() }.doesNotThrowAnyException()
    }


    @Test
    fun `Greenhouse feeds a single contiguous group of feedable buildings`() {
        val greenhouseLocation = Location(1, 1)
        size3Board.place(greenhouseLocation, Greenhouse())
        val cottageLocations = fillEmptySpacesWithUnfedCottages(size3Board)
        size3Board.feed()
        assertLocationsHaveBeenFed(size3Board, cottageLocations.map { it.toString() })
    }

    @Test
    fun `Greenhouse feeds the largest contiguous group of feedable buildings`() {
        val greenhouseLocation = Location(1, 1)
        val expectedFedLocations = listOf(Location(1, 2), Location(1, 3))
        val expectedUnfedLocations = listOf(Location(3, 3))
        expectedFedLocations.forEach { size3Board.place(it, UnfedCottage()) }
        expectedUnfedLocations.forEach { size3Board.place(it, UnfedCottage()) }
        size3Board.place(greenhouseLocation, Greenhouse())
        size3Board.feed()
        assertLocationsHaveBeenFed(size3Board, expectedFedLocations.map { it.toString() })
        assertLocationsHaveNotBeenFed(size3Board, expectedUnfedLocations.map { it.toString() })
    }

    @Test
    fun `Greenhouse can handle not having any feedable buildings on the board`() {
        size3Board.place(Location(1, 1), Greenhouse())
        assertThatCode { size3Board.feed() }.doesNotThrowAnyException()
    }

    @Test
    fun `Orchard can be serialized and deserialized`() {
        val orchard = Orchard()
        val serialized = getSerializer().encodeToString(orchard)
        val deserialized = getSerializer().decodeFromString<Orchard>(serialized)
        assertThat(orchard).isEqualTo(deserialized)
    }

    @Test
    fun `Granary can be serialized and deserialized`() {
        val granary = Granary()
        val serialized = getSerializer().encodeToString(granary)
        val deserialized = getSerializer().decodeFromString<Granary>(serialized)
        assertThat(granary).isEqualTo(deserialized)
    }

    @Test
    fun `Farm can be serialized and deserialized`() {
        val farm = Farm()
        val serialized = getSerializer().encodeToString(farm)
        val deserialized = getSerializer().decodeFromString<Farm>(serialized)
        assertThat(farm).isEqualTo(deserialized)
    }

    @Test
    fun `Greenhouse can be serialized and deserialized`() {
        val greenhouse = Greenhouse()
        val serialized = getSerializer().encodeToString(greenhouse)
        val deserialized = getSerializer().decodeFromString<Greenhouse>(serialized)
        assertThat(greenhouse).isEqualTo(deserialized)
    }

    private fun rowAndColumnOf(location: Location): MutableSet<Location> {
        return size3Board.gameGrid.row(location).plus(size3Board.gameGrid.col(location)).keys.map { Location.fromString(it) }.toMutableSet()
    }

    private fun fillEmptySpacesWithUnfedCottages(board: Board): Set<Location> {
        val cottageLocations = mutableSetOf<Location>()
        board.gameGrid
                .filter { (_, piece) -> piece is EmptySpace }
                .forEach { (location, _) ->
                    board.place(location, UnfedCottage())
                    cottageLocations.add(Location.fromString(location))
                }
        return cottageLocations
    }

    private fun assertLocationsHaveBeenFed(board: Board, toCheck: Collection<String>) {
        toCheck.forEach { location ->
            val pieceAtLocation = board.gameGrid[location]
            if (pieceAtLocation is FeedableBuilding) {
                assertThat(pieceAtLocation.isFed)
                        .withFailMessage("Expected ${pieceAtLocation.pieceName} at $location to be fed, but was not.")
                        .isTrue()
            } else {
                fail("Expected $pieceAtLocation to be a Feedable building, but was not")
            }
        }
    }

    private fun assertLocationsHaveNotBeenFed(board: Board, toCheck: Collection<String>) {
        toCheck.forEach { location ->
            val pieceAtLocation = board.gameGrid[location]
            if (pieceAtLocation is FeedableBuilding) {
                assertThat(pieceAtLocation.isFed)
                        .withFailMessage("Expected ${pieceAtLocation.pieceName} at $location to not be fed, but was not.")
                        .isFalse()
            }
        }
    }
}
