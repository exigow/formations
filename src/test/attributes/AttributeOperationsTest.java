package attributes;

import models.Entity;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AttributeOperationsTest {

  private final static float DELTA = .0000125f;

  @Test
  public void lengthBetween() throws Exception {
    Entity a = new Entity(0, 0);
    Entity b = new Entity(3, 4);
    float length = AttributeOperations.lengthBetween(a, b);
    assertEquals(length, 5, DELTA);
  }

}