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
      // todo reduce if needed (2 -> 1, or normalize)
    }
    return new Product(x, y);
  }

  private static Product reduce(Product input) {
    return null; // todo
  }

}
