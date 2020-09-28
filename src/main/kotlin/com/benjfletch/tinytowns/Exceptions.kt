package com.benjfletch.tinytowns

/** Exception to encompass something going wrong with the game board */
class BoardException(message: String) : Exception(message)

/** Exception to encompass something going wrong with a Buildings Shape configuration */
class ShapeException(message: String) : Exception(message)