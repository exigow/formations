package world;

import com.badlogic.gdx.math.MathUtils;
import world.attributes.Angle;
import world.attributes.AttributeRandom;
import world.attributes.Coordinate;
import world.models.CoordinateSimple;
import world.models.Entity;
import world.models.Group;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class World {

  public final Set<Group<Entity>> groups = new HashSet<>();
  {
    for (int g = 0; g < 16; g++) {
      Group<Entity> group = new Group<>();
      Coordinate setup = randomCoordinate(512f);
      for (int i = 0; i < 5; i++) {
        Entity entity = new Entity();
        entity.setPosition(randomCoordinate(64f));
        entity.addPosition(setup);
        entity.setAngle(MathUtils.random(Angle.MAX));
        entity.setRadius(MathUtils.random(4f, 8f));
        group.entities.add(entity);
      }
      groups.add(group);
    }
  }

  public Set<Entity> allEntities() {
    return groups.stream()
      .map(group -> group.entities)
      .flatMap(Collection::stream)
      .collect(Collectors.toSet());
  }

  private static Coordinate randomCoordinate(float scale) {
    Coordinate coordinate = new CoordinateSimple();
    randomize(coordinate);
    coordinate.scalePosition(scale);
    return coordinate;
  }

  private static void randomize(Coordinate coordinate) {
    float x = randomNormal();
    float y = randomNormal();
    coordinate.setPosition(x, y);
  }

  private static float randomNormal() {
    return (-1f + MathUtils.random() * 2f);
  }


}
