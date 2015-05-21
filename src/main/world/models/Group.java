package world.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Group {

  private final Set<Entity> entities = new HashSet<>();

  public void join(Entity entity) {
    assertNotConnected(entity);
    entity.parent = this;
    entities.add(entity);
  }

  public void unjoin(Entity entity) {
    assertNotConnected(entity);
    entity.parent = null;
    entities.remove(entity);
  }

  private void assertNotConnected(Entity entity) {
    if (entities.contains(entity))
      throw new RuntimeException();
  }

  @Deprecated
  public Set<Entity> getEntities() {
    return entities;
  }

  public static Collection<Entity> entitiesOf(Collection<Group> groups) {
    Collection<Entity> result = new ArrayList<>();
    for (Group group : groups)
      result.addAll(group.entities);
    return result;
  }


}
