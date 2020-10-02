package com.benjfletch.tinytowns.model.buildings

import com.benjfletch.tinytowns.model.Resource
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BuildingInstanceTest {
    @ParameterizedTest
    @MethodSource("buildingOrientations")
    fun `Building instance matches all orientations`(buildingOrientations: BuildingOrientations) {
        buildingOrientations.applyAssertions()
    }
}






class Orientation {
    val rows = arrayListOf<List<Resource>>()
    fun row(vararg resources: Resource) {
        this.rows.add(resources.toList())
    }

    override fun toString(): String {
        return rows.joinToString(",") { it.toString() }
    }
}

class BuildingOrientations(private val building: Building) {
    private val orientations = arrayListOf<Orientation>()

    fun orientation(init: Orientation.() -> Unit): Orientation {
        val orientation = Orientation()
        orientation.init()
        orientations.add(orientation)
        return orientation
    }

    fun applyAssertions() {
        assertThatCode { orientations.forEach { building.matrixMatches(it.rows) } }
                .withFailMessage("Could not find match for ${building.shape.matrix} in any of $orientations")
                .doesNotThrowAnyException()
    }
}

fun buildingOrientations(building: Building, init: BuildingOrientations.() -> Unit): BuildingOrientations {
    val buildingOrientations = BuildingOrientations(building)
    buildingOrientations.init()
    return buildingOrientations
}
