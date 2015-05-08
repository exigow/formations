package logic.selection;

import com.badlogic.gdx.math.Rectangle;
import models.Entity;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class RectangleSelectionTest {

  private final static Entity ENTITY_INSIDE = new Entity(2, 2, 0, 0);
  private final static Entity ENTITY_OUTSIDE = new Entity(0, 0, 0, 0);
  private final static Entity ENTITY_COLLIDED_RADIUS = new Entity(5, 2, 0, 2);
  private final static Collection<Entity> entities = Arrays.asList(ENTITY_INSIDE, ENTITY_OUTSIDE, ENTITY_COLLIDED_RADIUS);

  @Test
  public void testFilter() throws Exception {
    Rectangle rect = new Rectangle(1, 1, 4, 4);
    Collection<Entity> filtered = new RectangleSelection<Entity>(rect).selectFrom(entities);
    assertTrue(filtered.contains(ENTITY_INSIDE));
    assertFalse(filtered.contains(ENTITY_OUTSIDE));
    assertTrue(filtered.contains(ENTITY_COLLIDED_RADIUS));
  }

}