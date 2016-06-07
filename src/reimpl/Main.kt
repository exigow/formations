import game.Ship
import game.World
import input.Input
import interaction.actions.ActionsRegistrar
import interaction.actions.CameraArrowsMovementAction
import interaction.actions.CameraMiddleClickMovementAction
import interaction.actions.CameraScrollZoomingAction

class Main {

  private val world = World.randomWorld()

  init {
    ActionsRegistrar.addAction(CameraMiddleClickMovementAction)
    ActionsRegistrar.addAction(CameraArrowsMovementAction)
    ActionsRegistrar.addAction(CameraScrollZoomingAction)
    ActionsRegistrar.bindAll()
  }

  fun onRender() {
    Input.update()

    Camera.update(1f)

    Renderer.reset()
    Renderer.renderGrid()
    for (ship: Ship in world.findAllShips())
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    Renderer.renderCircle(Input.Button.position(), 8f)

    //if (SelectionAction.isSelecting)
      //Renderer.renderRectangle(SelectionAction.rectangle())

    //Interaction.interact(world)
  }

}