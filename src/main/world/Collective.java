package world;

import java.util.*;

public class Collective {

  private Collective() {
  }

  public final Queue<Order> orders = new ArrayDeque<>();
  public final Set<Group> groups = new HashSet<>();

  public static Collective of(Group... group) {
    Collective collective = new Collective();
    collective.groups.addAll(Arrays.asList(group));
    return collective;
  }

}
