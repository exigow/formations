package rendering.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2


object FontRenderer {

  private val font = BitmapFont(Gdx.files.internal("data/fonts/microgramma.fnt"), true)
  private val batch = SpriteBatch()

  fun draw(text: String, position: Vec2, matrix: Matrix4) {
    batch.projectionMatrix = matrix
    batch.begin()
    font.draw(batch, text, position.x, position.y)
    batch.end()
  }

}