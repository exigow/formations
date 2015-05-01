package attributes;

import models.Entity;
import org.testng.Assert;
import org.testng.annotations.Test;

import static attributes.AttributeOperations.*;

public class AttributeOperationsTest {

  @Test
  public void testLength() throws Exception {
    Entity a = new Entity(0f, 0f);
    Entity b = new Entity(3f, 4f);
    assertEquals(lengthBetween(a, b), 5f);
    assertEquals(lengthBetween(b, a), 5f);
  }

  @Test
  public void testAngle() throws Exception {
    Entity a = new Entity(0f, 0f, 45f);
    Entity b = new Entity(0f, 0f, -45f);
    assertEquals(angleBetween(a, b), 90f);
    assertEquals(angleBetween(b, a), -90f);
  }

  @Test
  public void testDirection() throws Exception {
    // todo
  }

  @Test
  public void testCollision() throws Exception {
    Entity a = new Entity(1f, 0f, 0f, 1f);
    Entity b = new Entity(4f, 0f, 0f, 3f);
    Entity c = new Entity(4f, 0f, 0f, 1f);
    Assert.assertTrue(collisionBetween(a, b));
    Assert.assertFalse(collisionBetween(a, c));
  }

  private static void assertEquals(float a, float b) {
    float allowableDelta = .0000125f;
    Assert.assertEquals(a, b, allowableDelta);
  }

}