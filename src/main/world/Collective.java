package world;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Collective {

  public final Queue<Order> orders = new ArrayDeque<>();
  public final Set<Group> groups = new HashSet<>();

}
