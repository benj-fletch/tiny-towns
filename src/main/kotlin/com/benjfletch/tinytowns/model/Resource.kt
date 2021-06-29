package com.benjfletch.tinytowns.model

/** Super class for any [GamePiece] which is, or can act as, part of a [Building] for the build process. */
abstract class Resource: GamePiece {
    /** Whether this [Resource] should be considered equal to EVERY other resource type */
    open fun actsAsAnyResource(): Boolean = false
    /** Whether this [Resource] should be removed after being used as part of a build process */
    open fun removeAfterBuild(): Boolean = true

    companion object {
        /** All of the resources which are "basic". i.e each of the resources themselves, not any specialisations */
        fun basicResources(): Set<Resource> = setOf(BRICK, GLASS, STONE, WOOD, WHEAT)
    }

    /** Specify equals so that we can evaluate [actsAsAnyResource] */
    override fun equals(other: Any?): Boolean {
        if(other is Resource) {
            return this.actsAsAnyResource()
                    || other.actsAsAnyResource()
                    || this.pieceName == other.pieceName
        }
        return false
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return this::class.simpleName ?: this.pieceName
    }
}

object BRICK: Resource() {
    override val pieceName = "brick"
}

object GLASS: Resource() {
    override val pieceName = "glass"
}

object STONE: Resource() {
    override val pieceName = "stone"
}

object WOOD: Resource() {
    override val pieceName = "wood"
}

object WHEAT: Resource() {
    override val pieceName = "wheat"
}

object ALL: Resource() {
    override val pieceName = "all"

    override fun actsAsAnyResource() = true
}

object NONE: Resource() {
    override val pieceName = "none"
}
