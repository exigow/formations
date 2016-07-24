package rendering.trails

import rendering.Draw
import rendering.paths.Path


object TrailsDebugRenderer {

  fun render(buffer: TrailsBuffer) = buffer.trails.forEach {
    TrailStructuresIterator.iterate(it.list, {
      struct, vec ->
      val scaled = vec * 16// * (.25f + struct.life * .75f)
      val where = struct.position
      Draw.line(where + scaled, where - scaled, alpha = .5f)
    })
    drawAsPath(it.list)
    Draw.diamond(it.list.last.position, 4f)
    Draw.diamond(it.list.first.position, 4f)
  }

  private fun drawAsPath(list: List<TrailsBuffer.Structure>) = Draw.paths(Path(list.reversed().map { it.position }).populate(32f).slice())

}