package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.model.buildings.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameGridTest {
    private lateinit var size3GameGrid: GameGrid
    private lateinit var size4GameGrid: GameGrid
    private lateinit var size3Board: Board
    private lateinit var size4Board: Board

    @BeforeEach
    fun setupBoard() {
        size3Board = Board(3)
        size3GameGrid = Board(3).gameGrid
        size4Board = Board()
        size4GameGrid = Board().gameGrid
    }

    @Test
    fun `Determines adjacent pieces for given location`() {
        val expectedLocs = listOf(
                Location(1, 2),
                Location(2, 1),
                Location(2, 3),
                Location(3, 2)
        )
        expectedLocs.forEach { size3Board.place(it, Shed()) }
        val boardCentre = Location(2, 2)
        val actual = size3Board.gameGrid.adjacentPieces(boardCentre)
        assertThat(actual).allMatch { it == Shed() }
    }

    @Test
    fun `Determines surrounding spaces for given location`() {
        val expected = listOf(
                Location(1, 1).toString(),
                Location(2, 1).toString(),
                Location(3, 1).toString(),
                Location(1, 2).toString(),
                Location(3, 2).toString(),
                Location(1, 3).toString(),
                Location(2, 3).toString(),
                Location(3, 3).toString(),
        )
        val actual = size3GameGrid.surroundingSpaces(Location(2, 2)).keys
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun `Provides all locations in row given single location`() {
        val expected = mapOf(
                Location(1, 1).toString() to EmptySpace(),
                Location(2, 1).toString() to EmptySpace(),
                Location(3, 1).toString() to EmptySpace())
        val actual = size3GameGrid.row((Location(1, 1)))
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected)
    }

    @Test
    fun `Provides all locations in column given single location`() {
        val expected = mapOf(
                Location(1, 1).toString() to EmptySpace(),
                Location(1, 2).toString() to EmptySpace(),
                Location(1, 3).toString() to EmptySpace())
        val actual = size3GameGrid.col((Location(1, 1)))
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected)
    }

    @Test
    fun `Determines the minimum location on a game grid`() {
        val expected = Location(1, 1)
        val actual = size3GameGrid.minLocation()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Determines the maximum location on a game grid`() {
        val expected = Location(3, 3)
        val actual = size3GameGrid.maxLocation()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Determines the centre space in an odd sized game grid`() {
        val expected = mapOf(Location(2, 2) to EmptySpace())
        val actual = size3GameGrid.centerSpaces()
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected)
    }

    @Test
    fun `Determines the centre spaces in an even sized game grid`() {
        val expected = mapOf(
                Location(2, 2) to EmptySpace(),
                Location(2, 3) to EmptySpace(),
                Location(3, 2) to EmptySpace(),
                Location(3, 3) to EmptySpace())
        val actual = size4GameGrid.centerSpaces()
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected)
    }

    @Test
    fun `Determines the corner spaces in a game grid`() {
        val expected = mapOf(
                Location(1, 1) to EmptySpace(),
                Location(1, 3) to EmptySpace(),
                Location(3, 1) to EmptySpace(),
                Location(3, 3) to EmptySpace())
        val actual = size3GameGrid.cornerSpaces()
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected)
    }

    @Test
    fun `Counts the number of a given building accurately in a game grid`() {
        var board = Board(3)
        for (i in rangesAsLocations(1..3, 1..3)) {
            board.place(i, Shed())
        }
        assertThat(board.gameGrid.countPieces(Shed::class)).isEqualTo(9)

        board = Board(3)
        for (i in rangesAsLocations(2..3, 2..3)) {
            board.place(i, Shed())
        }
        assertThat(board.gameGrid.countPieces(Shed::class)).isEqualTo(4)
    }

    @Test
    fun `Correctly identifies a single contiguous group on the board`() {
        val board = Board(3)
        board.gameGrid.forEach { board.place(it.key, Shed()) }

        val contiguousShedGroups = board.gameGrid.contiguousGroupsOf(Shed()).map { it.map { it.toString() } }
        assertThat(contiguousShedGroups).hasSize(1)

        val shedGroup = contiguousShedGroups.first()
        assertThat(shedGroup).hasSize(9)
        assertThat(shedGroup).containsExactlyInAnyOrderElementsOf(board.gameGrid.keys)
    }

    @Test
    fun `Correctly identifies 2 contiguous groups on the board`() {
        val board = Board(3)
        val group1 = board.gameGrid.row(Location(1, 1))
        group1.mapKeys { Location.fromString(it.key) }.forEach { board.place(it.key, Shed()) }
        val group2 = board.gameGrid.row(Location(3, 3))
        group2.mapKeys { Location.fromString(it.key) }.forEach { board.place(it.key, Shed()) }

        val contiguousShedGroups = board.gameGrid.contiguousGroupsOf(Shed()).map { it.map { it.toString() } }
        assertThat(contiguousShedGroups).hasSize(2)

        assertThat(contiguousShedGroups).allMatch { it.size == 3 }

        contiguousShedGroups.forEach {
            if(it.containsAll(group1.keys)) {
                assertThat(it).containsExactlyInAnyOrderElementsOf(group1.keys)
            }
            else {
                assertThat(it).containsExactlyInAnyOrderElementsOf(group2.keys)
            }
        }
    }

    @Test
    fun `Correctly identifies a full row and column as contiguous`() {
        val board = Board(3)
        val rowAndCol = board.gameGrid.row(Location(1, 1)).plus(board.gameGrid.col(Location(1, 1)))
        rowAndCol.forEach { board.place(it.key, Shed()) }

        val contiguousShedGroups = board.gameGrid.contiguousGroupsOf(Shed()).map { it.map { it.toString() } }
        assertThat(contiguousShedGroups).hasSize(1)

        assertThat(contiguousShedGroups.first()).hasSize(5)
        assertThat(contiguousShedGroups.first()).containsExactlyInAnyOrderElementsOf(rowAndCol.keys)
    }
}
