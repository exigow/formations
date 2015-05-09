import agents.InputAgent;
import agents.RenderAgent;
import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import logic.camera.Camera;
import logic.input.Trigger;
import logic.input.states.Tick;
import logic.selection.RectangleSimpleSelection;
import models.CoordinateSimple;
import models.Entity;
import models.World;
import models.helpers.CoordinatesToRectangleConverter;
import renderers.EntityRenderer;
import renderers.RectangleRenderer;
import renderers.SelectionRenderer;
import renderers.VariablesRenderer;

import java.util.HashSet;
import java.util.Set;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final VariablesRenderer variables = new VariablesRenderer();
  private final Camera camera = new Camera();
  private final World world = new World();
  private final Coordinate pinPoint = new CoordinateSimple();
  private final Rectangle fixedRect = new Rectangle();
  private final Set<Entity> selected = new HashSet<>();
  private final Set<Entity> wantToSelect = new HashSet<>();

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    input.update(camera);
    variables.update("fps", Gdx.graphics.getFramesPerSecond());
    switch (input.stateOf(Trigger.MOUSE_LEFT)) {
      case ON_PRESS:
        pinPoint.set(input.getMouseWorld());
        break;
      case ON_HOLD:
        Rectangle fixed = CoordinatesToRectangleConverter.convert(pinPoint, input.getMouseWorld());
        fixedRect.set(fixed);
        wantToSelect.clear();
        wantToSelect.addAll(new RectangleSimpleSelection<Entity>(fixedRect).selectFrom(world.entities));
        break;
      case ON_RELEASE:
        selected.clear();
        selected.addAll(wantToSelect);
        wantToSelect.clear();
        break;
    }
  }

  public void render() {
    clearBackground();
    agent.setProjection(camera);
    EntityRenderer.render(agent, world.entities);
    SelectionRenderer.render(agent, selected, 8);
    SelectionRenderer.render(agent, wantToSelect, 16);
    if (input.stateOf(Trigger.MOUSE_LEFT).equals(Tick.ON_HOLD))
      RectangleRenderer.render(agent, fixedRect);
    variables.render(agent);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
