package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.BoardException
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.buildings.Feeder
import kotlinx.serialization.Serializable

/** Representation of the game board, with a configurable size */
@Serializable
data class Board(val size: Int = 4) {
    val gameGrid: MutableMap<String, GamePiece> = mutableMapOf()

    init {
        if (size < 1) throw BoardException("Board size $size is invalid. Must be > 1")
        val boardSize = IntRange(1, size)
        rangesAsLocations(boardSize, boardSize)
                .map { it.toString() to EmptySpace() }
                .toMap(gameGrid)
    }

    /**
     * Place a [GamePiece] on to the board. This is only valid if:
     *
     * * [location] is a valid space on the board
     * * [location] is not already occupied by a [GamePiece]
     *
     * in which case a [BoardException] is thrown.
     */
    fun place(location: String, piece: GamePiece) {
        checkLocationIsOnBoard(location)
        checkLocationIsUnoccupied(location)
        gameGrid[location] = piece
    }

    fun place(location: Location, piece: GamePiece) {
        place(location.toString(), piece)
    }

    /** Sets all spaces on this [Board] to [EmptySpace] **/
    fun clear() {
        gameGrid.replaceAll { _, _ -> EmptySpace() }
    }

    /** Set all [locations] on this [Board] to [EmptySpace] */
    fun remove(locations: Iterable<Location>) {
        locations.forEach { remove(it) }
    }

    /** Set [location] on this [Board] to [EmptySpace] */
    fun remove(location: Location) {
        remove(location.toString())
    }

    /** Set [location] on this [Board] to [EmptySpace] */
    fun remove(location: String) {
        checkLocationIsOnBoard(location)
        gameGrid[location] = EmptySpace()
    }

    /**
     * Converts a set of [Resources][Resource] into a [Building], provided that:
     * * [targetLocation] is on this [Board]
     * * [targetLocation] is a valid build location, according to [checkBuildLocationValid]
     * * [components] is a valid [Shape] orientation of [targetBuilding]
     *
     * All [Resources][Resource] which should be removed during the building process in [components] will be replaced
     * with [EmptySpace] and [targetBuilding] will be placed at [targetLocation], providing a building is not already
     * in that [Location]
     */
    fun build(components: Map<Location, Resource>, targetLocation: Location, targetBuilding: Building) {
        checkLocationIsOnBoard(targetLocation)
        checkBuildLocationValid(components.keys, targetLocation, targetBuilding)
        checkResourcesAreAtLocations(components, targetLocation, targetBuilding)
        targetBuilding.matrixMatches(components.toResourceMatrix())
        components
                .filter { it.value.removeAfterBuild() }
                .forEach { remove(it.key) }
        place(targetLocation, targetBuilding)
    }

    fun build(components: Map<String, Resource>, targetLocation: String, targetBuilding: Building) {
        build(components.mapKeys { Location.fromString(it.key) }, Location.fromString(targetLocation), targetBuilding)
    }

    /**
     * Update the [gameGrid] with the fed variant of [FeedableBuildings][FeedableBuilding] by executing [Feeder.feed] for
     * each of the [Feeders][Feeder] in the current game state.
     */
    fun feed() {
        gameGrid.forEach { (loc, piece) ->
                    if(piece is Feeder) {
                        piece.feed(Location.fromString(loc), gameGrid)
                    }
                }
    }

    /**
     * Check that the provided [Location] is within the bounds of this [Board]
     * @throws BoardException when [location] has either x or y out of bounds.
     */
    private fun checkLocationIsOnBoard(location: Location) {
        checkLocationIsOnBoard(location.toString())
    }
    private fun checkLocationIsOnBoard(location: String) {
        if (!gameGrid.containsKey(location)) {
            throw BoardException("$location is out of bounds")
        }
    }

    /**
     * Check that the provided [Location] is within the bounds of this board (via [checkLocationIsOnBoard]) and that it
     * is currently an [EmptySpace].
     * @throws BoardException when location is out of bounds or [location] is occupied by anything except [EmptySpace]
     */
    private fun checkLocationIsUnoccupied(location: String) {
        checkLocationIsOnBoard(location)
        if (gameGrid[location] != EmptySpace()) {
            throw BoardException("$location is occupied by ${gameGrid[location]?.pieceName}")
        }
    }

    private fun checkLocationIsUnoccupied(location: Location) {
        checkLocationIsOnBoard(location.toString())
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

    private fun checkResourcesAreAtLocations(components: Map<Location, Resource>, targetLocation: Location, targetBuilding: Building) {
        if(components.any { gameGrid[it.key.toString()] != it.value }) {
            throw BoardException("Cannot build $targetBuilding at $targetLocation. Resources are not valid")
        }
    }
}
