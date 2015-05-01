package models;

import attributes.AngleAttribute;
import attributes.PositionAttribute;
import attributes.SizeAttribute;

public class Entity implements PositionAttribute, AngleAttribute, SizeAttribute {

  private final static float DEFAULT_X = 0f;
  private final static float DEFAULT_Y = 0f;
  private final static float DEFAULT_ANGLE = 0f;
  private final static float DEFAULT_SIZE = 1f;

  private float x;
  private float y;
  private float angle;
  private float size;

  public Entity(float x, float y, float angle, float size) {
    setPosition(x, y);
    setAngle(angle);
    setSize(size);
  }

  public Entity(float x, float y, float angle) {
    this(x, y, angle, DEFAULT_SIZE);
  }

  public Entity(float x, float y) {
    this(x, y, DEFAULT_ANGLE);
  }

  public Entity() {
    this(DEFAULT_X, DEFAULT_Y);
  }

  @Override
  public float getX() {
    return x;
  }

  @Override
  public void setX(float x) {
    this.x = x;
  }

  @Override
  public float getY() {
    return y;
  }

  @Override
  public void setY(float y) {
    this.y = y;
  }

  @Override
  public void setPosition(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public float getAngle() {
    return angle;
  }

  @Override
  public void setAngle(float angle) {
    this.angle = angle;
  }

  @Override
  public float getSize() {
    return size;
  }

  @Override
  public void setSize(float size) {
    this.size = size;
  }

}
