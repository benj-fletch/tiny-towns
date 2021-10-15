package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.getSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PlacesOfWorshipTest {
    @Test
    fun `Abbey can be serialized and deserialized`() {
        val abbey = Abbey()
        val serialized = getSerializer().encodeToString(abbey)
        val deserialized = getSerializer().decodeFromString<Abbey>(serialized)
        Assertions.assertThat(abbey).isEqualTo(deserialized)
    }

    @Test
    fun `Cloister can be serialized and deserialized`() {
        val cloister = Cloister()
        val serialized = getSerializer().encodeToString(cloister)
        val deserialized = getSerializer().decodeFromString<Cloister>(serialized)
        Assertions.assertThat(cloister).isEqualTo(deserialized)
    }

    @Test
    fun `Chapel can be serialized and deserialized`() {
        val chapel = Chapel()
        val serialized = getSerializer().encodeToString(chapel)
        val deserialized = getSerializer().decodeFromString<Chapel>(serialized)
        Assertions.assertThat(chapel).isEqualTo(deserialized)
    }

    @Test
    fun `Temple can be serialized and deserialized`() {
        val temple = Temple()
        val serialized = getSerializer().encodeToString(temple)
        val deserialized = getSerializer().decodeFromString<Temple>(serialized)
        Assertions.assertThat(temple).isEqualTo(deserialized)
    }
}
