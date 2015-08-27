package logic.input.wrappers;

import logic.input.Key;
import logic.input.State;
import logic.input.actions.Action;

public class MouseScrollWrapper extends Wrapper {

  public MouseScrollWrapper(Key key, Action action) {
    super(key, action);
  }

  @Override
  public boolean scrolled(int amount) {
    if (amount > 0) {
      action.execute(State.UP);
      return true;
    }
    if (amount < 0) {
      action.execute(State.DOWN);
      return true;
    }
    return false;
  }

}
