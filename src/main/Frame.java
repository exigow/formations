import agents.InputAgent;
import agents.RenderAgent;
import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import logging.Logger;
import logic.camera.Camera;
import logic.camera.rules.ManualKeyboardRule;
import logic.camera.rules.ManualMouseRule;
import logic.camera.rules.Resolver;
import logic.input.Key;
import logic.selection.RectangleSimpleSelection;
import models.CoordinateSimple;
import models.Entity;
import models.World;
import models.helpers.CoordinatesToRectangleConverter;
import renderers.EntityRenderer;
import renderers.RectangleRenderer;
import renderers.SelectionRenderer;

import java.util.HashSet;
import java.util.Set;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final Camera camera = new Camera(new Resolver(new ManualKeyboardRule(input), new ManualMouseRule()));
  private final World world = new World();
  private final Coordinate pinPoint = new CoordinateSimple();
  private final Rectangle fixedRect = new Rectangle();
  private final Set<Entity> selected = new HashSet<>();
  private final Set<Entity> wantToSelect = new HashSet<>();
  private boolean tick = false;
  {
    input.register(Key.MOUSE_LEFT,
      (state) -> {
        switch (state) {
          case PRESSED:
            pinPoint.set(input.mouse(camera));
            tick = true;
            break;
          case RELEASED:
            updateSelection();
            selected.clear();
            selected.addAll(wantToSelect);
            wantToSelect.clear();
            tick = false;
            break;
        }
      }
    );
  }

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    if (tick)
      updateSelection();
  }

  private void updateSelection() {
    Rectangle fixed = CoordinatesToRectangleConverter.convert(pinPoint, input.mouse(camera));
    fixedRect.set(fixed);
    wantToSelect.clear();
    wantToSelect.addAll(new RectangleSimpleSelection<Entity>(fixedRect).selectFrom(world.entities));
  }

  public void render() {
    clearBackground();
    agent.setProjection(camera);
    EntityRenderer.render(agent, world.entities);
    SelectionRenderer.render(agent, selected, 8);
    SelectionRenderer.render(agent, wantToSelect, 16);
    if (tick)
      RectangleRenderer.render(agent, fixedRect);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
