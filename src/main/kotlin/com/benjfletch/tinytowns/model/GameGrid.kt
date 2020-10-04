package com.benjfletch.tinytowns.model

typealias GameGrid = Map<Location, GamePiece>
typealias MutableGameGrid = MutableMap<Location, GamePiece>

/** determine what [GamePieces][GamePiece] are adjacent to [buildingLocation] */
fun GameGrid.adjacentPieces(buildingLocation: Location): List<GamePiece> {
    return buildingLocation.adjacent().map { this.getOrDefault(it, EmptySpace) }
}

/** Helper method to get the contents of a row on the board that contains [location] */
fun GameGrid.row(location: Location): GameGrid = this.filter { it.key.y == location.y }

/** Helper method to get the contents of a column on the board that contains [location] */
fun GameGrid.col(location: Location): GameGrid = this.filter { it.key.x == location.x }

/** Helper method which determines the central [Locations][Location] in a given Map of Locations. */
fun GameGrid.centerSpaces(): GameGrid {
    val minLocation = this.keys.minOrNull()
    val maxLocation = this.keys.maxOrNull()
    return this
            .filter { it.key.x != minLocation?.x && it.key.y != minLocation?.y }
            .filter { it.key.x != maxLocation?.x && it.key.y != maxLocation?.y }
}
