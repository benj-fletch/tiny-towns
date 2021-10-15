package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.LocationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class LocationTest {
    @Test
    fun `Converts to String correctly`() {
        val location = Location(1,1)
        assertThat(location.toString()).isEqualTo("1:1")
    }

    @Test
    fun `Converts from String correctly`() {
        val location = Location(1,1)
        assertThat(Location.fromString(location.toString())).isEqualTo(location)
    }

    @Test
    fun `Throws exception when conversion from string is bad format`() {
        val badLocationStrings = listOf("a:1", "1:a", "a:a", "a", "1-1")
        badLocationStrings.forEach {
            assertThatCode { Location.fromString(it) }
                .isInstanceOf(LocationException::class.java)
                .hasMessageContaining("Cannot parse $it")
        }
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

    @Test
    fun `Is serializable`() {
        val location = Location(1, 1)
        val expected = """{"x":1,"y":1}"""
        assertThat(Json.encodeToString(location)).isEqualToIgnoringNewLines(expected)
    }

    @Test
    fun `Is deserializable`() {
        val expected = Location(1, 1)
        val locationJson = """{"x":1,"y":1}"""
        assertThat(Json.decodeFromString<Location>(locationJson)).isEqualTo(expected)
    }
}
