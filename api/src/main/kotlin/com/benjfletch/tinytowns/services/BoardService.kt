package com.benjfletch.tinytowns.services

import com.benjfletch.tinytowns.BoardException
import com.benjfletch.tinytowns.BuildingException
import com.benjfletch.tinytowns.memorystore.MemoryStore
import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.Resource
import com.benjfletch.tinytowns.model.buildings.Building
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import java.util.*

fun createBoard(size: Int): String {
    val uuid = UUID.randomUUID().toString()
    val board = Board(size)
    storeBoard(uuid, board)
    return uuid
}

fun storeBoard(uuid: String, board: Board) {
    MemoryStore.boards[uuid] = board
}

fun retrieveBoard(uuid: String): Board? {
    return MemoryStore.boards[uuid]
}

fun clearBoard(boardId: String) {
    MemoryStore.boards[boardId]?.clear()
}

suspend fun retrieveBoardId(call: ApplicationCall): String? {
    val id = call.parameters["id"]

     id ?: call.respondText(
        "Board id is missing",
        status = HttpStatusCode.BadRequest
    )
    return id
}

suspend fun retrieveBoard(call: ApplicationCall, boardId: String): Board? {
    val board = retrieveBoard(boardId)
    board ?: call.respondText(
        "No board found with id $boardId",
        status = HttpStatusCode.NotFound
    )
    return board
}

suspend fun placePiece(call: ApplicationCall, board: Board, location: Location, piece: GamePiece): Boolean {
    val unknownExceptionMessage = "Unknown error occurred when attempting to place ${piece.pieceName} at $location"
    return safeExecuteBoardAction(call, unknownExceptionMessage) { board.place(location, piece) }
}

suspend fun removePiece(call: ApplicationCall, board: Board, location: Location): Boolean {
    val unknownExceptionMessage = "Unknown error occurred when attempting to remove piece at $location"
    return safeExecuteBoardAction(call, unknownExceptionMessage) { board.remove(location) }
}

suspend fun build(call: ApplicationCall, board: Board, components: Map<String, Resource>, targetLocation: Location, targetBuilding: Building): Boolean {
    val unknownExceptionMessage = "Unknown error occurred when attempting to build ${targetBuilding.pieceName} at $targetLocation"
    return safeExecuteBoardAction(call, unknownExceptionMessage) { board.build(components, targetLocation.toString(), targetBuilding) }
}

suspend fun safeExecuteBoardAction(call: ApplicationCall, fallbackExceptionMessage: String, action: () -> Unit): Boolean {
    try {
        action.invoke()
        return true
    } catch (e: BoardException) {
        call.respondText(e.message ?: fallbackExceptionMessage, status = HttpStatusCode.BadRequest)
    } catch (e: BuildingException) {
        call.respondText(e.message ?: fallbackExceptionMessage, status = HttpStatusCode.BadRequest)
    } catch (e: Exception) {
        call.respondText(e.message ?: fallbackExceptionMessage, status = HttpStatusCode.InternalServerError)
    }
    return false
}
