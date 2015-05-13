package models.helpers;

import world.attributes.Angle;
import com.badlogic.gdx.math.MathUtils;
import world.Entity;

public class RandomEntityGenerator {

  public static Entity generate() {
    float scale = 512f;
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
