package debug;

import com.badlogic.gdx.math.Vector2;
import rendering.Draw;
import world.Ship;
import world.World;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

public class WorldDebugRenderer {

  public static void renderWorld(World world) {
    for (Ship s : world.allShips()) {
      Draw.drawCircleFilled(s.place.position.x, s.place.position.y, 8);
      Vector2 arrow = computeArrowPosition(s);
      Draw.drawLine(s.place.position.x, s.place.position.y, arrow.x, arrow.y);
    }
  }

  private static Vector2 computeArrowPosition(Ship ship) {
    float dir = ship.place.direction;
    return new Vector2(cosDeg(dir), sinDeg(dir)).scl(32).add(ship.place.position);
  }

}
