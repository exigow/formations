package world.models;

import java.util.HashSet;
import java.util.Set;

public class Group {

  // todo zamienic na model bez metod (jako Squad, czy cu)

  public final Set<Entity> entities = new HashSet<>();

  public void join(Entity entity) {
    entities.add(entity);
  }

}
