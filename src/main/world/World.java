package world;

import com.badlogic.gdx.math.MathUtils;
import world.attributes.Angle;
import world.attributes.AttributeRandom;
import world.attributes.Coordinate;
import world.attributes.Radius;
import world.models.CoordinateSimple;
import world.models.Entity;
import world.models.Group;

import java.util.HashSet;
import java.util.Set;

public class World {

  public final Set<Group<Entity>> entityGroups = new HashSet<>();
  public final Set<Entity> entities = new HashSet<>();
  {
    for (int g = 0; g < 16; g++) {
      //Group<Entity> group = new Group<>();
      Coordinate setupCoord = randomCoordinate(512f);
      for (int i = 0; i < 5; i++) {
        Entity entity = new Entity();
        entity.set(randomCoordinate(64f));
        entity.add(setupCoord.getX(), setupCoord.getY());
        entity.setAngle(MathUtils.random(Angle.MAX));
        entity.setRadius(MathUtils.random(4f, 8f));
        entities.add(entity);
      }
    }
  }

  private static Coordinate randomCoordinate(float scale) {
    Coordinate groupPosition = new CoordinateSimple();
    AttributeRandom.randomize(groupPosition);
    groupPosition.scale(scale);
    return groupPosition;
  }

}
