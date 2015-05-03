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
    int x = normalize(input.horizontal);
    int y = normalize(input.vertical);
    return new Product(x, y);
  }

  private static int normalize(int vector) {
    if (vector > POSITIVE)
      return POSITIVE;
    if (vector < NEGATIVE)
      return NEGATIVE;
    return vector;
  }

}
