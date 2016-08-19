package rendering.canvas

import assets.AssetsManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import Vec2
import java.util.*


class ShaderEffect(private val shader: ShaderProgram) {

  private var textureBindings = HashMap<String, Texture>()
  private var vecBindings = HashMap<String, FloatArray>()

  fun bind(uniformName: String, texture: Texture): ShaderEffect {
    textureBindings.put(uniformName, texture)
    return this
  }

  fun bind(uniformName: String, canvas: Canvas) = bind(uniformName, canvas.texture)

  fun parametrize(uniformName: String, vector: Vec2): ShaderEffect {
    vecBindings.put(uniformName, vector.toFloatArray())
    return this
  }

  fun parametrize(uniformName: String, value: Float): ShaderEffect {
    vecBindings.put(uniformName, floatArrayOf(value))
    return this
  }

  fun showAsQuad() {
    shader.begin()
    var bindPivot = 0
    for (entry in textureBindings) {
      entry.value.bind(bindPivot)
      shader.setUniformi(entry.key, bindPivot)
      bindPivot++
    }
    for (entry in vecBindings) {
      when (entry.value.size) {
        1 -> shader.setUniformf(entry.key, entry.value.first())
        2 -> shader.setUniform2fv(entry.key, entry.value, 0, 2)
        else -> RuntimeException("Unsupported float array parameter")
      }
    }
    FullscreenQuad.renderWith(shader)
    shader.end()
  }

  companion object {

    fun fromShader(shaderAssetName: String) = ShaderEffect(AssetsManager.peekShader(shaderAssetName))

  }

}