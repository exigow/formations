package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.BufferUtils
import rendering.canvas.Canvas
import rendering.canvas.ShaderEffect
import rendering.utils.FastBloomTool
import java.nio.IntBuffer


class GBuffer {

  /**
   * Buffer overview:
   * +--------------+--------------+--------------+--------------+
   * |     RED 8    |    GREEN 8   |    BLUE 8    |    ALPHA 8   |
   * +--------------+--------------+--------------+--------------+
   * |   diffuse.R  |   diffuse.G  |   diffuse.B  |      -       |
   * +--------------+--------------+--------------+--------------+
   * |  emissive.R  |  emissive.G  |  emissive.B  |      -       |
   * +--------------+--------------+--------------+--------------+
   * |   motion.X   |   motion.Y   |    depth     |      -       |
   */

  val width = Gdx.graphics.width
  val height =  Gdx.graphics.height

  val fboHandle: Int
  val drawBuffersCall = createDrawBuffersCall()
  val diffuseTexture: Texture
  val emissiveTexture: Texture
  val specializedTexture: Texture

  private val combined = Canvas.setUpRGB(width, height)
  private val bloom = FastBloomTool(256, 256)

  init {
    diffuseTexture = createBufferTexture(width, height)
    emissiveTexture = createBufferTexture(width, height)
    specializedTexture = createBufferTexture(width, height)
    fboHandle = Gdx.gl30.glGenFramebuffer()
    bindFramebuffer(fboHandle)
    attachTextureToFramebuffer(diffuseTexture, GL30.GL_COLOR_ATTACHMENT0)
    attachTextureToFramebuffer(emissiveTexture, GL30.GL_COLOR_ATTACHMENT1)
    attachTextureToFramebuffer(specializedTexture, GL30.GL_COLOR_ATTACHMENT2)
  }

  private fun attachTextureToFramebuffer(texture: Texture, where: Int) {
    texture.bind()
    Gdx.gl30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, where, texture.glTarget, texture.textureObjectHandle, 0)
  }

  private fun createBufferTexture(width: Int, height: Int): Texture {
    val d = Texture(width, height, Pixmap.Format.RGB888)
    d.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    d.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge)
    return d
  }

  fun paint(f: () -> Unit) {
    bindFramebuffer(fboHandle)
    Gdx.gl30.glDrawBuffers(2, drawBuffersCall)
    f.invoke()
    unbindFramebuffer()
  }

  fun clear() {
    paint {
      Gdx.gl30.glClearColor(0f, 0f, 0f, 1f)
      Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT)
    }
  }

  private fun bindFramebuffer(handle: Int) {
    Gdx.gl30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, handle)
  }

  private fun unbindFramebuffer() = bindFramebuffer(0)

  fun showCombined() {
    /*combined.paint {
      ShaderEffect.fromShader("mixDiffuseWithEmissive")
        .bind("textureDiffuse", diffuseTexture)
        .bind("textureEmissive", emissiveTexture)
        .parametrize("noiseOffset", (System.currentTimeMillis() % 16).toFloat())
        .showAsQuad()
    }
    val bloomed = bloom.process(combined)
    combined.paint {
      Blending.ADDITIVE.decorate {
        bloomed.showAsQuad()
      }
    }
    combined.showAsQuad()*/
    ShaderEffect.fromShader("fullscreenQuadShader")
      .bind("texture", specializedTexture)
      .showAsQuad()
  }

  private fun createDrawBuffersCall(): IntBuffer {
    val drawBuffersCall = BufferUtils.newIntBuffer(3)
      .put(GL30.GL_COLOR_ATTACHMENT0)
      .put(GL30.GL_COLOR_ATTACHMENT1)
      .put(GL30.GL_COLOR_ATTACHMENT2)
    drawBuffersCall.rewind()
    return drawBuffersCall
  }

}