package input.camera;

import attributes.Coordinate;
import input.InputAgent;

public class ManualMouseRule implements MovementRule {

  private final static int BORDER = 32;

  @Override
  public Product specify(InputAgent agent) {
    Coordinate mouse = agent.getMouseWindow();
    Coordinate size = agent.getWindowSize();
    int horizontal = signFor(mouse.x(), size.x());
    int vertical = -signFor(mouse.y(), size.y());
    return new Product(horizontal, vertical);
  }

  private static int signFor(float position, float maxPosition) {
    if (position < BORDER)
      return NEGATIVE;
    if (position > maxPosition - BORDER)
      return POSITIVE;
    return NEUTRAL;
  }

}
