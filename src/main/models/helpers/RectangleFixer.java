package models.helpers;

import com.badlogic.gdx.math.Rectangle;

public class RectangleFixer {

  public static Rectangle fix(Rectangle source) {
    Rectangle fixed = new Rectangle(source);
    if (fixed.width < 0) {
      fixed.width = Math.abs(fixed.width);
      fixed.x = fixed.getX() - fixed.width;
    }
    if (fixed.height < 0) {
      fixed.height = Math.abs(fixed.height);
      fixed.x = fixed.getX() - fixed.height;
    }
    return fixed;
  }

}
