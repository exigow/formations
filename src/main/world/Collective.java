package world;

import world.orders.MoveOrder;

import java.util.*;

public class Collective {

  private Collective() {
  }

  public final List<MoveOrder> orders = new ArrayList<>();
  public final Set<Squad> squads = new HashSet<>();

  public static Collective empty() {
    return new Collective();
  }

  public static Collective of(Squad... squad) {
    Collective collective = new Collective();
    collective.squads.addAll(Arrays.asList(squad));
    return collective;
  }

}
