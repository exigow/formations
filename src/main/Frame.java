import agents.InputAgent;
import agents.RenderAgent;
import logic.input.TriggerAction;
import logic.input.adapter.Adapter;
import logic.input.adapter.TriggerAdapter;
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

import java.util.HashSet;
import java.util.Set;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final Camera camera = new Camera();
  private final World world = new World();
  private final Coordinate pinPoint = new CoordinateSimple();
  private final Rectangle fixedRect = new Rectangle();
  private final Set<Entity> selected = new HashSet<>();
  private final Set<Entity> wantToSelect = new HashSet<>();

  { // todo to co tu jest to eventy pozaklatkowe (czyli takie jak być powinny)
    // todo należy zrobić refactor z tym związany, bo frame-based jest chujowy i czasami nie wyłapuje zmiany stanu,
    // todo ...albo nawet całego inputu, jeśli klikniemy myszką szybko między refreshami
    Adapter adapter = TriggerAdapter.instantiate(Trigger.MOUSE_LEFT,
      new TriggerAction() {

        @Override
        public void onPress() {
          System.out.println("asd");
        }

        @Override
        public void onRelease() {
          System.out.println("asd");
        }

      }
    );
    Gdx.input.setInputProcessor(adapter);
  }

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    input.update(camera);
    switch (input.stateOf(Trigger.MOUSE_LEFT)) {
      case PRESS:
        pinPoint.set(input.getMouseWorld());
        break;
      case HOLD:
        Rectangle fixed = CoordinatesToRectangleConverter.convert(pinPoint, input.getMouseWorld());
        fixedRect.set(fixed);
        wantToSelect.clear();
        wantToSelect.addAll(new RectangleSimpleSelection<Entity>(fixedRect).selectFrom(world.entities));
        break;
      case RELEASE:
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
    if (input.stateOf(Trigger.MOUSE_LEFT).equals(Tick.HOLD))
      RectangleRenderer.render(agent, fixedRect);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
