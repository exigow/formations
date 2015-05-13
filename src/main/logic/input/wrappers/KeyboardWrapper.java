package logic.input.wrappers;

import logic.input.Key;
import logic.input.State;
import logic.input.actions.Action;

public class KeyboardWrapper extends Wrapper {

  public KeyboardWrapper(Key key, Action action) {
    super(key, action);
  }

  @Override
  public boolean keyDown(int key) {
    return executeWithStateIfSame(key, State.PRESSED);
  }

  @Override
  public boolean keyUp(int key) {
    return executeWithStateIfSame(key, State.RELEASED);
  }

}