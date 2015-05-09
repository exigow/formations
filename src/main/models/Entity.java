package models;

import agents.RenderAgent;
import attributes.Angle;
import attributes.Coordinate;
import attributes.Radius;

import static com.badlogic.gdx.math.MathUtils.*;

public class Entity implements Coordinate, Angle, Radius {

  private float x;
  private float y;
  private float angle;
  private float size;

  public Entity(float x, float y, float angle, float size) {
    set(x, y);
    setAngle(angle);
    setRadius(size);
  }

  public Entity(float x, float y, float angle) {
    this(x, y, angle, 1f);
  }

  public Entity(float x, float y) {
    this(x, y, 1f);
  }

  public Entity() {
    this(0f, 0f);
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
  public float angle() {
    return angle;
  }

  @Override
  public void setAngle(float angle) {
    this.angle = angle;
  }

  @Override
  public float getRadius() {
    return size;
  }

  @Override
  public void setRadius(float size) {
    this.size = size;
  }

}
