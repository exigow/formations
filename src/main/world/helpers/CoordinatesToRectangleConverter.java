package world.helpers;

import world.attributes.Coordinate;
import com.badlogic.gdx.math.Rectangle;

public class CoordinatesToRectangleConverter {

  public static Rectangle convert(Coordinate pinPoint, Coordinate pointer) {
    float width = pointer.getX() - pinPoint.getX();
    float height = pointer.getY() - pinPoint.getY();
    Rectangle rect = new Rectangle(pinPoint.getX(), pinPoint.getY(), width, height);
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
