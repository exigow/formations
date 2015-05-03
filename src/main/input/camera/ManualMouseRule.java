package input.camera;

import attributes.Coordinate;
import input.InputAgent;

public class ManualMouseRule implements MovementRule {

  private final static int BORDER = 32;

  @Override
  public Product specify(InputAgent agent) {
    Coordinate mouse = agent.getMouseWindow();
    Coordinate size = agent.getWindowSize();
    int horizontal = signOf(mouse.getX(), size.getX());
    int vertical = -signOf(mouse.getY(), size.getY());
    return new Product(horizontal, vertical);
  }

  private static int signOf(float position, float maxPosition) {
    if (position < BORDER)
      return -1;
    if (position > maxPosition - BORDER)
      return 1;
    return 0;
  }

}
