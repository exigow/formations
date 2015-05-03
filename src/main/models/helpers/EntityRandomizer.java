package models.helpers;

import attributes.Angle;
import com.badlogic.gdx.math.MathUtils;
import models.Entity;

public class EntityRandomizer {

  public static Entity generate() {
    float scale = 256f;
    float x = (-1f + MathUtils.random() * 2f) * scale;
    float y = (-1f + MathUtils.random() * 2f) * scale;
    float angle = MathUtils.random(Angle.MAX);
    float size = MathUtils.random(4f, 8f);
    return new Entity(x, y, angle, size);
  }

  private static float rand() {
    return (float) Math.random() * 2;
  }

}
