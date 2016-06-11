package actions

import core.input.EventRegistry
import kotlin.reflect.KClass

interface Action {

  fun discardOn(): Set<KClass<*>> = emptySet()

  fun bind(registry: EventRegistry)

}