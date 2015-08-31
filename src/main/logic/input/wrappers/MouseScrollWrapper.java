package logic.input.wrappers;

import logic.input.Action;
import logic.input.Key;
import logic.input.State;

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
