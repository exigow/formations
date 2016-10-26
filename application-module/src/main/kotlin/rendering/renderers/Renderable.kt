package rendering.renderers

import core.Camera

// todo rename to DrawCall ???
interface Renderable {

  fun depth(): Float

  fun isVisible(camera: Camera): Boolean

}


