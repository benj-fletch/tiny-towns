package com.benjfletch.tinytowns.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Super class for any [GamePiece] which is, or can act as, part of a [Building] for the build process. */
@Serializable
@SerialName("resource")
abstract class Resource: GamePiece {
    /** Whether this [Resource] should be considered equal to EVERY other resource type */
    open fun actsAsAnyResource(): Boolean = false
    /** Whether this [Resource] should be removed after being used as part of a build process */
    open fun removeAfterBuild(): Boolean = true

    companion object {
        /** All resources which are "basic". i.e each of the resources themselves, not any specialisations */
        fun basicResources(): Set<Resource> = setOf(BRICK(), GLASS(), STONE(), WOOD(), WHEAT())
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

@Serializable
@SerialName("brick")
data class BRICK(override val pieceName: String = "brick"): Resource() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Serializable
@SerialName("glass")
data class GLASS(override val pieceName: String = "glass"): Resource() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Serializable
@SerialName("stone")
data class STONE(override val pieceName: String = "stone"): Resource() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Serializable
@SerialName("wood")
data class WOOD(override val pieceName: String = "wood"): Resource() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Serializable
@SerialName("wheat")
data class WHEAT(override val pieceName: String = "wheat"): Resource() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Serializable
@SerialName("all")
data class ALL(override val pieceName: String = "all"): Resource() {
    override fun actsAsAnyResource() = true
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@Serializable
@SerialName("none")
data class NONE(override val pieceName: String = "none"): Resource() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
