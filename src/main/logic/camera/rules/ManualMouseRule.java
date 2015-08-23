package logic.camera.rules;

import agents.InputAgent;
import com.badlogic.gdx.math.Vector2;

public class ManualMouseRule implements MovementRule {

  private final static int BORDER_SIZE = 32;

  @Override
  public Product specify(InputAgent agent) {
    Vector2 mouse = agent.mouseWindow();
    Vector2 size = agent.windowSize();
    int horizontal = signFor(mouse.x, size.x);
    int vertical = -signFor(mouse.y, size.y);
    return new Product(horizontal, vertical, 0);
  }

  private static int signFor(float position, float maxPosition) {
    if (position < BORDER_SIZE)
      return NEGATIVE;
    if (position > maxPosition - BORDER_SIZE)
      return POSITIVE;
    return NEUTRAL;
  }

}
