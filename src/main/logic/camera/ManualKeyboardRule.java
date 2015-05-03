package logic.camera;

import agents.InputAgent;
import mappings.Trigger;

public class ManualKeyboardRule implements MovementRule {

  @Override
  public Product specify(InputAgent agent) {
    boolean up = agent.isKeyboardKeyPressed(Trigger.W);
    boolean down = agent.isKeyboardKeyPressed(Trigger.S);
    boolean left = agent.isKeyboardKeyPressed(Trigger.A);
    boolean right = agent.isKeyboardKeyPressed(Trigger.D);
    int x = specifyDimension(right, left);
    int y = specifyDimension(up, down);
    return new Product(x, y);
  }

  private static int specifyDimension(boolean positive, boolean negative) {
    if (positive)
      return POSITIVE;
    if (negative)
      return NEGATIVE;
    return NEUTRAL;
  }

}
