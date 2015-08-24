import agents.InputAgent;
import logic.camera.Camera;
import logic.camera.rules.ManualKeyboardRule;
import logic.camera.rules.ManualMouseRule;
import logic.camera.rules.Resolver;
import logic.input.Key;
import logic.selection.Selector;
import renderers.DebugRenderer;
import world.World;
import world.models.Entity;
import world.models.Group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Frame {

  private final DebugRenderer renderer = new DebugRenderer();
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
    renderer.clearBackground();
    renderer.shape.setProjectionMatrix(camera.getOrthographicCamera().combined);
    renderer.renderEntities(world.groups.stream().map(g -> g.entities).flatMap(Collection::stream).collect(Collectors.toList()));
    renderer.renderSelection(entitiesOf(selected), 8);
    renderer.renderSelection(entitiesOf(wantToSelect), 16);
    if (isSelecting)
      renderer.renderRectangle(selector.getRectangle());
  }

  private static Collection<Entity> entitiesOf(Collection<Group> groups) {
    Collection<Entity> result = new ArrayList<>();
    for (Group group : groups)
      result.addAll(group.entities);
    return result;
  }

}
