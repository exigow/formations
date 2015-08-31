package helpers;

import com.badlogic.gdx.math.Vector2;
import world.Collective;
import world.Group;
import world.Ship;
import world.World;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.Vector2Utilities.randomVector2;

public class WorldDebugInitializer {

  public static World init() {
    World world = new World();
    for (int g = 0; g < 3; g++) {
      Group group = spawnGroupWithShips(g + 1);
      world.collectives.add(Collective.of(group));
    }
    return world;
  }

  private static Group spawnGroupWithShips(float modifier) {
    Group group = new Group();
    Vector2 pivot = randomVector2(512);
    int count = random(3, 7);
    for (int i = 0; i < count; i++) {
      Ship ship = spawnShip(pivot, modifier);
      group.ships.add(ship);
    }
    return group;
  }

  private static Ship spawnShip(Vector2 groupPosition, float modifier) {
    Ship ship = new Ship();
    ship.place.position.set(randomVector2(128));
    ship.place.position.add(groupPosition);
    ship.place.direction = random(360);
    ship.size = 4 + 8 * (1 / modifier);
    ship.maximumAvailableSpeed = modifier;
    ship.destination.set(ship.place);
    return ship;
  }

}
