package logic.camera.rules;

import agents.InputAgent;
import logic.input.Key;
import logic.input.actions.TickAction;

public class ManualKeyboardRule implements MovementRule {

  private final TickAction upAction;
  private final TickAction downAction;
  private final TickAction leftAction;
  private final TickAction rightAction;
  private final TickAction closeAction;
  private final TickAction farAction;

  public ManualKeyboardRule(InputAgent agent) {
    upAction = registerTick(agent, Key.KEY_W);
    downAction = registerTick(agent, Key.KEY_S);
    leftAction = registerTick(agent, Key.KEY_A);
    rightAction = registerTick(agent, Key.KEY_D);
    closeAction = registerTick(agent, Key.KEY_E);
    farAction = registerTick(agent, Key.KEY_Q);
  }

  private static TickAction registerTick(InputAgent agent, Key key) {
    return (TickAction) agent.register(key, new TickAction());
  }

  @Override
  public Product specify(InputAgent agent) {
    int x = specifyDimension(rightAction.isPressed(), leftAction.isPressed());
    int y = specifyDimension(upAction.isPressed(), downAction.isPressed());
    int z = -specifyDimension(closeAction.isPressed(), farAction.isPressed());
    return new Product(x, y, z);
  }

  private static int specifyDimension(boolean positive, boolean negative) {
    if (positive)
      return POSITIVE;
    if (negative)
      return NEGATIVE;
    return NEUTRAL;
  }

}
