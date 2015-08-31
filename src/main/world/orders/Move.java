package world.orders;

import com.badlogic.gdx.math.Vector2;

public class Move implements Order {

  public final Vector2 where;

  public Move(Vector2 where) {
    this.where = new Vector2(where);
  }

}
