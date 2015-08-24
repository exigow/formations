package world;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import world.models.Entity;
import world.models.Group;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

  public Collection<Entity> allEntities() {
    return groups.stream()
      .map(g -> g.entities)
      .flatMap(Collection::stream)
      .collect(Collectors.toSet());
  }

  private static Vector2 randomCoordinate(float scale) {
    Vector2 coordinate = new Vector2();
    randomize(coordinate);
    coordinate.scl(scale);
    return coordinate;
  }

  private static void randomize(Vector2 coordinate) {
    float x = randomNormal();
    float y = randomNormal();
    coordinate.set(x, y);
  }

  private static float randomNormal() {
    return (-1f + MathUtils.random() * 2f);
  }

}
