package world;

import com.badlogic.gdx.math.Vector2;
import world.models.Entity;
import world.models.Group;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.Vector2Utilities.randomVector2;

public class World {

  public final Set<Group> groups = new HashSet<>();

  {
    for (int g = 0; g < 16; g++) {
      Group group = spawnGroupWithEntities();
      groups.add(group);
    }
  }

  private static Group spawnGroupWithEntities() {
    Group group = new Group();
    Vector2 pivot = randomVector2(512);
    for (int i = 0; i < 5; i++) {
      Entity entity = spawnEntity(pivot);
      group.entities.add(entity);
    }
    return group;
  }

  private static Entity spawnEntity(Vector2 groupPosition) {
    Entity entity = new Entity();
    entity.position.set(randomVector2(64));
    entity.position.add(groupPosition);
    entity.angle = random(360);
    entity.size = random(4, 8);
    return entity;
  }

}
