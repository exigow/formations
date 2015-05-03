package models.helpers;

import attributes.Angle;
import com.badlogic.gdx.math.MathUtils;
import models.Entity;

public class RandomEntityGenerator {

  public static Entity generate() {
    float scale = 256f;
    float x = rand() * scale;
    float y = rand() * scale;
    float angle = MathUtils.random(Angle.MAX);
    float size = MathUtils.random(4f, 8f);
    return new Entity(x, y, angle, size);
  }

  private static float rand() {
    return (-1f + MathUtils.random() * 2f);
  }

}
