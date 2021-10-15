package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.getSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FedCottageTest {
    @Test
    fun `Can be serialized and deserialized`() {
        val fedCottage = FedCottage()
        val serialized = getSerializer().encodeToString(fedCottage)
        val deserialized = getSerializer().decodeFromString<FedCottage>(serialized)
        assertThat(fedCottage).isEqualTo(deserialized)
    }
}

class UnfedCottageTest {
    @Test
    fun `Can be serialized and deserialized`() {
        val unfedCottage = UnfedCottage()
        val serialized = getSerializer().encodeToString(unfedCottage)
        val deserialized = getSerializer().decodeFromString<UnfedCottage>(serialized)
        assertThat(unfedCottage).isEqualTo(deserialized)
    }
}
