package logic.camera.rules;

import agents.InputAgent;

public interface MovementRule {

  int POSITIVE = 1;
  int NEGATIVE = -1;
  int NEUTRAL = 0;

  Product specify(InputAgent agent);

  class Product {

    public final int horizontal;
    public final int vertical;
    public final int depth;

    public Product(int horizontal, int vertical, int depth) {
      this.horizontal = horizontal;
      this.vertical = vertical;
      this.depth = depth;
    }

  }

}
