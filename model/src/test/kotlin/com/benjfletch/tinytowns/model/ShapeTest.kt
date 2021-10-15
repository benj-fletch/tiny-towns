package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.ShapeException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class ShapeTest {
    private val squareMatrixOne: ResourceMatrix = listOf(
            listOf(NONE(), WHEAT()),
            listOf(BRICK(), GLASS()))
    private val squareMatrixTwo: ResourceMatrix = listOf(
            listOf(BRICK(), NONE()),
            listOf(GLASS(), WHEAT()))
    private val squareMatrixThree: ResourceMatrix = listOf(
            listOf(GLASS(), BRICK()),
            listOf(WHEAT(), NONE()))
    private val squareMatrixFour: ResourceMatrix = listOf(
            listOf(WHEAT(), GLASS()),
            listOf(NONE(), BRICK()))

    private val rectangleMatrixOne: ResourceMatrix = listOf(
            listOf(WOOD(), STONE()))
    private val rectangleMatrixTwo: ResourceMatrix = listOf(
            listOf(WOOD()),
            listOf(STONE()))
    private val rectangleMatrixFour: ResourceMatrix = listOf(
            listOf(STONE(), WOOD()))
    private val rectangleMatrixThree: ResourceMatrix = listOf(
            listOf(STONE()),
            listOf(WOOD()))

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
        assertThatCode { Shape(listOf(listOf(STONE()), listOf())) }
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
