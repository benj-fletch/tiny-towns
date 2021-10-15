package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.getSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RestaurantsTest {
    @Test
    fun `Almshouse can be serialized and deserialized`() {
        val almshouse = Almshouse()
        val serialized = getSerializer().encodeToString(almshouse)
        val deserialized = getSerializer().decodeFromString<Almshouse>(serialized)
        Assertions.assertThat(almshouse).isEqualTo(deserialized)
    }

    @Test
    fun `FeastHall can be serialized and deserialized`() {
        val feastHall = FeastHall()
        val serialized = getSerializer().encodeToString(feastHall)
        val deserialized = getSerializer().decodeFromString<FeastHall>(serialized)
        Assertions.assertThat(feastHall).isEqualTo(deserialized)
    }

    @Test
    fun `Inn can be serialized and deserialized`() {
        val inn = Inn()
        val serialized = getSerializer().encodeToString(inn)
        val deserialized = getSerializer().decodeFromString<Inn>(serialized)
        Assertions.assertThat(inn).isEqualTo(deserialized)
    }

    @Test
    fun `Tavern can be serialized and deserialized`() {
        val tavern = Tavern()
        val serialized = getSerializer().encodeToString(tavern)
        val deserialized = getSerializer().decodeFromString<Tavern>(serialized)
        Assertions.assertThat(tavern).isEqualTo(deserialized)
    }
}
