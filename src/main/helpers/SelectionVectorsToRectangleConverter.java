package helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SelectionVectorsToRectangleConverter {

  public static Rectangle convert(Vector2 pinPoint, Vector2 pointer) {
    float width = pointer.x - pinPoint.x;
    float height = pointer.y - pinPoint.y;
    Rectangle rect = new Rectangle(pinPoint.x, pinPoint.y, width, height);
    return fix(rect);
  }

  private static Rectangle fix(Rectangle source) {
    Rectangle fixed = new Rectangle(source);
    if (fixed.width < 0) {
      fixed.width = Math.abs(fixed.width);
      fixed.x = fixed.getX() - fixed.width;
    }
    if (fixed.height < 0) {
      fixed.height = Math.abs(fixed.height);
      fixed.y = fixed.getY() - fixed.height;
    }
    return fixed;
  }

}
