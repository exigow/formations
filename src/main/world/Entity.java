package world;

import com.badlogic.gdx.math.Vector2;

import static helpers.MathUtilities.*;

// todo rename Ship
public class Entity {

  public final Vector2 position = new Vector2();
  public float direction;
  public float size;
  public float speed;
  public float maximumAvailableSpeed = 1f;

  public void moveTo(Vector2 designation, float designationAngle) {
    float acceleration = .0125f;
    float directionToDesignation = pointDirection(designation, position);
    float distanceToDesignation = pointDistance(designation, position);
    float flySpeedTarget = Math.min(distanceToDesignation * .025f, maximumAvailableSpeed);
    speed += (flySpeedTarget - speed) * acceleration;
    direction += angdiff(directionToDesignation, direction) * .0125f;
    float fix = (float)Math.pow(1 - Math.min(pointDistance(position, designation), 128f) / 128f, 16f);
    direction -= angdiff(direction, designationAngle) * fix;
    float vx = lerp(ldx(speed, direction), (designation.x - position.x) * .125f, fix);
    float vy = lerp(ldy(speed, direction), (designation.y - position.y) * .125f, fix);
    position.add(vx, vy);
  }

}
