package rendering

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.GLOnlyTextureData
import java.nio.ByteBuffer
import java.nio.ByteOrder

// todo https://github.com/libgdx/libgdx/blob/055b9f762e90303d3d5367b2e974bbaf595a7c64/tests/gdx-tests/src/com/badlogic/gdx/tests/g3d/MultipleRenderTargetTest.java

class MRTFrameBuffer(val width: Int, val height: Int) {

  private var colorTextures: Array<Texture>? = null
  private var defaultFramebufferHandle: Int = 0
  private var defaultFramebufferHandleInitialized = false
  private var framebufferHandle: Int = 0

  init {
    //build()
    //addManagedFrameBuffer(Gdx.app, this)
  }

  private fun createColorTexture(min: Texture.TextureFilter, mag: Texture.TextureFilter, internalformat: Int, format: Int, type: Int): Texture {
    val data = GLOnlyTextureData(width, height, 0, internalformat, format, type)
    val result = Texture(data)
    result.setFilter(min, mag)
    result.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge)
    return result
  }

  private fun createDepthTexture(): Texture {
    val data = GLOnlyTextureData(width, height, 0, GL30.GL_DEPTH_COMPONENT32F, GL30.GL_DEPTH_COMPONENT,
      GL30.GL_FLOAT)
    val result = Texture(data)
    result.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
    result.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge)
    return result
  }

  private fun build() {
    val gl = Gdx.gl20

    // iOS uses a different framebuffer handle! (not necessarily 0)
    if (!defaultFramebufferHandleInitialized) {
      defaultFramebufferHandleInitialized = true
      if (Gdx.app.type == Application.ApplicationType.iOS) {
        val intbuf = ByteBuffer.allocateDirect(16 * Integer.SIZE / 8).order(ByteOrder.nativeOrder()).asIntBuffer()
        gl.glGetIntegerv(GL20.GL_FRAMEBUFFER_BINDING, intbuf)
        defaultFramebufferHandle = intbuf.get(0)
      } else {
        defaultFramebufferHandle = 0
      }
    }

    /*colorTextures = com.badlogic.gdx.utils.Array<Texture>()

    framebufferHandle = gl.glGenFramebuffer()
    gl.glBindFramebuffer(GL20.GL_FRAMEBUFFER, framebufferHandle)

    //rgba
    val diffuse = createColorTexture(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, GL30.GL_RGBA8,
      GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE)
    //rgb
    val normal = createColorTexture(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, GL30.GL_RGB8,
      GL30.GL_RGB, GL30.GL_UNSIGNED_BYTE)
    //rgb
    val position = createColorTexture(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, GL30.GL_RGB8,
      GL30.GL_RGB, GL30.GL_UNSIGNED_BYTE)
    val depth = createDepthTexture()

    colorTextures!!.add(diffuse)
    colorTextures!!.add(normal)
    colorTextures!!.add(position)
    colorTextures!!.add(depth)

    gl.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D,
      diffuse.textureObjectHandle, 0)
    gl.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_TEXTURE_2D,
      normal.textureObjectHandle, 0)
    gl.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT2, GL30.GL_TEXTURE_2D,
      position.textureObjectHandle, 0)
    gl.glFramebufferTexture2D(GL20.GL_FRAMEBUFFER, GL20.GL_DEPTH_ATTACHMENT, GL20.GL_TEXTURE_2D,
      depth.textureObjectHandle, 0)

    val buffer = BufferUtils.newIntBuffer(3)
    buffer.put(GL30.GL_COLOR_ATTACHMENT0)
    buffer.put(GL30.GL_COLOR_ATTACHMENT1)
    buffer.put(GL30.GL_COLOR_ATTACHMENT2)
    buffer.position(0)
    Gdx.gl30.glDrawBuffers(3, buffer)

    gl.glBindRenderbuffer(GL20.GL_RENDERBUFFER, 0)
    gl.glBindTexture(GL20.GL_TEXTURE_2D, 0)

    val result = gl.glCheckFramebufferStatus(GL20.GL_FRAMEBUFFER)

    gl.glBindFramebuffer(GL20.GL_FRAMEBUFFER, defaultFramebufferHandle)

    if (result != GL20.GL_FRAMEBUFFER_COMPLETE) {
      for (colorTexture in colorTextures!!)
        disposeColorTexture(colorTexture)

      gl.glDeleteFramebuffer(framebufferHandle)

      if (result == GL20.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT)
        throw IllegalStateException("frame buffer couldn't be constructed: incomplete attachment")
      if (result == GL20.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS)
        throw IllegalStateException("frame buffer couldn't be constructed: incomplete dimensions")
      if (result == GL20.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT)
        throw IllegalStateException("frame buffer couldn't be constructed: missing attachment")
      if (result == GL20.GL_FRAMEBUFFER_UNSUPPORTED)
        throw IllegalStateException("frame buffer couldn't be constructed: unsupported combination of formats")
      throw IllegalStateException("frame buffer couldn't be constructed: unknown error " + result)
    }
    */
  }

}