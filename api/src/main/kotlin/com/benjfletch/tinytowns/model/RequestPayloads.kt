package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.model.buildings.Building
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("placePieceRequest")
data class PlacePieceRequest(val location: Location, val piece: GamePiece)

@Serializable
@SerialName("removePieceRequest")
data class RemovePieceRequest(val location: Location)

@Serializable
@SerialName("buildRequest")
data class BuildRequest(val components: Map<String, Resource>, val targetLocation: Location, val targetBuilding: Building)
