package rendering

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.GLOnlyTextureData
import com.badlogic.gdx.utils.BufferUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder

// todo https://github.com/libgdx/libgdx/blob/055b9f762e90303d3d5367b2e974bbaf595a7c64/tests/gdx-tests/src/com/badlogic/gdx/tests/g3d/MultipleRenderTargetTest.java

class MRTFrameBuffer(val width: Int, val height: Int) {

  val handle: Int
  val colorTextureHandlers: Array<Texture>

  init {
    handle = Gdx.gl20.glGenFramebuffer()
    Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, handle)

    val diffuse  = createColorTexture(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE)
    val emissive = createColorTexture(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE)

    colorTextureHandlers = arrayOf(diffuse, emissive)

    Gdx.gl20.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, diffuse.textureObjectHandle, 0)
    Gdx.gl20.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_TEXTURE_2D, emissive.textureObjectHandle, 0)

    val attachmentsCount = 2
    val buffer = BufferUtils.newIntBuffer(attachmentsCount)
    buffer.put(GL30.GL_COLOR_ATTACHMENT0)
    buffer.put(GL30.GL_COLOR_ATTACHMENT1)
    buffer.position(0)
    Gdx.gl30.glDrawBuffers(attachmentsCount, buffer)

    Gdx.gl20.glBindRenderbuffer(GL20.GL_RENDERBUFFER, 0)
    Gdx.gl20.glBindTexture(GL20.GL_TEXTURE_2D, 0)

    val result = Gdx.gl20.glCheckFramebufferStatus(GL20.GL_FRAMEBUFFER)

    Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, 0)

    if (result != GL20.GL_FRAMEBUFFER_COMPLETE)
      throw RuntimeException()
  }

  private fun createColorTexture(min: Texture.TextureFilter, mag: Texture.TextureFilter, internalformat: Int, format: Int, type: Int): Texture {
    val data = GLOnlyTextureData(width, height, 0, internalformat, format, type)
    val result = Texture(data)
    result.setFilter(min, mag)
    result.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge)
    return result
  }

  fun begin () {
    Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, handle);
    Gdx.gl20.glViewport(0, 0, width, height);
  }

  fun end() {
    Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, 0);
    Gdx.gl20.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height);
  }

  fun getBufferTexture(place: Int) = colorTextureHandlers[place]

}