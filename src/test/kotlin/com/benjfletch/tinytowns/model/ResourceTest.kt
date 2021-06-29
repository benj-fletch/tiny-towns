package com.benjfletch.tinytowns.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ResourceTest {
    @Test
    fun `Equality checks`() {
        assertThat(BRICK).isEqualTo(BRICK)
        assertThat(BRICK).isNotEqualTo(GLASS)
        assertThat(GLASS).isEqualTo(GLASS)
        assertThat(GLASS).isNotEqualTo(WOOD)
        assertThat(STONE).isEqualTo(STONE)
        assertThat(STONE).isNotEqualTo(GLASS)
        assertThat(WOOD).isEqualTo(WOOD)
        assertThat(WOOD).isNotEqualTo(GLASS)
        assertThat(WHEAT).isEqualTo(WHEAT)
        assertThat(WHEAT).isNotEqualTo(GLASS)
        assertThat(NONE).isEqualTo(NONE)
        assertThat(NONE).isNotEqualTo(GLASS)
        assertThat(ALL).isEqualTo(BRICK)
        assertThat(ALL).isEqualTo(GLASS)
        assertThat(ALL).isEqualTo(STONE)
        assertThat(ALL).isEqualTo(WOOD)
        assertThat(ALL).isEqualTo(WHEAT)
        assertThat(ALL).isEqualTo(NONE)
        assertThat(BRICK).isEqualTo(ALL)
        assertThat(GLASS).isEqualTo(ALL)
        assertThat(STONE).isEqualTo(ALL)
        assertThat(WOOD).isEqualTo(ALL)
        assertThat(WHEAT).isEqualTo(ALL)
        assertThat(NONE).isEqualTo(ALL)
    }
}
