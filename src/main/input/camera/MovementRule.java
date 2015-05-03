package input.camera;

import input.InputAgent;

public interface MovementRule {

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
