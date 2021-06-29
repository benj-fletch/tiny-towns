package com.benjfletch.tinytowns.model

/** typealias for `Array<Array<Resource>>` which represents a 2D matrix of [Resources][Resource] */
typealias ResourceMatrix = List<List<Resource>>

/** Transpose a [ResourceMatrix] by flipping across the diagonal of NW -> SE */
fun transpose(elements: ResourceMatrix): ResourceMatrix {
    val rows = elements.size
    val cols = elements[0].size

    val transposed = MutableList(cols) { MutableList<Resource>(rows) { NONE } }

    for(rowIndex in elements.indices) {
        for(colIndex in elements[rowIndex].indices) {
            transposed[colIndex][rowIndex] = elements[rowIndex][colIndex]
        }
    }
    return transposed
}

/** Rotate a [ResourceMatrix] 90 degrees. This is in effect tipping it onto its side */
fun ResourceMatrix.rotate90() = transpose(this).map { it.reversed() }

/**
 * Convert from a [Map] of occupied spaces to a [ResourceMatrix], filling in any [Locations][Location] which aren't
 * populated with [Resource.NONE] such that the [ResourceMatrix] has a [Resource] at every location.
 *
 * The following logic is applied here:
 * 1. Minimum and maximum x and y coordinates are calculated, to give the size of the [ResourceMatrix]
 * 1. Any [Location] which has an x or y within the [Matrix] size but is not in the map is added, as a mapping of
 *    [Location](x, y) -> [Resource.NONE]. This covers spaces which are in the matrix but aren't part of the
 *    building [Resources][Resource]
 */
fun Map<Location, Resource>.toResourceMatrix(): ResourceMatrix {
    val xRange = IntRange(keys.minOf { it.x }, keys.maxOf { it.x })
    val yRange = IntRange(keys.minOf { it.y }, keys.maxOf { it.y })

    val filledComponents = toMutableMap()
    rangesAsLocations(xRange, yRange)
            .forEach { filledComponents.putIfAbsent(it, NONE) }

    return filledComponents.entries
            .sortedBy { it.key.x }
            .groupBy { it.key.y }
            .entries.sortedByDescending { it.key }
            .map { it.value.map { it.value } }
}

/** convert two ranges of Ints, representing x and y, to a [List] of [Locations][Location] */
fun rangesAsLocations(xRange: IntRange, yRange: IntRange): List<Location> = xRange.flatMap { x -> yRange.map { y -> Location(x, y) } }
