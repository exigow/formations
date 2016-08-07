package rendering.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture

class PixelIterator(val texture: Texture) {

  private val pixmap: Pixmap

  init {
    texture.textureData.prepare()
    pixmap = texture.textureData.consumePixmap()
  }

  fun iterate(f: (x: Int, y: Int, color: Color) -> Unit) {
    for (sx in 0..pixmap.width)
      for (sy in 0..pixmap.height)
        f.invoke(sx, sy, colorOf(sx, sy))
  }

  private fun colorOf(x: Int, y: Int) = Color(pixmap.getPixel(x, y))

}