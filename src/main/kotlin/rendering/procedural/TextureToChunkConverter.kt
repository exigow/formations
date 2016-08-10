package rendering.procedural

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import commons.math.Vec2
import java.util.*


object TextureToChunkConverter {

  fun convert(texture: Texture, conversion: (Color) -> Float): Collection<Chunk> {
    val pixmap = texture.toConsumedPixmap()
    val result = ArrayList<Chunk>()
    for (x in 0..(pixmap.width - 1)) {
      for (y in 0..(pixmap.height - 1)) {
        val color = pixmap.readColor(x, y)
        result += Chunk(Vec2(x, y), conversion.invoke(color))
      }
    }
    return result
  }

  private fun Texture.toConsumedPixmap(): Pixmap {
    textureData.prepare()
    return textureData.consumePixmap()
  }

  private fun Pixmap.readColor(x: Int, y: Int) = Color(getPixel(x, y))

}