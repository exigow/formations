package logic.selection;

import com.badlogic.gdx.math.Rectangle;
import world.attributes.Coordinate;
import world.helpers.CoordinatesToRectangleConverter;
import world.models.CoordinateSimple;
import world.models.Entity;
import world.models.Group;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Selector {

  private final Coordinate pinPoint = new CoordinateSimple();
  private final Rectangle rectangle = new Rectangle();

  public void start(Coordinate where) {
    pinPoint.setPosition(where);
  }

  public Collection<Group> update(Coordinate to, Set<Entity> entities) {
    Rectangle fixed = CoordinatesToRectangleConverter.convert(pinPoint, to);
    rectangle.set(fixed);
    Collection<Entity> insides = new RectangleSimpleSelection(fixed).selectFrom(entities);
    return collectGroups(insides);
  }

  private static Collection<Group> collectGroups(Collection<Entity> entities) {
    return entities.stream()
      .map(inside -> inside.parent)
      .collect(Collectors.toSet());
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

}
