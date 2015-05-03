package logic.camera;

import attributes.Coordinate;
import agents.InputAgent;

public class ManualMouseRule implements MovementRule {

  private final static int BORDER_SIZE = 32;

  @Override
  public Product specify(InputAgent agent) {
    Coordinate mouse = agent.getMouseWindow();
    Coordinate size = agent.getWindowSize();
    int horizontal = signFor(mouse.x(), size.x());
    int vertical = -signFor(mouse.y(), size.y());
    return new Product(horizontal, vertical);
  }

  private static int signFor(float position, float maxPosition) {
    if (position < BORDER_SIZE)
      return NEGATIVE;
    if (position > maxPosition - BORDER_SIZE)
      return POSITIVE;
    return NEUTRAL;
  }

}
