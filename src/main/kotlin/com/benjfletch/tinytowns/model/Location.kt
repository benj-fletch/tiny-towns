package com.benjfletch.tinytowns.model

/** Represents a location on the [Board], defined by x / y coordinates */
data class Location(val x: Int, val y: Int) {
    override fun toString(): String {
        return "$x:$y"
    }
}