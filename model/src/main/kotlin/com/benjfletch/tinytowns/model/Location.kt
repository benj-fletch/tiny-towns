package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.LocationException
import kotlinx.serialization.Serializable

/** Represents a location on the [Board], defined by x / y coordinates */
@Serializable
data class Location(val x: Int, val y: Int): Comparable<Location> {
    companion object {
        fun fromString(locAsString: String): Location {
            try {
                val parts = locAsString.split(":")
                return Location(parts[0].toInt(), parts[1].toInt())
            } catch (e: NumberFormatException) {
                throw LocationException("Cannot parse $locAsString to Location. Must be in format int:int")
            }
        }
    }

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
