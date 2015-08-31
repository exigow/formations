package world;

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

}
