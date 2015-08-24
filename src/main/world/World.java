package world;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import world.models.Entity;
import world.models.Group;

import java.util.HashSet;
import java.util.Set;

import static helpers.Vector2Utilities.randomCoordinate;

public class World {

  public final Set<Group> groups = new HashSet<>();

  {
    for (int g = 0; g < 16; g++) {
      Group group = new Group();
      Vector2 setup = randomCoordinate(512);
      for (int i = 0; i < 5; i++) {
        Entity entity = new Entity();
        entity.position.set(randomCoordinate(64));
        entity.position.add(setup);
        entity.angle = MathUtils.random(360);
        entity.size = MathUtils.random(4, 8);
        group.entities.add(entity);
      }
      groups.add(group);
    }
  }

}
