package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.GameGridException

typealias GameGrid = Map<Location, GamePiece>
typealias MutableGameGrid = MutableMap<Location, GamePiece>

/** determine what [GamePieces][GamePiece] are adjacent to [location] */
fun GameGrid.adjacentPieces(location: Location): List<GamePiece> {
    return location.adjacent().map { this.getOrDefault(it, EmptySpace) }
}

fun GameGrid.surroundingSpaces(location: Location): GameGrid {
    val surroundingLocs = location.surrounding()
    return filterKeys { surroundingLocs.contains(it) }
}

/** Helper method to get the contents of a row on the board that contains [location] */
fun GameGrid.row(location: Location): GameGrid = this.filter { it.key.y == location.y }

/** Helper method to get the contents of a column on the board that contains [location] */
fun GameGrid.col(location: Location): GameGrid = this.filter { it.key.x == location.x }

/** Helper method to get the "smallest" Location (closest to origin) */
fun GameGrid.minLocation(): Location {
    return keys.minOrNull() ?: throw GameGridException("GameGrid has no entries. Has it been initialised?")
}

/** Helper method to get the "smallest" Location (closest to origin) */
fun GameGrid.maxLocation(): Location {
    return keys.maxOrNull() ?: throw GameGridException("GameGrid has no entries. Has it been initialised?")
}

/** Helper method which determines the central [Locations][Location] in a given Map of Locations. */
fun GameGrid.centerSpaces(): GameGrid {
    return this
            .filter { it.key.x != minLocation().x && it.key.y != minLocation().y }
            .filter { it.key.x != maxLocation().x && it.key.y != maxLocation().y }
}

fun GameGrid.cornerSpaces(): GameGrid {
    val validX = listOf(minLocation().x, maxLocation().x)
    val validY = listOf(minLocation().y, maxLocation().y)
    return this
            .filter { validX.contains(it.key.x) && validY.contains(it.key.y) }
}
