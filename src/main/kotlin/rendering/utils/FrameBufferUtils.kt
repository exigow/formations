package rendering.utils

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer

object FrameBufferUtils {

  fun create(size: Int) = create(size, size)

  fun create(width: Int, height: Int): FrameBuffer {
    val buffer = FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    val wrap = Texture.TextureWrap.ClampToEdge
    buffer.colorBufferTexture.setWrap(wrap, wrap);
    return buffer;
  }

  fun FrameBuffer.paintOn(f: () -> Unit) {
    begin()
    f.invoke()
    end()
  }

}