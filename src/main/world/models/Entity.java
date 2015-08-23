package world.models;

import com.badlogic.gdx.math.Vector2;

public class Entity {

  public final Vector2 position = new Vector2();
  public float angle;
  public float size;
  public Group parent; // todo tak nie moze byc; ref. jest dwustronna (ent <-> group), a ma byc drzewo

  public Entity(float x, float y, float angle, float size) {
    position.set(x, y);
    this.angle = angle;
    this.size = size;
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

}
