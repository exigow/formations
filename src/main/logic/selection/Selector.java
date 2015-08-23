package logic.selection;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import world.helpers.CoordinatesToRectangleConverter;
import world.models.Entity;
import world.models.Group;

import java.util.Collection;
import java.util.stream.Collectors;

public class Selector {

  private final Vector2 pinPoint = new Vector2();
  private final Rectangle rectangle = new Rectangle();

  public void start(Vector2 where) {
    pinPoint.set(where);
  }

  public Collection<Group> update(Vector2 to, Collection<Entity> entities) {
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
