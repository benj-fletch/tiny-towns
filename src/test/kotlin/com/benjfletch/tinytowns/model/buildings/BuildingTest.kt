package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.BuildingException
import com.benjfletch.tinytowns.model.Resource.GLASS
import com.benjfletch.tinytowns.model.Resource.NONE
import com.benjfletch.tinytowns.model.Resource.STONE
import com.benjfletch.tinytowns.model.Resource.WHEAT
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class BuildingTest {
    @Test
    fun `Correctly matches resource matrix`() {
        assertThatCode { TestBuilding.matrixMatches(TestBuilding.resourceMatrix) }
                .doesNotThrowAnyException()
    }

    @Test
    fun `Throws exception when resource matrices do not match`() {
        assertThatCode { TestBuilding.matrixMatches(listOf(listOf(NONE, GLASS), listOf(STONE, WHEAT))) }
                .isInstanceOf(BuildingException::class.java)
    }
}
