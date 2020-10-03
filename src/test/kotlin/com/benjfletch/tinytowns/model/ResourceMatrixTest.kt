package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Resource.WOOD
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

    @Test
    fun `Converts IntRanges of same sizes to Locations`() {
        val x = IntRange(1, 2)
        val y = IntRange(1, 2)
        val expected = listOf(Location(1, 1), Location(1, 2), Location(2, 1), Location(2, 2))
        val actual = rangesAsLocations(x, y)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun `Converts IntRanges of different sizes to Locations`() {
        val x = IntRange(1, 1)
        val y = IntRange(1, 2)
        val expected = listOf(Location(1, 1), Location(1, 2))
        val actual = rangesAsLocations(x, y)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @ParameterizedTest
    @MethodSource("mapsToMatrices")
    fun `Can convert from Location-Resource map to Resource Matrix`(input: Map<Location, Resource>, expected: ResourceMatrix) {
        val actual = input.toResourceMatrix()
        assertThat(actual).isEqualTo(expected)
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

        @JvmStatic
        fun mapsToMatrices(): List<Arguments> = listOf(
                // 1x1
                Arguments.of(mapOf(Location(0, 0) to GLASS), listOf(listOf(GLASS))),
                // 2x1
                Arguments.of(mapOf(Location(0, 0) to GLASS, Location(1, 0) to WHEAT), listOf(listOf(GLASS, WHEAT))),
                // 1x2
                Arguments.of(mapOf(Location(0, 0) to GLASS, Location(0, 1) to WHEAT), listOf(listOf(WHEAT), listOf(GLASS))),
                // 2x2 with empty space
                Arguments.of(mapOf(Location(0, 0) to GLASS, Location(1, 0) to WHEAT, Location(0, 1) to BRICK), listOf(listOf(BRICK, NONE), listOf(GLASS, WHEAT))),
                // 2x2
                Arguments.of(mapOf(Location(0, 0) to GLASS, Location(1, 0) to WHEAT, Location(0, 1) to BRICK, Location(1, 1) to WOOD), listOf(listOf(BRICK, WOOD), listOf(GLASS, WHEAT))),
                // 3x3 with empty space
                Arguments.of(mapOf(Location(0, 0) to GLASS, Location(1, 0) to WHEAT, Location(2, 0) to STONE, Location(0, 1) to BRICK, Location(1, 1) to WOOD), listOf(listOf(BRICK, WOOD, NONE), listOf(GLASS, WHEAT, STONE))),
                // 3x3
                Arguments.of(mapOf(Location(0, 0) to GLASS, Location(1, 0) to WHEAT, Location(2, 0) to STONE, Location(0, 1) to BRICK, Location(1, 1) to WOOD, Location(2, 1) to GLASS), listOf(listOf(BRICK, WOOD, GLASS), listOf(GLASS, WHEAT, STONE))),
        )
    }
}
