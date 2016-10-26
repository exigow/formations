package rendering

import core.Camera
import rendering.renderers.Renderable

class ImmediateDrawCall(private val call: () -> Unit): Renderable {

  override fun depth() = 0f

  override fun isVisible(camera: Camera) = true

  fun draw() = call.invoke()

}