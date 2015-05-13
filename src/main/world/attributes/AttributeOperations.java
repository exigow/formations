package world.attributes;

import world.attributes.Angle;
import world.attributes.Coordinate;
import world.attributes.Radius;

import static com.badlogic.gdx.math.MathUtils.atan2;
import static com.badlogic.gdx.math.MathUtils.radiansToDegrees;

public class AttributeOperations {

  public static float lengthBetween(Coordinate first, Coordinate second) {
    float deltaX = second.getX() - first.getX();
    float deltaY = second.getY() - first.getY();
    return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }

  public static float angleBetween(Angle first, Angle second) {
    float difference = first.getAngle() - second.getAngle();
    return (((difference % Angle.MAX) + 540f) % Angle.MAX) - 180f;
  }

  public static float directionBetween(Coordinate first, Coordinate second) {
    float deltaY = first.getY() - second.getY();
    float deltaX = first.getX() - second.getX();
    return atan2(deltaY, deltaX) * radiansToDegrees;
  }

  public static <Mix extends Coordinate & Radius> boolean collisionBetween(Mix first, Mix second){
    float length = lengthBetween(first, second);
    float sizeSum = first.getRadius() + second.getRadius();
    float result = length - sizeSum;
    return result <= 0;
  }

}
