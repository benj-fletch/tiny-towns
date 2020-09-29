package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.BuildingException
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import com.benjfletch.tinytowns.model.Resource.WOOD
import com.benjfletch.tinytowns.model.Shape
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class BuildingTest {
    companion object TestGamePiece: Building {
        val resourceMatrix = listOf(listOf(WOOD, STONE), listOf(NONE, GLASS))

        override val pieceName = "test"
        override val text = "A test building"
        override val canBeBuiltAnywhere = false
        override val shape = Shape(resourceMatrix)
    }

    @Test
    fun `Correctly matches resource matrix`() {
        assertThatCode { TestGamePiece.matrixMatches(resourceMatrix) }
                .doesNotThrowAnyException()
    }

    @Test
    fun `Throws exception when resource matrices do not match`() {
        assertThatCode { TestGamePiece.matrixMatches(listOf(listOf(NONE, GLASS), listOf(STONE, WHEAT))) }
                .isInstanceOf(BuildingException::class.java)
    }
}
