package com.benjfletch.tinytowns.model

/** typealias for `Array<Array<Resource>>` which represents a 2D matrix of [Resources][Resource] */
typealias ResourceMatrix = List<List<Resource>>

/** Transpose a [ResourceMatrix] by flipping across the diagonal of NW -> SE */
fun transpose(elements: ResourceMatrix): ResourceMatrix {
    val rows = elements.size
    val cols = elements[0].size

    val transposed = MutableList(cols) { MutableList(rows) {Resource.NONE} }

    for(rowIndex in elements.indices) {
        for(colIndex in elements[rowIndex].indices) {
            transposed[colIndex][rowIndex] = elements[rowIndex][colIndex]
        }
    }
    return transposed
}

/** Rotate a [ResourceMatrix] 90 degrees. This is in effect tipping it onto its side */
fun ResourceMatrix.rotate90() = transpose(this).map { it.reversed() }
