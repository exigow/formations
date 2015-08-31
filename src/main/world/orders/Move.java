package world.orders;

import world.Place;

public class Move implements Order {

  public final Place where;

  public Move(Place where) {
    this.where = where;
  }

}
