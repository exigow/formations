package attributes;

public interface Coordinate {

  float x();

  float y();

  void setX(float x);

  void setY(float y);

  default Coordinate set(float x, float y) {
    setX(x);
    setY(y);
    return this;
  }

  default Coordinate add(float x, float y) {
    return set(x() + x, y() + y);
  }

}
