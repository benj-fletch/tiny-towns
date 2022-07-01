package com.benjfletch.tinytowns.ktor

import com.benjfletch.tinytowns.Serializer
import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.Board
import com.benjfletch.tinytowns.model.BuildRequest
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.Location
import com.benjfletch.tinytowns.model.PlacePieceRequest
import com.benjfletch.tinytowns.model.RemovePieceRequest
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.buildings.UnfedCottage
import com.benjfletch.tinytowns.services.retrieveBoard
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.*

class BoardRoutesTest {
    @Test
    fun `Creates a board with a valid UUID`() {
        withTestApplication({ module(testing = true) }) {
            createBoard(this)
        }
    }

    @Test
    fun `Retrieves a board with a specified id`() {
        withTestApplication({ module(testing = true) }) {
            val boardId = createBoard(this)
            handleRequest(HttpMethod.Get, "/board/${boardId}").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isEqualTo(Serializer.Model.encodeToString(Board(4)))
            }
        }
    }

    @Test
    fun `Returns Not Found when no id is passed`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/board/").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.NotFound)
            }
        }
    }

    @Test
    fun `Returns Not Found when no board is found for passed id`() {
        withTestApplication({ module(testing = true) }) {
            val boardId = "non-existent-id"
            handleRequest(HttpMethod.Get, "/board/${boardId}").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.NotFound)
                assertThat(response.content).isEqualTo("No board found with id $boardId")
            }
        }
    }

    @Test
    fun `Clears a board with no pieces on it`() {
        withTestApplication({ module(testing = true) }) {
            val boardId = createBoard(this)
            handleRequest(HttpMethod.Patch, "/board/${boardId}/clear").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isNotBlank
            }
        }
    }

    @Test
    fun `Clears a board with pieces on it`() {
        withTestApplication({ module(testing = true) }) {
            val boardId = createBoard(this)
            val expectedBoard = Board()
            var actualBoard: Board
            placePiece(this, boardId, Location(1, 1), STONE())
            placePiece(this, boardId, Location(3, 2), STONE())
            handleRequest(HttpMethod.Patch, "/board/${boardId}/clear").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isNotBlank
                actualBoard = responseToBoard(response.content ?: "")
            }
            assertThat(actualBoard).isEqualTo(expectedBoard)
        }
    }

    @Test
    fun `Places a piece on the board`() {
        withTestApplication({ module(testing = true) }) {
            val placeLoc = Location(1, 1)
            val placePiece = WHEAT()
            val expected = Board()
            expected.place(Location(1, 1), placePiece)

            val boardId = createBoard(this)
            val actual = placePiece(this, boardId, placeLoc, placePiece)
            assertThat(actual).isEqualTo(Serializer.Model.encodeToString(expected))
        }
    }

    @Test
    fun `Returns 400 when placing a piece and location is not on board`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(-1, -1)
            val boardId = createBoard(this)
            handleRequest(HttpMethod.Post, "/board/${boardId}/place") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(PlacePieceRequest(targetLoc, WHEAT())))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
                    assertThat(response.content).isEqualTo("$targetLoc is out of bounds")
                }
        }
    }

    @Test
    fun `Returns 400 when placing a piece and target location is occupied`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(1, 1)
            val targetPiece = WHEAT()
            val boardId = createBoard(this)
            placePiece(this, boardId, targetLoc, targetPiece)
            handleRequest(HttpMethod.Post, "/board/${boardId}/place") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(PlacePieceRequest(targetLoc, WHEAT())))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
                    assertThat(response.content).isEqualTo("$targetLoc is occupied by wheat")
                }
        }
    }

    @Test
    fun `Removes a piece on the board`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(1, 1)
            val targetPiece = WHEAT()

            val boardId = createBoard(this)
            val actualBoardWithPiece = placePiece(this, boardId, targetLoc, targetPiece)

            val expectedBoardWithPiece = Board()
            expectedBoardWithPiece.place(targetLoc, targetPiece)
            assertThat(actualBoardWithPiece).isEqualTo(Serializer.Model.encodeToString(expectedBoardWithPiece))

            handleRequest(HttpMethod.Post, "/board/${boardId}/remove") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(RemovePieceRequest(targetLoc)))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                    assertThat(response.content).isEqualTo(Serializer.Model.encodeToString(Board()))
                }
        }
    }

    @Test
    fun `Returns 200 when removing a location that has no occupant`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(1, 1)

            val boardId = createBoard(this)
            handleRequest(HttpMethod.Post, "/board/${boardId}/remove") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(RemovePieceRequest(targetLoc)))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                    assertThat(response.content).isEqualTo(Serializer.Model.encodeToString(Board()))
                }
        }
    }

    @Test
    fun `Returns 400 when removing a piece and location is not on board`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(-1, -1)

            val boardId = createBoard(this)
            handleRequest(HttpMethod.Post, "/board/${boardId}/remove") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(RemovePieceRequest(targetLoc)))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
                    assertThat(response.content).isEqualTo("$targetLoc is out of bounds")
                }
        }
    }

    @Test
    fun `Builds a building`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(1, 1)
            val targetBuilding = UnfedCottage()
            val cottageResources = mapOf(
                Location(2, 2).toString() to WHEAT(),
                Location(2, 1).toString() to GLASS(),
                Location(1, 1).toString() to BRICK(),
            )

            val boardId = createBoard(this)
            placePiece(this, boardId, Location(2, 2), WHEAT())
            placePiece(this, boardId, Location(1, 1), BRICK())
            val actualBoardWithCottageResources = placePiece(this, boardId, Location(2, 1), GLASS())
            val expectedBoard = Board()
            expectedBoard.place(Location(2, 2), WHEAT())
            expectedBoard.place(Location(1, 1), BRICK())
            expectedBoard.place(Location(2, 1), GLASS())
            assertThat(actualBoardWithCottageResources).isEqualTo(Serializer.Model.encodeToString(expectedBoard))

            expectedBoard.build(cottageResources, targetLoc.toString(), targetBuilding)
            assertThat(expectedBoard.gameGrid[targetLoc.toString()]).isEqualTo(UnfedCottage())

            handleRequest(HttpMethod.Post, "/board/${boardId}/build") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(BuildRequest(cottageResources, targetLoc, targetBuilding)))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                    assertThat(response.content).isEqualTo(Serializer.Model.encodeToString(expectedBoard))
                }
        }
    }

    @Test
    fun `Returns 400 when building and target location is not on board`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(-1, -1)
            val targetBuilding = UnfedCottage()
            val cottageResources = mapOf(
                Location(2, 2).toString() to WHEAT(),
                Location(2, 1).toString() to GLASS(),
                Location(1, 1).toString() to BRICK(),
            )

            val boardId = createBoard(this)
            placePiece(this, boardId, Location(2, 2), WHEAT())
            placePiece(this, boardId, Location(1, 1), BRICK())
            placePiece(this, boardId, Location(1, 2), GLASS())

            handleRequest(HttpMethod.Post, "/board/${boardId}/build") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(BuildRequest(cottageResources, targetLoc, targetBuilding)))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
                    assertThat(response.content).isEqualTo("$targetLoc is out of bounds")
                }
        }
    }

    @Test
    fun `Returns 400 when building and target location is invalid for target building`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(3, 3)
            val targetBuilding = UnfedCottage()
            val cottageResources = mapOf(
                Location(2, 2).toString() to WHEAT(),
                Location(2, 1).toString() to GLASS(),
                Location(1, 1).toString() to BRICK(),
            )

            val boardId = createBoard(this)
            placePiece(this, boardId, Location(2, 2), WHEAT())
            placePiece(this, boardId, Location(1, 1), BRICK())
            placePiece(this, boardId, Location(1, 2), GLASS())

            handleRequest(HttpMethod.Post, "/board/${boardId}/build") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(BuildRequest(cottageResources, targetLoc, targetBuilding)))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
                    assertThat(response.content).contains("Cannot build at $targetLoc. Location has to be one of")
                }
        }
    }

    @Test
    fun `Returns 400 when building and resources are not correct`() {
        withTestApplication({ module(testing = true) }) {
            val targetLoc = Location(1, 1)
            val targetBuilding = UnfedCottage()
            val cottageResources = mapOf(
                Location(2, 2).toString() to WHEAT(),
                Location(2, 1).toString() to GLASS(),
                Location(1, 1).toString() to BRICK(),
            )

            val boardId = createBoard(this)
            placePiece(this, boardId, Location(2, 2), STONE())
            placePiece(this, boardId, Location(1, 1), STONE())
            placePiece(this, boardId, Location(1, 2), STONE())

            handleRequest(HttpMethod.Post, "/board/${boardId}/build") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Serializer.Model.encodeToString(BuildRequest(cottageResources, targetLoc, targetBuilding)))
            }
                .apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
                    assertThat(response.content).contains("Cannot build $targetBuilding at $targetLoc. Resources are not valid")
                }
        }
    }

    private fun createBoard(app: TestApplicationEngine): String {
        app.handleRequest(HttpMethod.Get, "/board/create").apply {
            assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

            val boardId = response.content
            assertThat(boardId).isNotBlank
            assertThatCode { UUID.fromString(boardId) }.doesNotThrowAnyException()
            assertThat(retrieveBoard(boardId!!)).isEqualTo(Board(4))
            return boardId
        }
    }

    private fun placePiece(app: TestApplicationEngine, boardId: String, placeLoc: Location, placePiece: GamePiece): String {
        app.handleRequest(HttpMethod.Post, "/board/${boardId}/place") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Serializer.Model.encodeToString(PlacePieceRequest(placeLoc, placePiece)))
        }
            .apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isNotBlank
                return response.content!!
            }
    }

    private fun responseToBoard(boardString: String): Board {
        try {
            return Serializer.Model.decodeFromString(boardString)
        } catch (e: Exception) {
            fail("Failed to parse response to Board object. \nError message: ${e.message} \nResponse: $boardString")
        }
    }
}
