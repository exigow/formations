package rendering.utils

import rendering.paths.Path
import rendering.trails.Trail
import rendering.trails.TrailIterator


object TrailsDebugRenderer {

  fun render(trail: Trail) {
    TrailIterator.iterate(trail.list, {
      struct, vec ->
      val scaled = vec * 16// * (.25f + struct.life * .75f)
      val where = struct.position
      Draw.line(where + scaled, where - scaled, alpha = .5f)
    })
    drawAsPath(trail.list)
    Draw.diamond(trail.list.last.position, 4f)
    Draw.diamond(trail.list.first.position, 4f)
  }

  private fun drawAsPath(list: List<Trail.Structure>) = Draw.paths(Path(list.reversed().map { it.position }).populate(32f).slice())

}