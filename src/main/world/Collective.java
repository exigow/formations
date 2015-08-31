package world;

import world.orders.Order;

import java.util.*;

public class Collective {

  private Collective() {
  }

  public final List<Order> orders = new ArrayList<>();
  public final Set<Group> groups = new HashSet<>();

  public static Collective empty() {
    return new Collective();
  }

  public static Collective of(Group... group) {
    Collective collective = new Collective();
    collective.groups.addAll(Arrays.asList(group));
    return collective;
  }

}
