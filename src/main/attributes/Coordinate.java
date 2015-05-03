package attributes;

public interface Coordinate {

  float getX();

  float getY();

  void setX(float x);

  void setY(float y);

  default void set(float x, float y) {
    setX(x);
    setY(y);
  }

}
