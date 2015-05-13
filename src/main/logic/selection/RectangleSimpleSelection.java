package logic.selection;

import world.attributes.Coordinate;
import com.badlogic.gdx.math.Rectangle;

import java.util.Set;
import java.util.stream.Collectors;

public class RectangleSimpleSelection<T extends Coordinate> implements Selection<T>{

  private final Rectangle rectangle;

  public RectangleSimpleSelection(Rectangle rectangle) {
    this.rectangle = rectangle;
  }

  @Override
  public Set<T> selectFrom(Set<T> from) {
    return from.stream()
      .filter(this::isInside)
      .collect(Collectors.toSet());
  }

  private boolean isInside(Coordinate coordinate) {
    return rectangle.contains(coordinate.getX(), coordinate.getY());
  }

}
