package logic.selection;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import world.models.Entity;

import java.util.Collection;
import java.util.stream.Collectors;

public class RectangleSimpleSelection {

  private final Rectangle rectangle;

  public RectangleSimpleSelection(Rectangle rectangle) {
    this.rectangle = rectangle;
  }

  // todo
  public Collection<Entity> selectFrom(Collection<Entity> from) {
    return from.stream().filter(e -> isInside(e.position)).collect(Collectors.toList());
  }

  private boolean isInside(Vector2 coordinate) {
    return rectangle.contains(coordinate.x, coordinate.y);
  }

}
