package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.BuildingException
import com.benjfletch.tinytowns.model.ALL
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.ResourceMatrix
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class BuildingTest {
    @Test
    fun `Correctly matches resource matrix`() {
        assertThatCode { TestBuilding.matrixMatches(TestBuilding.resourceMatrix) }
                .doesNotThrowAnyException()
    }

    @Test
    fun `Correctly matches resource matrix with ALL in`() {
        val allResourceMatrix: ResourceMatrix = listOf(listOf(ALL, STONE), listOf(NONE, GLASS))

        assertThatCode { TestBuilding.matrixMatches(allResourceMatrix) }
                .doesNotThrowAnyException()
    }

    @Test
    fun `Correctly matches resource matrix which is all ALLs`() {
        val allResourceMatrix: ResourceMatrix = listOf(listOf(ALL, ALL), listOf(ALL, ALL))

        assertThatCode { TestBuilding.matrixMatches(allResourceMatrix) }
                .doesNotThrowAnyException()
    }

    @Test
    fun `Throws exception when resource matrices do not match`() {
        assertThatCode { TestBuilding.matrixMatches(listOf(listOf(NONE, GLASS), listOf(STONE, WHEAT))) }
                .isInstanceOf(BuildingException::class.java)
    }
}
