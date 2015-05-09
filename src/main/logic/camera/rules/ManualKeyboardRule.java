package logic.camera.rules;

import agents.InputAgent;
import logic.input.Trigger;

public class ManualKeyboardRule implements MovementRule {

  @Override
  public Product specify(InputAgent agent) {
    boolean up = agent.isPressed(Trigger.KEY_W);
    boolean down = agent.isPressed(Trigger.KEY_S);
    boolean left = agent.isPressed(Trigger.KEY_A);
    boolean right = agent.isPressed(Trigger.KEY_D);
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
