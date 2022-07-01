package com.benjfletch.tinytowns.ktor

import com.benjfletch.tinytowns.Serializer
import com.benjfletch.tinytowns.model.BuildRequest
import com.benjfletch.tinytowns.model.PlacePieceRequest
import com.benjfletch.tinytowns.model.RemovePieceRequest
import com.benjfletch.tinytowns.services.build
import com.benjfletch.tinytowns.services.clearBoard
import com.benjfletch.tinytowns.services.createBoard
import com.benjfletch.tinytowns.services.deserializeInputRequest
import com.benjfletch.tinytowns.services.placePiece
import com.benjfletch.tinytowns.services.removePiece
import com.benjfletch.tinytowns.services.retrieveBoard
import com.benjfletch.tinytowns.services.retrieveBoardId
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString

fun Route.boardRoutes() {
    route("/board") {
        get("/create") {
            val boardId = createBoard(4)
            call.respond(boardId)
        }
        get("/{id}") {
            val boardId = retrieveBoardId(call) ?: return@get
            val board = retrieveBoard(call, boardId) ?: return@get
            call.respond(Serializer.Model.encodeToString(board))
        }
        patch("/{id}/clear") {
            val boardId = retrieveBoardId(call) ?: return@patch
            val board = retrieveBoard(call, boardId) ?: return@patch
            clearBoard(boardId)
            call.respond(Serializer.Model.encodeToString(board))
        }
        post("/{id}/place") {
            val boardId = retrieveBoardId(call) ?: return@post
            val board = retrieveBoard(call, boardId) ?: return@post
            val (location, piece) = deserializeInputRequest(call, PlacePieceRequest::class) ?: return@post
            val result = placePiece(call, board, location, piece)
            if(result) {
                call.respond(Serializer.Model.encodeToString(board))
            }
        }
        post("/{id}/remove") {
            val boardId = retrieveBoardId(call) ?: return@post
            val board = retrieveBoard(call, boardId) ?: return@post
            val (location) = deserializeInputRequest(call, RemovePieceRequest::class) ?: return@post
            val result = removePiece(call, board, location)
            if(result) {
                call.respond(Serializer.Model.encodeToString(board))
            }
        }
        post("/{id}/build") {
            val boardId = retrieveBoardId(call) ?: return@post
            val board = retrieveBoard(call, boardId) ?: return@post
            val (components, targetLocation, targetBuilding) = deserializeInputRequest(call, BuildRequest::class) ?: return@post
            val result = build(call, board, components, targetLocation, targetBuilding)
            if(result) {
                call.respond(Serializer.Model.encodeToString(board))
            }
        }
    }
}

fun Application.registerBoardRoutes() {
    routing {
        boardRoutes()
    }
}
