package logic.camera.rules;

import world.attributes.Coordinate;
import agents.InputAgent;

public class ManualMouseRule implements MovementRule {

  private final static int BORDER_SIZE = 32;

  @Override
  public Product specify(InputAgent agent) {
    Coordinate mouse = agent.mouseWindow();
    Coordinate size = agent.windowSize();
    int horizontal = signFor(mouse.getX(), size.getX());
    int vertical = -signFor(mouse.getY(), size.getY());
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
