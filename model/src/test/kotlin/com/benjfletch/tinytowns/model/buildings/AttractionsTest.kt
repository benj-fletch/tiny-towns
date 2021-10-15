package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.getSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FountainTest {
    @Test
    fun `Can be serialized and deserialized`() {
        val fountain = Fountain()
        val serialized = getSerializer().encodeToString(fountain)
        val deserialized = getSerializer().decodeFromString<Fountain>(serialized)
        assertThat(fountain).isEqualTo(deserialized)
    }
}

class MillstoneTest {
    @Test
    fun `Can be serialized and deserialized`() {
        val millstone = Millstone()
        val serialized = getSerializer().encodeToString(millstone)
        val deserialized = getSerializer().decodeFromString<Millstone>(serialized)
        assertThat(millstone).isEqualTo(deserialized)
    }
}

class ShedTest {
    @Test
    fun `Can be serialized and deserialized`() {
        val shed = Shed()
        val serialized = getSerializer().encodeToString(shed)
        val deserialized = getSerializer().decodeFromString<Shed>(serialized)
        assertThat(shed).isEqualTo(deserialized)
    }
}

class WellTest {
    @Test
    fun `Can be serialized and deserialized`() {
        val well = Well()
        val serialized = getSerializer().encodeToString(well)
        val deserialized = getSerializer().decodeFromString<Well>(serialized)
        assertThat(well).isEqualTo(deserialized)
    }
}
