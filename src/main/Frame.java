import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import helpers.CoordinatesToRectangleConverter;
import helpers.Logger;
import helpers.WorldDebugInitializer;
import logic.CameraController;
import logic.input.Input;
import logic.input.Key;
import logic.input.State;
import renderers.DebugRenderer;
import world.Collective;
import world.Entity;
import world.Group;
import world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Frame {

  private final Vector3 cameraEye = new Vector3(0, 0, 1);
  private final Vector3 cameraEyeTarget = new Vector3(cameraEye);
  private final DebugRenderer renderer = new DebugRenderer();
  private final OrthographicCamera camera = new OrthographicCamera();
  private final World world = WorldDebugInitializer.init();
  private final CameraController controller = new CameraController();
  private final Set<Group> selected = new HashSet<>();
  private final Set<Group> wantToSelect = new HashSet<>();
  private boolean isSelecting = false;
  public final Vector2 pinPoint = new Vector2();
  public final Rectangle selectionRectangle = new Rectangle();

  {
    camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Input.register(Key.MOUSE_LEFT, state -> {
      switch (state) {
        case UP:
          pinPoint.set(Input.mouse(camera));
          isSelecting = true;
          break;
        case DOWN:
          updateSelection();
          selected.clear();
          selected.addAll(wantToSelect);
          Logger.log(selected.toString());
          wantToSelect.clear();
          isSelecting = false;
          break;
      }
    });
    Input.register(Key.MOUSE_RIGHT, state -> {
      if (state == State.DOWN) {
        Logger.log("selected groups " + selected);
        Collective newone = world.instantiateCollective(selected);
      }
    });
  }


  private void updateSelection() {
    wantToSelect.clear();
    wantToSelect.addAll(updateSelection(Input.mouse(camera), world.allGroups()));
  }

  public void update() {
    cameraEyeTarget.add(controller.actualMovementVector().scl(8, 8, .1f));
    cameraEye.x += (cameraEyeTarget.x - cameraEye.x) * .25f;
    cameraEye.y += (cameraEyeTarget.y - cameraEye.y) * .25f;
    cameraEye.z += (cameraEyeTarget.z - cameraEye.z) * .25f;
    camera.position.set(cameraEye.x, cameraEye.y, 0);
    camera.zoom = cameraEye.z;
    camera.update();
  }

  public void render() {
    renderer.renderWorld(world, camera.combined);
    renderer.renderEntities(entitiesOf(selected), Color.ORANGE, 2);
    if (isSelecting) {
      updateSelection();
      renderer.renderEntities(entitiesOf(wantToSelect), Color.CYAN, 4);
      renderer.renderRectangle(selectionRectangle, Color.WHITE);
    }
  }

  public Collection<Group> updateSelection(Vector2 to, Collection<Group> groups) {
    Rectangle fixed = CoordinatesToRectangleConverter.convert(pinPoint, to);
    selectionRectangle.set(fixed);
    Collection<Group> result = new ArrayList<>();
    for (Group group : groups)
      for (Entity entity : group.entities)
        if (isInside(entity.position))
          result.add(group);
    return result;
  }

  private static Set<Entity> entitiesOf(Collection<Group> groups) {
    Set<Entity> entities = new HashSet<>();
    for (Group group : groups)
      entities.addAll(group.entities);
    return entities;
  }

  private boolean isInside(Vector2 coordinate) {
    return selectionRectangle.contains(coordinate.x, coordinate.y);
  }



}
