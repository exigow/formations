package logic.camera.rules;

import agents.InputAgent;
import logic.input.Trigger;

public class ManualKeyboardRule implements MovementRule {

  @Override
  public Product specify(InputAgent agent) {
    boolean up = Trigger.W.state().isPressed();
    boolean down = Trigger.S.state().isPressed();
    boolean left = Trigger.A.state().isPressed();
    boolean right = Trigger.D.state().isPressed();
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
