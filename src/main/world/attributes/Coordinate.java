package world.attributes;

public interface Coordinate {

  float getX();

  float getY();

  void setX(float x);

  void setY(float y);

  default void setPosition(float x, float y) {
    setX(x);
    setY(y);
  }

  default void setPosition(Coordinate other) {
    setPosition(other.getX(), other.getY());
  }

  default void addPosition(float x, float y) {
    setPosition(getX() + x, getY() + y);
  }

  default void addPosition(Coordinate other) {
    addPosition(other.getX(), other.getY());
  }

  default void scalePosition(float factor) {
    setPosition(getX() * factor, getY() * factor);
  }

}
