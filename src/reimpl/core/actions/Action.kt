package core.actions

import core.input.event.EventBundle
import kotlin.reflect.KClass

interface Action {

  fun conflictsWith(): Set<KClass<*>> = emptySet()

  fun events(): EventBundle

}