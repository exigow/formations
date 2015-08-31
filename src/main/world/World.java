package world;

import com.badlogic.gdx.math.Vector2;
import helpers.Vector2Utilities;
import world.orders.MoveOrder;

import java.util.HashSet;
import java.util.Set;

public class World {

  public final Set<Collective> collectives = new HashSet<>();

  public Collective instantiateCollective(Set<Group> groups) {
    Collective result = Collective.empty();
    for (Group group : groups) {
      Collective stolen = collectiveOf(group);
      stolen.groups.remove(group);
      result.groups.add(group);
    }
    collectives.add(result);
    return result;
  }

  private Collective collectiveOf(Group group) {
    for (Collective collective : collectives)
      if (collective.groups.contains(group))
        return collective;
    throw new RuntimeException("Group must be in the collective");
  }

  public void update() {
    for (Collective collective : collectives) {
      if (collective.orders.isEmpty())
        continue;
      MoveOrder order = collective.orders.get(0);
      for (Group group : collective.groups) {
        for (Ship ship : group.ships) {
          Vector2 asd = new Vector2(order.where.position);
          asd.add(Vector2Utilities.randomVector2(32));
          ship.destination.position.set(asd);
        }
      }
    }
    allShips().forEach(world.Ship::update);
  }

  public Set<Ship> allShips() {
    Set<Ship> result = new HashSet<>();
    for (Collective collective : collectives)
      for (Group group : collective.groups)
        result.addAll(group.ships);
    return result;
  }

}
