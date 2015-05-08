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

  default void set(Coordinate other) {
    set(other.getX(), other.getY());
  }

  default void add(float x, float y) {
    set(getX() + x, getY() + y);
  }

}
