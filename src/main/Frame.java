import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import helpers.SelectionVectorsToRectangleConverter;
import helpers.WorldDebugInitializer;
import logic.camera.CameraController;
import logic.input.Input;
import logic.input.Key;
import logic.input.State;
import rendering.WorldDebugRenderer;
import world.Collective;
import world.Place;
import world.Squad;
import world.World;
import world.orders.MoveOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Frame {

  private final CameraController cameraController = new CameraController();
  private final World world = WorldDebugInitializer.init();
  private final Set<Squad> selected = new HashSet<>();
  private final Set<Squad> wantToSelect = new HashSet<>();
  private boolean isSelecting = false;
  private final Vector2 pinPoint = new Vector2();
  private final Rectangle selectionRectangle = new Rectangle();

  {
    Input.register(Key.MOUSE_LEFT, state -> {
      switch (state) {
        case UP:
          pinPoint.set(cameraController.unproject(Input.mousePosition()));
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
        Place place = new Place();
        place.position.set(cameraController.unproject(Input.mousePosition()));
        place.direction = 0;
        instantiated.orders.clear();
        instantiated.orders.add(new MoveOrder(place));
      }
    });
  }

  private void updateSelection() {
    wantToSelect.clear();
    wantToSelect.addAll(updateSelection(cameraController.unproject(Input.mousePosition()), groupsOf(world)));
  }

  public void update() {
    cameraController.update();
    world.update();
  }

  public void render() {
    WorldDebugRenderer.renderWorld(world, cameraController.matrix());
    WorldDebugRenderer.renderSelected(selected, 4);
    if (isSelecting) {
      updateSelection();
      WorldDebugRenderer.renderSelected(wantToSelect, 8);
      WorldDebugRenderer.renderSelection(selectionRectangle);
    }
  }

  public Collection<Squad> updateSelection(Vector2 to, Collection<Squad> squads) {
    Rectangle fixed = SelectionVectorsToRectangleConverter.convert(pinPoint, to);
    selectionRectangle.set(fixed);
    Collection<Squad> result = new ArrayList<>();
    for (Squad squad : squads)
      result.addAll(squad.ships.stream()
        .filter(ship -> isInsideSelection(ship.place.position))
        .map(ship -> squad)
        .collect(Collectors.toList()));
    return result;
  }

  private boolean isInsideSelection(Vector2 coordinate) {
    return selectionRectangle.contains(coordinate.x, coordinate.y);
  }

  public Set<Squad> groupsOf(World world) {
    Set<Squad> result = new HashSet<>();
    for (Collective collective : world.collectives)
      result.addAll(collective.squads.stream().collect(Collectors.toList()));
    return result;
  }

}
