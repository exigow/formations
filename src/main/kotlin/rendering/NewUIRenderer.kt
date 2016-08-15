package rendering

import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2
import core.Camera
import game.PlayerContext
import game.Ship
import game.Squad
import rendering.utils.Draw


class NewUIRenderer(private val camera: Camera, private val context: PlayerContext) {

  private val font = BitmapFont(Gdx.files.internal("data/fonts/microgramma.fnt"), true)
  private val batch = SpriteBatch()

  fun render() {
    batch.projectionMatrix = camera.projectionMatrix()
    if (context.isHovering()) {
      drawForEach(context.hovered!!.ships, "arrow")
      drawDescription(context.hovered!!)
    }
    drawForEach(context.highlighted.flatMap { it.ships }, "highlight")

    if (context.selectionRect != null)
      Draw.rectangle(context.selectionRect!!)
  }

  private fun drawDescription(squad: Squad) {
    val type = squad.ships.first().config.displayedName
    val amount = squad.ships.size
    val text = "$type ($amount/$amount)"
    drawText(text, camera.mouseScreenPosition() + Vec2(32, 32))
  }

  private fun drawForEach(ships: Collection<Ship>, asset: String) {
    batch.begin()
    ships.forEach {
      draw(asset, it.position)
    }
    batch.end()
  }

  private fun drawText(text: String, position: Vec2) {
    batch.projectionMatrix = camera.screenMatrix()
    batch.begin()
    font.draw(batch, text, position.x, position.y)
    batch.end()
  }

  private fun draw(materialName: String, where: Vec2) {
    val tex = AssetsManager.peekMaterial(materialName).diffuse!!
    val scale = camera.renderingScale()
    batch.draw(tex, where.x - tex.width / 2f * scale, where.y - tex.height / 2f * scale, tex.width * scale, tex.height * scale)
  }

}