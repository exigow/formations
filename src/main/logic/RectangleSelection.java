package logic;

import attributes.Coordinate;
import attributes.Radius;
import com.badlogic.gdx.math.Rectangle;

import java.util.Collection;
import java.util.stream.Collectors;

public class RectangleSelection<T extends Coordinate & Radius> {

  public Collection<T> selection(Collection<T> from, Rectangle rectangle) {
    return from.stream().filter(
      entity -> isInside(entity, rectangle)
    ).collect(Collectors.toList());
  }

  private boolean isInside(T entity, Rectangle rectangle) {
    float dist = coordinateToRectangleDistance(entity, rectangle);
    return dist < entity.getSize();
  }

  public static float coordinateToRectangleDistance(Coordinate point, Rectangle rectangle) {
    return coordinateToRectangleDistance(point.x(), point.y(), rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height); // todo refactor to Rectagle (bo chuj, nie podoba mi się ten zapis "rectangle.x + rectangle.width")
  }

  public static float coordinateToRectangleDistance(float px, float py, float ex, float ey, float sx, float sy) {
    float cx = Math.max(Math.min(px, sx), ex);
    float cy = Math.max(Math.min(py, sy), ey);
    return (float) Math.sqrt((px - cx) * (px - cx) + (py - cy) * (py - cy));
  }

}
