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
        val input: ResourceMatrix = listOf(
                listOf(NONE, WHEAT),
                listOf(BRICK, GLASS))
        val expected = listOf(
                listOf(NONE, BRICK),
                listOf(WHEAT, GLASS))
        assertThat(transpose(input)).isEqualTo(expected)
    }

    @Test
    fun `Transpose correctly transposes rectangular matrix`() {
        val input: ResourceMatrix = listOf(listOf(WOOD, STONE))
        val expected = listOf(
                listOf(WOOD),
                listOf(STONE))
        assertThat(transpose(input)).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("matrices")
    fun `Rotate 90 correctly rotates matrices of different sizes`(input: ResourceMatrix, expected: ResourceMatrix) {
        assertThat(input.rotate90()).isEqualTo(expected)
    }

    companion object {
        val woodStone = listOf(WOOD, STONE)
        val woodWood = listOf(WOOD, WOOD)
        val stoneStone = listOf(STONE, STONE)
        val wood = listOf(WOOD)
        val stone = listOf(STONE)
        @JvmStatic
        fun matrices(): List<Arguments> = listOf(
                Arguments.of(listOf(stone), listOf(stone)),
                Arguments.of(listOf(woodStone), listOf(wood, stone)),
                Arguments.of(listOf(stone, wood), listOf(woodStone)),
                Arguments.of(listOf(woodStone, woodStone), listOf(woodWood, stoneStone)),
                Arguments.of(listOf(stone, wood, stone), listOf(listOf(STONE, WOOD, STONE))),
                Arguments.of(listOf(
                        listOf(STONE, WOOD, WHEAT),
                        listOf(BRICK, GLASS, NONE),
                        listOf(NONE, GLASS, BRICK)
                ),
                listOf(
                        listOf(NONE, BRICK, STONE),
                        listOf(GLASS, GLASS, WOOD),
                        listOf(BRICK, NONE, WHEAT)
                )),
                )
    }
}