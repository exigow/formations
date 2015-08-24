import agents.InputAgent;
import agents.RenderAgent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import logic.camera.Camera;
import logic.camera.rules.ManualKeyboardRule;
import logic.camera.rules.ManualMouseRule;
import logic.camera.rules.Resolver;
import logic.input.Key;
import logic.selection.Selector;
import renderers.EntityRenderer;
import renderers.RectangleRenderer;
import renderers.SelectionRenderer;
import world.World;
import world.models.Entity;
import world.models.Group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final Camera camera = new Camera(new Resolver(new ManualKeyboardRule(input), new ManualMouseRule()));
  private final World world = new World();
  private final Collection<Group> selected = new HashSet<>();
  private final Collection<Group> wantToSelect = new HashSet<>();
  private boolean isSelecting = false;
  private final Selector selector = new Selector();

  {
    input.register(Key.MOUSE_LEFT,
      (state) -> {
        switch (state) {
          case PRESSED:
            selector.start(input.mouse(camera));
            isSelecting = true;
            break;
          case RELEASED:
            updateSelection();
            flush(wantToSelect, selected);
            isSelecting = false;
            break;
        }
      }
    );
  }

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    if (isSelecting)
      updateSelection();
  }

  private void updateSelection() {
    wantToSelect.clear();
    wantToSelect.addAll(selector.update(input.mouse(camera), world.groups));
  }

  private static <T> void flush(Collection<T> from, Collection<T> to) {
    to.clear();
    to.addAll(from);
    from.clear();
  }

  public void render() {
    clearBackground();
    agent.setProjection(camera);
    EntityRenderer.render(agent, world.allEntities());
    SelectionRenderer.render(agent, entitiesOf(selected), 8);
    SelectionRenderer.render(agent, entitiesOf(wantToSelect), 16);
    if (isSelecting)
      RectangleRenderer.render(agent, selector.getRectangle());
  }

  public static Collection<Entity> entitiesOf(Collection<Group> groups) {
    Collection<Entity> result = new ArrayList<>();
    for (Group group : groups)
      result.addAll(group.entities);
    return result;
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
