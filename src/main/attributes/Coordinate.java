package attributes;

public interface Coordinate {

  float getX();

  float getY();

  void setX(float x);

  void setY(float y);

  default Coordinate set(float x, float y) {
    setX(x);
    setY(y);
    return this;
  }

  default Coordinate add(float x, float y) {
    return set(getX() + x, getY() + y);
  }

}
