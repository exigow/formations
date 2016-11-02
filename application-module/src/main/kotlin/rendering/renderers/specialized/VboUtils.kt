package rendering.renderers.specialized

import assets.AssetsManager
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import Color
import rendering.materials.Material

object VboUtils {

  fun createCommonVbo(capacity: Int) = Mesh(true, capacity, 0,
    VertexAttribute(VertexAttributes.Usage.Position, 3, "positionAttr"),
    VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr"),
    VertexAttribute(VertexAttributes.Usage.Generic, 1, "alphaAttr")
  )

  fun paintWithMaterialShader(material: Material, matrix: Matrix4, color: Color, meshDrawFunction: (shader: ShaderProgram) -> Unit) {
    val shader = AssetsManager.peekShader("material")
    material.diffuse!!.bind(0)
    if (material.emissive != null)
      material.emissive.bind(1)
    else
      AssetsManager.peekMaterial("black").diffuse!!.bind(1)
    shader.begin()
    shader.setUniformMatrix("projection", matrix)
    shader.setUniformi("textureDiffuse", 0)
    shader.setUniformi("textureEmissive", 1)
    shader.setUniform3fv("ambientColor", color.toFloatArray(), 0, 3)
    meshDrawFunction.invoke(shader)
    shader.end()
  }

}
