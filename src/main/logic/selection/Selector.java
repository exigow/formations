package logic.selection;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import helpers.CoordinatesToRectangleConverter;
import world.Entity;
import world.Group;

import java.util.ArrayList;
import java.util.Collection;

public class Selector {

  private final Vector2 pinPoint = new Vector2();
  private final Rectangle rectangle = new Rectangle();

  public void start(Vector2 where) {
    pinPoint.set(where);
  }

  public Collection<Group> update(Vector2 to, Collection<Group> groups) {
    Rectangle fixed = CoordinatesToRectangleConverter.convert(pinPoint, to);
    rectangle.set(fixed);
    Collection<Group> result = new ArrayList<>();
    for (Group group : groups)
      for (Entity entity : group.entities)
        if (isInside(entity.position))
          result.add(group);
    return result;
  }

  private boolean isInside(Vector2 coordinate) {
    return rectangle.contains(coordinate.x, coordinate.y);
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

}
