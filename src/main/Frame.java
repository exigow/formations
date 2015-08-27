import agents.InputAgent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import helpers.WorldDebugInitializer;
import logic.CameraController;
import logic.input.Key;
import logic.selection.Selector;
import renderers.DebugRenderer;
import world.World;
import world.models.Entity;
import world.models.Group;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Frame {

  private final Vector3 cameraEye = new Vector3(0, 0, 1);
  private final Vector3 cameraEyeTarget = new Vector3(0, 0, 1);
  private final DebugRenderer renderer = new DebugRenderer();
  private final InputAgent input = new InputAgent();
  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final World world = WorldDebugInitializer.init();
  private final Set<Group> selected = new HashSet<>();
  private final Set<Group> wantToSelect = new HashSet<>();
  private boolean isSelecting = false;
  private final Selector selector = new Selector();
  private final CameraController controller = new CameraController(input);

  {
    input.register(Key.MOUSE_LEFT,
      (state) -> {
        switch (state) {
          case UP:
            selector.start(input.mouse(camera));
            isSelecting = true;
            break;
          case DOWN:
            updateSelection();
            selected.clear();
            selected.addAll(wantToSelect);
            wantToSelect.clear();
            isSelecting = false;
            break;
        }
      }
    );
  }

  public void update(float deltaTime) {
    cameraEyeTarget.add(controller.actualMovementVector().scl(8, 8, .1f));
    cameraEye.x += (cameraEyeTarget.x - cameraEye.x) * .125f;
    cameraEye.y += (cameraEyeTarget.y - cameraEye.y) * .125f;
    cameraEye.z += (cameraEyeTarget.z - cameraEye.z) * .125f;
    //cameraEye
    camera.position.set(cameraEye.x, cameraEye.y, 0);
    camera.zoom = cameraEye.z;
    camera.update();
    if (isSelecting)
      updateSelection();
  }

  private void updateSelection() {
    wantToSelect.clear();
    wantToSelect.addAll(selector.update(input.mouse(camera), world.groups));
  }

  public void render() {
    renderer.clearBackground();
    renderer.shape.setProjectionMatrix(camera.combined);
    renderer.renderEntities(world.groups.stream().map(g -> g.entities).flatMap(Set::stream).collect(Collectors.toList()));
    renderer.renderSelection(entitiesOf(selected), 8);
    renderer.renderSelection(entitiesOf(wantToSelect), 16);
    if (isSelecting)
      renderer.renderRectangle(selector.getRectangle());
  }

  private static Set<Entity> entitiesOf(Set<Group> groups) {
    Set<Entity> result = new HashSet<>();
    for (Group group : groups)
      result.addAll(group.entities);
    return result;
  }

}
