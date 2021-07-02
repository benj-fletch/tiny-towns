package com.benjfletch.tinytowns.model

/** Represents a location on the [Board], defined by x / y coordinates */
data class Location(val x: Int, val y: Int): Comparable<Location> {
    fun adjacent(): List<Location> = listOf(
                Location(x - 1, y),
                Location(x + 1, y),
                Location(x, y - 1),
                Location(x, y + 1))

    fun surrounding(): List<Location> = listOf(
            Location(x - 1, y - 1),
            Location(x - 1, y),
            Location(x - 1, y + 1),
            Location(x, y - 1),
            Location(x, y + 1),
            Location(x + 1, y - 1),
            Location(x + 1, y),
            Location(x + 1, y + 1))

    override fun toString(): String {
        return "$x:$y"
    }

    override fun compareTo(other: Location): Int {
        return when {
            this == other -> 0
            this.x > other.x || this.y > other.y -> 1
            else -> -1
        }
    }
}
