package logic.selection;

import attributes.Coordinate;
import com.badlogic.gdx.math.Rectangle;
import models.CoordinateSimple;
import models.Entity;
import models.helpers.CoordinatesToRectangleConverter;

import java.util.Collection;
import java.util.Set;

public class Selector {

  private final Coordinate pinPoint = new CoordinateSimple();
  private final Rectangle rectangle = new Rectangle();

  public void start(Coordinate where) {
    pinPoint.set(where);
  }

  public Collection<Entity> update(Coordinate to, Set<Entity> entities) {
    Rectangle fixed = CoordinatesToRectangleConverter.convert(pinPoint, to);
    rectangle.set(fixed);
    return new RectangleSimpleSelection<Entity>(fixed).selectFrom(entities);
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

}
