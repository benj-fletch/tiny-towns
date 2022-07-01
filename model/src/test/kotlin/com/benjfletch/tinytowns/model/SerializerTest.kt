package com.benjfletch.tinytowns.model

import com.benjfletch.tinytowns.Serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class SerializerTest {
    @Test
    fun `Can serialize a board to string`() {
        val board = Board()
        assertDoesNotThrow {
            Serializer.Model.encodeToString(board)
        }
    }

    @Test
    fun `Can deserialize a board from string`() {
        val boardString = """
            {"gameGrid":{"1:1":{"type":"empty"},"1:2":{"type":"empty"},"1:3":{"type":"empty"},"1:4":{"type":"empty"},
            "2:1":{"type":"empty"},"2:2":{"type":"empty"},"2:3":{"type":"empty"},"2:4":{"type":"empty"},
            "3:1":{"type":"empty"},"3:2":{"type":"empty"},"3:3":{"type":"empty"},"3:4":{"type":"empty"},
            "4:1":{"type":"empty"},"4:2":{"type":"empty"},"4:3":{"type":"empty"},"4:4":{"type":"empty"}}}
            """.trimIndent()
        assertDoesNotThrow {
            Serializer.Model.decodeFromString<Board>(boardString)
        }
    }

    @Test
    fun `Can serialize and deserialize a board`() {
        val board = Board()
        val serialized = Serializer.Model.encodeToString(board)
        val deserialized = Serializer.Model.decodeFromString<Board>(serialized)
        assertThat(board).isEqualTo(deserialized)
    }
}
