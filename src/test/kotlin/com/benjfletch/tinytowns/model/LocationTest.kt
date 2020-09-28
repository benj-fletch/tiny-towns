package com.benjfletch.tinytowns.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LocationTest {
    @Test
    fun `Converts to String correctly`() {
        val location = Location(1,1)
        assertThat(location.toString()).isEqualTo("1:1")
    }
}