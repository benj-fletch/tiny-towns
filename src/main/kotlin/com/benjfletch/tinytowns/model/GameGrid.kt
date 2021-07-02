package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.GameGridException
import com.benjfletch.tinytowns.model.buildings.Building

typealias GameGrid = Map<Location, GamePiece>
typealias MutableGameGrid = MutableMap<Location, GamePiece>

/** determine what [GamePieces][GamePiece] are adjacent to [location] */
fun GameGrid.adjacentPieces(location: Location): List<GamePiece> {
    return location.adjacent().map { this.getOrDefault(it, EmptySpace) }
}

/** Helper method which returns the 8 [Locations][Location] around the provided [location] */
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
fun GameGrid.centerSpaces(): Map<Location, GamePiece> {
    return this
            .filter { it.key.x != minLocation().x && it.key.y != minLocation().y }
            .filter { it.key.x != maxLocation().x && it.key.y != maxLocation().y }
}

/** Helper method which determines the corner [Locations][Location] in a given Map of Locations */
fun GameGrid.cornerSpaces(): Map<Location, GamePiece> {
    val validX = listOf(minLocation().x, maxLocation().x)
    val validY = listOf(minLocation().y, maxLocation().y)
    return this
            .filter { validX.contains(it.key.x) && validY.contains(it.key.y) }
}

/** Helper method which counts the instances of a given [Building] type on the grid */
fun GameGrid.countPieces(building: Building): Int {
    return this.values
            .filter { it::class.isInstance(building) }
            .count()
}

fun GameGrid.contiguousGroupsOf(targetPiece: GamePiece): Set<Set<Location>> {
    val contiguousGroups = mutableSetOf<MutableSet<Location>>()
    val toVisit = this.filterValues { targetPiece::class.isInstance(it) }.keys.toMutableSet()

    while (toVisit.isNotEmpty()) {
        val group = mutableSetOf<Location>()
        visit(toVisit.first(), group, toVisit, targetPiece)
        contiguousGroups.add(group)
    }
    return contiguousGroups
}

fun GameGrid.visit(loc: Location, visited: MutableSet<Location>, toVisit: MutableSet<Location>, targetPiece: GamePiece) {
    visited.add(loc)
    toVisit.remove(loc)
    if (toVisit.isNotEmpty()) {
        this.filterKeys { loc.adjacent().contains(it) }
                .filter { (_, piece) -> targetPiece::class.isInstance(piece) }
                .minus(visited)
                .forEach { visit(it.key, visited, toVisit, targetPiece) }
    }
}
