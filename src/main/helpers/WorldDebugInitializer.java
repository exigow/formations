package helpers;

import com.badlogic.gdx.math.Vector2;
import world.World;
import world.models.Entity;
import world.models.Group;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.Vector2Utilities.randomVector2;

public class WorldDebugInitializer {

  public static World init() {
    World world = new World();
    for (int g = 0; g < 32; g++) {
      Group group = spawnGroupWithEntities();
      world.groups.add(group);
    }
    return world;
  }

  private static Group spawnGroupWithEntities() {
    Group group = new Group();
    Vector2 pivot = randomVector2(1024);
    int count = random(5, 11);
    for (int i = 0; i < count; i++) {
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
