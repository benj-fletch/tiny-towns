package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.model.Resource.BRICK
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Resource.WOOD
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ResourceMatrixTest {
    @Test
    fun `Transpose correctly transposes square matrix`() {
        val input: ResourceMatrix = arrayOf(
                arrayOf(NONE, WHEAT),
                arrayOf(BRICK, GLASS))
        val expected = arrayOf(
                arrayOf(NONE, BRICK),
                arrayOf(WHEAT, GLASS))
        Assertions.assertThat(transpose(input)).isEqualTo(expected)
    }

    @Test
    fun `Transpose correctly transposes rectangular matrix`() {
        val input: ResourceMatrix = arrayOf(arrayOf(WOOD, STONE))
        val expected = arrayOf(
                arrayOf(WOOD),
                arrayOf(STONE))
        Assertions.assertThat(transpose(input)).isEqualTo(expected)
    }
}