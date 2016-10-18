package rendering.renderers

import core.Camera


interface Renderable {

  fun depth(): Float

  fun isVisible(camera: Camera): Boolean

}


