package core.actions

import core.input.event.EventBundle
import kotlin.reflect.KClass

interface Action {

  fun discardOn(): Set<KClass<*>> = emptySet()

  fun events(): EventBundle

  fun isWorking(): Boolean = false

}