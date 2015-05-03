package input.camera;

import input.InputAgent;

import java.util.Arrays;
import java.util.Collection;

public class MovementRuleResolver implements MovementRule {

  private final Collection<MovementRule> rules = Arrays.asList(
    new ManualKeyboardRule(),
    new ManualMouseRule()
  );

  @Override
  public Product specify(InputAgent agent) {
    int x = 0;
    int y = 0;
    for (MovementRule rule : rules) {
      Product product = rule.specify(agent);
      x += product.horizontal;
      y += product.vertical;
    }
    Product result = new Product(x, y);
    return normalize(result);
  }

  private static Product normalize(Product input) {
    int x = input.horizontal;
    int y = input.vertical;
    if (x > 1)
      x = 1;
    if (x < -1)
      x = -1;
    if (y > 1)
      y = 1;
    if (y < -1)
      y = -1;
    return new Product(x, y);
  }

}
