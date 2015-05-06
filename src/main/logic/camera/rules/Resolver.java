package logic.camera.rules;

import agents.InputAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Resolver implements MovementRule {

  private final Collection<MovementRule> rules = new ArrayList<>();

  public Resolver(MovementRule... rules) {
    this.rules.addAll(Arrays.asList(rules));
  }

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
