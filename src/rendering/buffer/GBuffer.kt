package rendering.buffer

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer


class GBuffer(val color: FrameBuffer, val emissive: FrameBuffer) {

  companion object  {

    fun setUp(width: Int, height: Int) = GBuffer(
      color = setUpSubBuffer(width, height),
      emissive = setUpSubBuffer(width / 2, height / 2)
    )

    private fun setUpSubBuffer(width: Int, height: Int): FrameBuffer {
      val buffer = FrameBuffer(Pixmap.Format.RGB888, width, height, false);
      val wrap = Texture.TextureWrap.ClampToEdge
      buffer.colorBufferTexture.setWrap(wrap, wrap);
      return buffer;
    }

  }

}