package world;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class World {

  public final Set<Collective> collectives = new HashSet<>();

  public Set<Entity> allEntities() {
    Set<Entity> result = new HashSet<>();
    for (Collective collective : collectives)
      for (Group group : collective.groups)
        result.addAll(group.entities);
    return result;
  }

  public Set<Group> allGroups() {
    Set<Group> result = new HashSet<>();
    for (Collective collective : collectives)
      result.addAll(collective.groups.stream().collect(Collectors.toList()));
    return result;
  }

}
