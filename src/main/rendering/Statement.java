package rendering;

import rendering.statements.CircleStatement;

public class Statement {

  public static CircleStatement circle(Vec2 position, float radius) {
    return new CircleStatement(position, radius);
  }

}
