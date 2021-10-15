package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.getSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ShopsTest {
    @Test
    fun `Bakery can be serialized and deserialized`() {
        val bakery = Bakery()
        val serialized = getSerializer().encodeToString(bakery)
        val deserialized = getSerializer().decodeFromString<Bakery>(serialized)
        Assertions.assertThat(bakery).isEqualTo(deserialized)
    }

    @Test
    fun `Market can be serialized and deserialized`() {
        val market = Market()
        val serialized = getSerializer().encodeToString(market)
        val deserialized = getSerializer().decodeFromString<Market>(serialized)
        Assertions.assertThat(market).isEqualTo(deserialized)
    }

    @Test
    fun `Tailor can be serialized and deserialized`() {
        val tailor = Tailor()
        val serialized = getSerializer().encodeToString(tailor)
        val deserialized = getSerializer().decodeFromString<Tailor>(serialized)
        Assertions.assertThat(tailor).isEqualTo(deserialized)
    }

    @Test
    fun `Theater can be serialized and deserialized`() {
        val theater = Theater()
        val serialized = getSerializer().encodeToString(theater)
        val deserialized = getSerializer().decodeFromString<Theater>(serialized)
        Assertions.assertThat(theater).isEqualTo(deserialized)
    }
}
