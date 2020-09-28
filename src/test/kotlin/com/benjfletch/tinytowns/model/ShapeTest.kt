package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.ShapeException
import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Resource.WOOD
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class ShapeTest {
    private val squareMatrixOne: ResourceMatrix = arrayOf(
            arrayOf(NONE, WHEAT),
            arrayOf(BRICK, GLASS))
    private val squareMatrixTwo: ResourceMatrix = arrayOf(
            arrayOf(BRICK, NONE),
            arrayOf(GLASS, WHEAT))
    private val squareMatrixThree: ResourceMatrix = arrayOf(
            arrayOf(GLASS, BRICK),
            arrayOf(WHEAT, NONE))
    private val squareMatrixFour: ResourceMatrix = arrayOf(
            arrayOf(WHEAT, GLASS),
            arrayOf(NONE, BRICK))

    private val rectangleMatrixOne: ResourceMatrix = arrayOf(
            arrayOf(WOOD, STONE))
    private val rectangleMatrixTwo: ResourceMatrix = arrayOf(
            arrayOf(WOOD),
            arrayOf(STONE))
    private val rectangleMatrixFour: ResourceMatrix = arrayOf(
            arrayOf(STONE, WOOD))
    private val rectangleMatrixThree: ResourceMatrix = arrayOf(
            arrayOf(STONE),
            arrayOf(WOOD))

    @Test
    fun `Calculates orientations of square matrix correctly`() {
        val shape = Shape(squareMatrixOne)
        assertThat(shape.orientations).containsExactlyInAnyOrder(squareMatrixOne, squareMatrixTwo, squareMatrixThree, squareMatrixFour)
    }

    @Test
    fun `Calculates orientations of rectangular matrix correctly`() {
        val shape = Shape(rectangleMatrixOne)
        assertThat(shape.orientations).containsExactlyInAnyOrder(rectangleMatrixOne, rectangleMatrixTwo, rectangleMatrixThree, rectangleMatrixFour)
    }

    @Test
    fun `Throws exception when matrix is not fully populated`() {
        assertThatCode { Shape(arrayOf(arrayOf(STONE), arrayOf())) }
                .isInstanceOf(ShapeException::class.java)
                .hasMessage("Invalid Shape. All columns must be populated with a Resource type.")
    }

    @Test
    fun `Can evaluate equals()`() {
        assertThat(squareMatrixOne.equals(squareMatrixOne)).isTrue()
        assertThat(squareMatrixOne.equals(squareMatrixTwo)).isFalse()
        assertThat(rectangleMatrixThree.equals(rectangleMatrixThree)).isTrue()
    }

    @Test
    fun `Can evaluate hashCodes`() {
        assertThat(squareMatrixOne.hashCode().equals(squareMatrixOne.hashCode())).isTrue()
        assertThat(squareMatrixOne.hashCode().equals(squareMatrixTwo.hashCode())).isFalse()
        assertThat(rectangleMatrixThree.hashCode().equals(rectangleMatrixThree.hashCode())).isTrue()
    }
}