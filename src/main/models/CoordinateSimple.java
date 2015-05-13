package models;

import world.attributes.Coordinate;

public class CoordinateSimple implements Coordinate {

  private float x;
  private float y;

  @Override
  public float getX() {
    return x;
  }

  @Override
  public float getY() {
    return y;
  }

  @Override
  public void setX(float x) {
    this.x = x;
  }

  @Override
  public void setY(float y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "[" + x + ", " + y + "]";
  }

}
