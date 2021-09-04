package com.benjfletch.tinytowns.model.masterbuildereffect

import com.benjfletch.tinytowns.model.Resource

interface MasterBuilderEffect

/** Specification of [MasterBuilderEffect] which removes the player's ability to choose certain [Resource] types */
interface RestrictedResourcesEffect: MasterBuilderEffect {
    /** Resources which cannot be placed when this effect is in play */
    val restrictedResources: Set<Resource>
}

/** Specification of [MasterBuilderEffect] which allows a player to switch a chosen resource for another one */
interface SwitchResourceEffect: MasterBuilderEffect {
    /** Set of resources which can be swapped */
    val switchable: Set<Resource>
    /** Set of resources which a resource can be swapped for */
    val switchableFor: Set<Resource>
}

/** Specification of [MasterBuilderEffect] which allows a resource to be used in a way other than being placed on a Board */
interface ConsumeResourceEffect: MasterBuilderEffect {
    /** Function to consume the resource and perform an action with it */
    fun consume(resource: Resource)
}
