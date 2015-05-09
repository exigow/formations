package logic.selection;

import attributes.Coordinate;
import com.badlogic.gdx.math.Rectangle;

import java.util.Collection;
import java.util.stream.Collectors;

public class RectangleSimpleSelection<T extends Coordinate> implements Selection<T>{

  private final Rectangle rectangle;

  public RectangleSimpleSelection(Rectangle rectangle) {
    this.rectangle = rectangle;
  }

  @Override
  public Collection<T> selectFrom(Collection<T> from) {
    return from.stream().filter(
      entity -> rectangle.contains(entity.getX(), entity.getY())
    ).collect(Collectors.toList());
  }

}
