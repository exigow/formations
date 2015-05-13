package logic.camera.rules;

import agents.InputAgent;
import logic.input.Key;
import logic.input.actions.TickAction;

public class ManualKeyboardRule implements MovementRule {

  private final TickAction upAction;
  private final TickAction downAction;
  private final TickAction leftAction;
  private final TickAction rightAction;

  public ManualKeyboardRule(InputAgent agent) {
    upAction = registerTick(agent, Key.KEY_W);
    downAction = registerTick(agent, Key.KEY_S);
    leftAction = registerTick(agent, Key.KEY_A);
    rightAction = registerTick(agent, Key.KEY_D);
  }

  private static TickAction registerTick(InputAgent agent, Key key) {
    return (TickAction) agent.register(key, new TickAction());
  }

  @Override
  public Product specify(InputAgent agent) {
    int x = specifyDimension(rightAction.isPressed(), leftAction.isPressed());
    int y = specifyDimension(upAction.isPressed(), downAction.isPressed());
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
