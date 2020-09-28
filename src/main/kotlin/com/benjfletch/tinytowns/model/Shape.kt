package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.ShapeException

/** Representation of the shape and resources required to build a specific [Building]. Each [Shape] is represented
 * by an NxM matrix of [Resources][Resource], with empty squares populated with [null].
 *
 * On creation, the [matrix] is rotated by 90 degrees 3 times, to result in the set of 4 possible orientations of a
 * given [Shape]. For example a shape might have the following [matrix]:
 * ```
 *      null  WHEAT
 *      BRICK GLASS
 * ```
 *  Which will result in the following 4 possible permutations
 * ```
 *      BRICK null      GLASS BRICK     WHEAT GLASS
 *      GLASS WHEAT     WHEAT null      null  BRICK
 * ```
 *
 * This rule also holds for non-square matrices:
 * ```
 *
 * ```
 */
data class Shape(val matrix: ResourceMatrix) {
    /** Verify that all spaces in [matrix] have been populated with a [Resource] or with null */
    init {
        val cols = matrix[0].size
        if(matrix.any { it.size != cols}) throw ShapeException("Invalid Shape. All columns must be populated with a Resource type.")
    }

    /** Holds the representations of this [Shape] in the 4 cardinal orientations */
    val orientations = calculateOrientations()

    /** Calculate the [Shape] permutations for this [Shape] in each of the 4 cardinal orientations */
    private fun calculateOrientations(): List<ResourceMatrix> {
        val up = matrix
        val right = up.rotate90()
        val down = right.rotate90()
        val left = down.rotate90()
        return listOf(up, right, down, left)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shape

        if (!matrix.contentDeepEquals(other.matrix)) return false
        if (orientations != other.orientations) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matrix.contentDeepHashCode()
        result = 31 * result + orientations.hashCode()
        return result
    }
}
