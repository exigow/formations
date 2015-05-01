package attributes;

import static com.badlogic.gdx.math.MathUtils.atan2;
import static com.badlogic.gdx.math.MathUtils.radiansToDegrees;

public class AttributeOperations {

  private final static float MAX_ANGLE = 360f;

  public static float lengthBetween(PositionAttribute first, PositionAttribute second) {
    float deltaX = second.getX() - first.getX();
    float deltaY = second.getY() - first.getY();
    return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }

  public static float angleBetween(AngleAttribute first, AngleAttribute second) {
    float difference = first.getAngle() - second.getAngle();
    return (((difference % MAX_ANGLE) + 540f) % MAX_ANGLE) - 180f;
  }

  public static float directionBetween(PositionAttribute first, PositionAttribute second) {
    float deltaY = first.getY() - second.getY();
    float deltaX = first.getX() - second.getX();
    return atan2(deltaY, deltaX) * radiansToDegrees;
  }

  public static <Mix extends PositionAttribute & SizeAttribute> boolean collisionBetween(Mix first, Mix second){
    float length = lengthBetween(first, second);
    float sizeSum = first.getSize() + second.getSize();
    float result = length - sizeSum;
    return result <= 0;
  }

}
