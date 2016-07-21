package rendering.trails

import rendering.Draw
import rendering.paths.Path


object TrailsDebugRenderer {

  fun render(buffer: TrailsBuffer) {
    buffer.trails.forEach {
      val dottedPath = Path(it.list.reversed().map { it.position }).populate(32f).slice()
      Draw.paths(dottedPath)
      var prev = it.list.first
      for (struct in it.list) {
        val rel = (struct.position - prev.position).rotate90Left().normalize()
        val scaled = rel * (.25f + .75f * struct.life) * 16
        Draw.line(struct.position - scaled, struct.position + scaled)
        prev = struct
      }
      Draw.diamond(it.list.last.position, 4f)
      Draw.diamond(it.list.first.position, 4f)
    }
  }

}