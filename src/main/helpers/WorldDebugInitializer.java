package helpers;

import com.badlogic.gdx.math.Vector2;
import world.Collective;
import world.Entity;
import world.Group;
import world.World;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.Vector2Utilities.randomVector2;

public class WorldDebugInitializer {

  public static World init() {
    World world = new World();
    for (int g = 0; g < 5; g++) {
      Group group = spawnGroupWithEntities();
      world.collectives.add(Collective.of(group));
    }

    ///Collective collective = new Collective();

    world.collectives.add(Collective.of(spawnGroupWithEntities(), spawnGroupWithEntities()));

    return world;
  }

  private static Group spawnGroupWithEntities() {
    Group group = new Group();
    Vector2 pivot = randomVector2(512);
    int count = random(7, 11);
    for (int i = 0; i < count; i++) {
      Entity entity = spawnEntity(pivot);
      group.entities.add(entity);
    }
    return group;
  }

  private static Entity spawnEntity(Vector2 groupPosition) {
    Entity entity = new Entity();
    entity.position.set(randomVector2(128));
    entity.position.add(groupPosition);
    entity.angle = random(360);
    entity.size = random(2, 4);
    return entity;
  }

}
