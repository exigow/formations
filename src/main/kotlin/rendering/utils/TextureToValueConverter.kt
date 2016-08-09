package rendering.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import commons.FloatArray2D


object TextureToValueConverter {

  fun convert(texture: Texture, conversion: (Color) -> Float): FloatArray2D {
    val pixmap = texture.toConsumedPixmap()
    val array = FloatArray2D(pixmap.width, pixmap.height)
    for (x in 0..(pixmap.width - 1)) {
      for (y in 0..(pixmap.height - 1)) {
        val color = pixmap.readColor(x, y)
        array[x, y] = conversion.invoke(color)
      }
    }
    return array
  }

  private fun Texture.toConsumedPixmap(): Pixmap {
    textureData.prepare()
    return textureData.consumePixmap()
  }

  private fun Pixmap.readColor(x: Int, y: Int) = Color(getPixel(x, y))

}