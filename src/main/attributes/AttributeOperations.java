package attributes;

public class AttributeOperations {

  public static float lengthBetween(PositionAttribute first, PositionAttribute second) {
    float deltaX = second.getX() - first.getX();
    float deltaY = second.getY() - first.getY();
    return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }

  public static float angleBetween(AngleAttribute first, AngleAttribute second) {
    return 0f; // todo
  }

  public static <Mix extends PositionAttribute & SizeAttribute> boolean collisionBetween(Mix first, Mix second){
    float length = lengthBetween(first, second);
    float sizeSum = first.getSize() + second.getSize();
    float result = length - sizeSum;
    return result <= 0;
  }

}
