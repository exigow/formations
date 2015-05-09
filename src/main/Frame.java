import agents.InputAgent;
import agents.RenderAgent;
import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import logic.camera.Camera;
import logic.input.Trigger;
import logic.selection.RectangleSimpleSelection;
import models.Entity;
import models.World;
import models.helpers.RectangleFixer;
import renderers.RectangleRenderer;
import renderers.VariablesRenderer;

import java.util.ArrayList;
import java.util.Collection;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final VariablesRenderer variables = new VariablesRenderer();
  private final Camera camera = new Camera();
  private final World world = new World();
  private final Rectangle temporaryRect = new Rectangle();
  private final Rectangle fixedRect = new Rectangle();
  //public final Collection<Entity> selected = new ArrayList<>();

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    input.update(camera);
    variables.update("fps", Gdx.graphics.getFramesPerSecond());
    switch (input.stateOf(Trigger.MOUSE_LEFT)) {
      case PRESS:
        temporaryRect.setPosition(input.getMouseWorld().getX(), input.getMouseWorld().getY());
        break;
      case RELEASE:
        Collection selected = new RectangleSimpleSelection<Entity>(fixedRect).selectFrom(world.entities);
        System.out.println(selected);
        break;
    }
    if (input.isPressed(Trigger.MOUSE_LEFT)) {
      updateRectangleSize(temporaryRect, input.getMouseWorld());
      Rectangle fix = RectangleFixer.fix(temporaryRect);
      fixedRect.set(fix);
    }
  }

  private static void updateRectangleSize(Rectangle rect, Coordinate pointer) {
    float deltaX = pointer.getX() - rect.getX();
    float deltaY = pointer.getY() - rect.getY();
    rect.setSize(deltaX, deltaY);
  }

  public void render() {
    clearBackground();
    agent.setProjection(camera);
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : world.entities)
      entity.render(agent);
    agent.shape.end();
    variables.render(agent);
    if (input.isPressed(Trigger.MOUSE_LEFT))
      RectangleRenderer.render(agent, fixedRect);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
