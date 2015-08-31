import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import helpers.SelectionVectorsToRectangleConverter;
import helpers.WorldDebugInitializer;
import logic.CameraController;
import logic.input.Input;
import logic.input.Key;
import logic.input.State;
import renderers.WorldDebugRenderer;
import world.*;
import world.orders.Move;
import world.orders.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Frame {

  private final Vector3 cameraEye = new Vector3(0, 0, 1);
  private final Vector3 cameraEyeTarget = new Vector3(cameraEye);
  private final WorldDebugRenderer renderer = new WorldDebugRenderer();
  private final OrthographicCamera camera = new OrthographicCamera();
  private final World world = WorldDebugInitializer.init();
  private final CameraController controller = new CameraController();
  private final Set<Group> selected = new HashSet<>();
  private final Set<Group> wantToSelect = new HashSet<>();
  private boolean isSelecting = false;
  private final Vector2 pinPoint = new Vector2();
  private final Rectangle selectionRectangle = new Rectangle();

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
          wantToSelect.clear();
          isSelecting = false;
          break;
      }
    });
    Input.register(Key.MOUSE_RIGHT, state -> {
      if (state == State.DOWN) {
        Collective instantiated = world.instantiateCollective(selected);
        instantiated.orders.clear();
        Place place = new Place();
        place.position.set(Input.mouse(camera));
        place.direction = 0;
        instantiated.orders.add(new Move(place));
      }
    });
  }

  private void updateSelection() {
    wantToSelect.clear();
    wantToSelect.addAll(updateSelection(Input.mouse(camera), groupsOf(world)));
  }

  public void update() {
    cameraEyeTarget.add(controller.actualMovementVector().scl(8, 8, .1f));
    cameraEye.x += (cameraEyeTarget.x - cameraEye.x) * .25f;
    cameraEye.y += (cameraEyeTarget.y - cameraEye.y) * .25f;
    cameraEye.z += (cameraEyeTarget.z - cameraEye.z) * .25f;
    camera.position.set(cameraEye.x, cameraEye.y, 0);
    camera.zoom = cameraEye.z;
    camera.update();

    for (Collective collective : world.collectives) {
      if (collective.orders.isEmpty())
        continue;
      Order order = collective.orders.get(0);
      if (order instanceof Move) {
        Move moveOrder = (Move) order;
        for (Group group : collective.groups) {
          for (Ship ship : group.ships) {
            ship.moveTo(moveOrder.where);
          }
        }
      }
    }
  }

  public void render() {
    renderer.renderWorld(world, camera.combined);
    renderer.renderSelected(selected, 4);
    if (isSelecting) {
      updateSelection();
      renderer.renderSelected(wantToSelect, 8);
      renderer.renderSelection(selectionRectangle);
    }
  }

  public Collection<Group> updateSelection(Vector2 to, Collection<Group> groups) {
    Rectangle fixed = SelectionVectorsToRectangleConverter.convert(pinPoint, to);
    selectionRectangle.set(fixed);
    Collection<Group> result = new ArrayList<>();
    for (Group group : groups)
      result.addAll(group.ships.stream()
        .filter(ship -> isInsideSelection(ship.place.position))
        .map(ship -> group)
        .collect(Collectors.toList()));
    return result;
  }

  private boolean isInsideSelection(Vector2 coordinate) {
    return selectionRectangle.contains(coordinate.x, coordinate.y);
  }

  public Set<Group> groupsOf(World world) {
    Set<Group> result = new HashSet<>();
    for (Collective collective : world.collectives)
      result.addAll(collective.groups.stream().collect(Collectors.toList()));
    return result;
  }

}
