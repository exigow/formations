package world.models;

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

}
