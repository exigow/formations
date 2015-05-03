package logic.camera;

import agents.InputAgent;

public interface MovementRule {

  int POSITIVE = 1;
  int NEGATIVE = -1;
  int NEUTRAL = 0;

  Product specify(InputAgent agent);

  class Product {

    public final int horizontal;
    public final int vertical;

    public Product(int horizontal, int vertical) {
      this.horizontal = horizontal;
      this.vertical = vertical;
    }

  }

}
