package helpers;

import com.badlogic.gdx.math.Vector2;


public class Vector2Utilities {

  public static Vector2 randomCoordinate(float scale) {
    Vector2 coordinate = new Vector2();
    randomize(coordinate);
    coordinate.scl(scale);
    return coordinate;
  }

  private static void randomize(Vector2 coordinate) {
    float x = randomNormal();
    float y = randomNormal();
    coordinate.set(x, y);
  }

  private static float randomNormal() {
    return (-1f + com.badlogic.gdx.math.MathUtils.random() * 2f);
  }


}
