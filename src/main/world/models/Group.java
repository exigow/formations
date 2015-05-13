package world.models;

import world.attributes.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Group<T extends Coordinate> {

  public final Set<T> entities = new HashSet<>();

  public Coordinate center() {
    return new CoordinateSimple(); // todo
  }

}
