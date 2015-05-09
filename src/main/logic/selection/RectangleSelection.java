package logic.selection;

import attributes.Coordinate;
import attributes.Radius;
import com.badlogic.gdx.math.Rectangle;

import java.util.Set;
import java.util.stream.Collectors;

public class RectangleSelection<T extends Coordinate & Radius> implements Selection<T>{

  private final Rectangle rectangle;

  public RectangleSelection(Rectangle rectangle) {
    this.rectangle = rectangle;
  }

  @Override
  public Set<T> selectFrom(Set<T> from) {
    return from.stream()
      .filter(this::isInside)
      .collect(Collectors.toSet());
  }

  private boolean isInside(T entity) {
    float dist = coordinateToRectangleDistance(entity, rectangle);
    return dist < entity.getRadius();
  }

  private static float coordinateToRectangleDistance(Coordinate point, Rectangle rectangle) {
    return coordinateToRectangleDistance(point.getX(), point.getY(), rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height); // todo refactor to Rectagle (bo chuj, nie podoba mi się ten zapis "rectangle.x + rectangle.width")
  }

  private static float coordinateToRectangleDistance(float px, float py, float ex, float ey, float sx, float sy) {
    float cx = Math.max(Math.min(px, sx), ex);
    float cy = Math.max(Math.min(py, sy), ey);
    return (float) Math.sqrt((px - cx) * (px - cx) + (py - cy) * (py - cy));
  }

}
