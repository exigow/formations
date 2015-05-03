package models;

import attributes.Angle;
import attributes.Coordinate;
import attributes.Radius;
import com.badlogic.gdx.math.MathUtils;
import rendering.RenderAgent;
import rendering.Renderable;

public class Entity implements Coordinate, Angle, Radius, Renderable {

  private final static float DEFAULT_X = 0f;
  private final static float DEFAULT_Y = 0f;
  private final static float DEFAULT_ANGLE = 0f;
  private final static float DEFAULT_SIZE = 1f;
  private float x;
  private float y;
  private float angle;
  private float size;

  public Entity(float x, float y, float angle, float size) {
    set(x, y);
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

  @Override
  public void render(RenderAgent agent) {
    agent.shape.circle(x, y, size);
    float toX = x + MathUtils.cos(angle * MathUtils.degreesToRadians) * size;
    float toY = y + MathUtils.sin(angle * MathUtils.degreesToRadians) * size;
    agent.shape.line(x, y, toX, toY);
  }

  public static Entity random() {
    float scale = 256f;
    float x = (-1f + MathUtils.random() * 2f) * scale;
    float y = (-1f + MathUtils.random() * 2f) * scale;
    float angle = MathUtils.random(Angle.MAX);
    float size = MathUtils.random(4f, 8f);
    return new Entity(x, y, angle, size);
  }

  private static float rand() {
    return (float) Math.random() * 2;
  }

}
