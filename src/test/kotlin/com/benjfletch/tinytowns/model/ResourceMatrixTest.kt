package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Resource.WOOD
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ResourceMatrixTest {
    @Test
    fun `Transpose correctly transposes square matrix`() {
        val input: ResourceMatrix = arrayOf(
                arrayOf(NONE, WHEAT),
                arrayOf(BRICK, GLASS))
        val expected = arrayOf(
                arrayOf(NONE, BRICK),
                arrayOf(WHEAT, GLASS))
        assertThat(transpose(input)).isEqualTo(expected)
    }

    @Test
    fun `Transpose correctly transposes rectangular matrix`() {
        val input: ResourceMatrix = arrayOf(arrayOf(WOOD, STONE))
        val expected = arrayOf(
                arrayOf(WOOD),
                arrayOf(STONE))
        assertThat(transpose(input)).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("matrices")
    fun `Rotate 90 correctly rotates matrices of different sizes`(input: ResourceMatrix, expected: ResourceMatrix) {
        assertThat(input.rotate90()).isEqualTo(expected)
    }

    companion object {
        val woodStone = arrayOf(WOOD, STONE)
        val woodWood = arrayOf(WOOD, WOOD)
        val stoneStone = arrayOf(STONE, STONE)
        val wood = arrayOf(WOOD)
        val stone = arrayOf(STONE)
        @JvmStatic
        fun matrices(): List<Arguments> = listOf(
                Arguments.of(arrayOf(stone), arrayOf(stone)),
                Arguments.of(arrayOf(woodStone), arrayOf(wood, stone)),
                Arguments.of(arrayOf(stone, wood), arrayOf(woodStone)),
                Arguments.of(arrayOf(woodStone, woodStone), arrayOf(woodWood, stoneStone)),
                Arguments.of(arrayOf(stone, wood, stone), arrayOf(arrayOf(STONE, WOOD, STONE))),
                Arguments.of(arrayOf(
                        arrayOf(STONE, WOOD, WHEAT),
                        arrayOf(BRICK, GLASS, NONE),
                        arrayOf(NONE, GLASS, BRICK)
                ),
                arrayOf(
                        arrayOf(NONE, BRICK, STONE),
                        arrayOf(GLASS, GLASS, WOOD),
                        arrayOf(BRICK, NONE, WHEAT)
                )),
                )
    }
}