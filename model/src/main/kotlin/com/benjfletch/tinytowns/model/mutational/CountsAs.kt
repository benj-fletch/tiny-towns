package com.benjfletch.tinytowns.model.mutational

import com.benjfletch.tinytowns.model.buildings.Building
import kotlin.reflect.KClass

/**
 * Makes a [Building] act as if it was another type of [Building]
 */
interface CountsAs {
    val countsAs: KClass<out Building>
    val countsAsAmount: Int
        get() = 1
}