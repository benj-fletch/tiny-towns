package com.benjfletch.tinytowns.model.buildings

/**
 * Interface to represent a [Building] which can be Fed. This interface is designed to be used by objects only, hence
 * why [isFed] is a val and not a var
 */
interface FeedableBuilding: Building {
    /** Is this [Building] object fed */
    val isFed: Boolean
}
