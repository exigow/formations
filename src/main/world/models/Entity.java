package world.models;

import com.badlogic.gdx.math.Vector2;
import world.attributes.Angle;
import world.attributes.Radius;

public class Entity implements Angle, Radius {

  public final Vector2 position = new Vector2();
  private float angle;
  private float size;
  public Group parent;

  public Entity(float x, float y, float angle, float size) {
    position.set(x, y);
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
  public float getAngle() {
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
