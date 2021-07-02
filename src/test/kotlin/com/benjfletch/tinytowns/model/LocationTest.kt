package com.benjfletch.tinytowns.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LocationTest {
    @Test
    fun `Converts to String correctly`() {
        val location = Location(1,1)
        assertThat(location.toString()).isEqualTo("1:1")
    }

    @Test
    fun `Calculates surrounding locations accurately`() {
        val location = Location(2, 2)
        val expected = listOf(
                Location(1, 1),
                Location(2, 1),
                Location(3, 1),
                Location(1, 2),
                Location(3, 2),
                Location(1, 3),
                Location(2, 3),
                Location(3, 3),
        )
        val actual = location.surrounding()
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }
}
