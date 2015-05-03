package attributes;

public interface PositionAttribute {

  float getX();

  float getY();

  void setX(float x);

  void setY(float y);

  default void setPosition(float x, float y) {
    setX(x);
    setY(y);
  }

}
