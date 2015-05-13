package world.attributes;

import com.badlogic.gdx.math.MathUtils;

// todo do wyjebania ta klasa
public class AttributeRandom {

  public static void randomize(Coordinate coordinate) {
    float x = randomNormal();
    float y = randomNormal();
    coordinate.setPosition(x, y);
  }

  private static float randomNormal() {
    return (-1f + MathUtils.random() * 2f);
  }

}
