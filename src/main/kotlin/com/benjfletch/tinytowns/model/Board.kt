package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException
import com.benjfletch.tinytowns.model.buildings.Building

/** Representation of the game board, with a configurable size */
data class Board(private val size: Int = 4) {
    val spaces: MutableMap<Location, GamePiece> = mutableMapOf()

    init {
        if (size < 1) throw BoardException("Board size $size is invalid. Must be > 1.")
        val boardSize = IntRange(0, size)
        rangesAsLocations(boardSize, boardSize)
                .map { it to EmptySpace }
                .toMap(spaces)
    }

    /** convert two ranges of Ints, representing x and y, to a [List] of [Locations][Location] */
    fun rangesAsLocations(xRange: IntRange, yRange: IntRange): List<Location> = xRange.flatMap { x -> yRange.map { y -> Location(x, y) } }

    /**
     * Place a [GamePiece] on to the board. This is only valid if:
     *
     * * [location] is a valid space on the board
     * * [location] is not already occupied by a [GamePiece]
     *
     * in which case a [BoardException] is thrown.
     */
    fun place(location: Location, piece: GamePiece) {
        checkLocationIsOnBoard(location)
        checkLocationIsUnoccupied(location)
        spaces[location] = piece
    }

    /** Set all [locations] on this [Board] to [EmptySpace] */
    fun remove(locations: Iterable<Location>) {
        locations.forEach { remove(it) }
    }

    /** Set [location] on this [Board] to [EmptySpace] */
    fun remove(location: Location) {
        checkLocationIsOnBoard(location)
        spaces[location] = EmptySpace
    }

    /**
     * Converts a set of [Resources][Resource] into a [Building], provided that:
     * * [targetLocation] is on this [Board]
     * * [targetLocation] is a valid build location, according to [checkBuildLocationValid]
     * * [components] is a valid [Shape] orientation of [targetBuilding]
     *
     * All [Resources][Resource] in [components] will be replaced with [EmptySpace] and [targetBuilding] will
     * be placed at [targetLocation].
     */
    fun build(components: Map<Location, Resource>, targetLocation: Location, targetBuilding: Building) {
        checkLocationIsOnBoard(targetLocation)
        checkBuildLocationValid(components.keys, targetLocation, targetBuilding)
        targetBuilding.matrixMatches(components.toResourceMatrix())
        remove(components.keys)
        place(targetLocation, targetBuilding)
    }

    /**
     * Convert from a [Map] of occupied spaces to a [ResourceMatrix], filling in any [Locations][Location] which aren't
     * populated with [Resource.NONE] such that the [ResourceMatrix] has a [Resource] at every location.
     *
     * The following logic is applied here:
     * 1. Minimum and maximum x and y coordinates are calculated, to give the size of the [ResourceMatrix]
     * 1. Any [Location] which has an x or y within the [Matrix] size but is not in the map is added, as a mapping of
     *    [Location](x, y) -> [Resource.NONE]. This covers spaces which are in the matrix but aren't part of the
     *    building [Resources][Resource]
     */
    fun Map<Location, Resource>.toResourceMatrix(): ResourceMatrix {
        val xRange = IntRange(keys.minOf { it.x }, keys.maxOf { it.x })
        val yRange = IntRange(keys.minOf { it.y }, keys.maxOf { it.y })

        val filledComponents = toMutableMap()
        rangesAsLocations(xRange, yRange)
                .forEach { filledComponents.putIfAbsent(it, Resource.NONE) }

        return filledComponents.entries.groupBy { it.key.y }
                .entries.sortedByDescending { it.key }
                .map { it.value.map { it.value } }
    }

    /**
     * Check that the provided [Location] is within the bounds of this [Board]
     * @throws BoardException when [location] has either x or y out of bounds.
     */
    private fun checkLocationIsOnBoard(location: Location) {
        if (!spaces.containsKey(location)) {
            throw BoardException("$location is out of bounds.")
        }
    }

    /**
     * Check that the provided [Location] is within the bounds of this board (via [checkLocationIsOnBoard]) and that it
     * is currently an [EmptySpace].
     * @throws BoardException when location is out of bounds or [location] is occupied by anything except [EmptySpace]
     */
    private fun checkLocationIsUnoccupied(location: Location) {
        checkLocationIsOnBoard(location)
        if (spaces[location] !=  EmptySpace) {
            throw BoardException("$location is occupied by ${spaces[location]?.pieceName}.")
        }
    }

    /**
     * Checks that the [targetLocation] is a valid place for this [building] by performing the following logic:
     * 1. Are all [componentLocations] are within the bounds of this [Board]?
     * 2. Is [targetLocation] within [componentLocations]?
     * 3. If not, can [building] be placed anywhere and is [targetLocation] occupied by [EmptySpace]?
     * 4. If not, throw [BoardException]?
     *
     * Other logic paths are considered to be valid placements.
     * @throws BoardException when [targetLocation] is invalid
     */
    private fun checkBuildLocationValid(componentLocations: Iterable<Location>, targetLocation: Location, building: Building) {
        componentLocations.forEach { checkLocationIsOnBoard(it) }

        if (!componentLocations.contains(targetLocation)) {
            if (building.canBeBuiltAnywhere) {
                checkLocationIsUnoccupied(targetLocation)
            } else {
                throw BoardException("Cannot build at $targetLocation. Location has to be one of ${componentLocations.joinToString(",")}")
            }
        }
    }
}
