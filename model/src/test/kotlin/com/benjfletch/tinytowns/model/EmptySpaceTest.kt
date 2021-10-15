package com.benjfletch.tinytowns.model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class EmptySpaceTest {
    @Test
    fun `Can be serialized and deserialized`() {
        val emptySpace = EmptySpace()
        val serialized = getSerializer().encodeToString(emptySpace)
        val deserialized = getSerializer().decodeFromString<EmptySpace>(serialized)
        Assertions.assertThat(emptySpace).isEqualTo(deserialized)
    }
}
