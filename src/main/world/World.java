package world;

import com.badlogic.gdx.math.Vector2;
import world.orders.MoveOrder;

import java.util.HashSet;
import java.util.Set;

public class World {

  public final Set<Collective> collectives = new HashSet<>();

  public Collective instantiateCollective(Set<Squad> squads) {
    Collective result = Collective.empty();
    for (Squad squad : squads) {
      Collective stolen = collectiveOf(squad);
      stolen.squads.remove(squad);
      result.squads.add(squad);
    }
    collectives.add(result);
    return result;
  }

  private Collective collectiveOf(Squad squad) {
    for (Collective collective : collectives)
      if (collective.squads.contains(squad))
        return collective;
    throw new RuntimeException("Group must be in the collective");
  }

  public void update() {
    for (Collective collective : collectives) {
      if (collective.orders.isEmpty())
        continue;
      MoveOrder order = collective.orders.get(0);
      for (Squad squad : collective.squads) {
        for (Ship ship : squad.ships) {
          Vector2 asd = new Vector2(order.where.position);
          ship.destination.position.set(asd);
        }
      }
    }
    allShips().forEach(world.Ship::move);
  }

  public Set<Ship> allShips() {
    Set<Ship> result = new HashSet<>();
    for (Collective collective : collectives)
      for (Squad squad : collective.squads)
        result.addAll(squad.ships);
    return result;
  }

}
