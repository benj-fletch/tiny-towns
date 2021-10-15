package com.benjfletch.tinytowns

/** Exception to encompass something going wrong with the game board */
class BoardException(message: String) : Exception(message)

/** Exception to encompass something going wrong with a Location */
class LocationException(message: String) : Exception(message)

/** Exception to encompass something going wrong with a Buildings Shape configuration */
class ShapeException(message: String) : Exception(message)

/** Exception to encompass something going wrong with a Building */
class BuildingException(message: String) : Exception(message)

/** Exception to encompass something going wrong with the GameGrid */
class GameGridException(message: String) : Exception(message)

/** Exception for specific errors to do with the Goods Handler building type */
class GoodsHandlerException(message: String): Exception(message)
