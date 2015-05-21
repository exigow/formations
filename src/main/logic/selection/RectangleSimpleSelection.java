package logic.selection;

import com.badlogic.gdx.math.Rectangle;
import world.attributes.Coordinate;
import world.models.Entity;

import java.util.Collection;
import java.util.stream.Collectors;

public class RectangleSimpleSelection {

  private final Rectangle rectangle;

  public RectangleSimpleSelection(Rectangle rectangle) {
    this.rectangle = rectangle;
  }

  public Collection<Entity> selectFrom(Collection<Entity> from) {
    return from.stream()
      .filter(this::isInside)
      .collect(Collectors.toSet());
  }

  private boolean isInside(Coordinate coordinate) {
    return rectangle.contains(coordinate.getX(), coordinate.getY());
  }

}
