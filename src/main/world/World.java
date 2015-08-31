package world;

import java.util.HashSet;
import java.util.Set;

public class World {

  public final Set<Collective> collectives = new HashSet<>();

  public Set<Entity> allEntities() {
    Set<Entity> result = new HashSet<>();
    for (Collective collective : collectives)
      for (Group group : collective.groups)
        result.addAll(group.entities);
    return result;
  }

}
