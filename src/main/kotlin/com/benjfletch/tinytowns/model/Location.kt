package com.benjfletch.tinytowns.model

/** Represents a location on the [Board], defined by x / y coordinates */
data class Location(val x: Int, val y: Int) {
    fun adjacent(): List<Location> = listOf(
                Location(x - 1, y),
                Location(x + 1, y),
                Location(x, y - 1),
                Location(x, y + 1))

    override fun toString(): String {
        return "$x:$y"
    }
}