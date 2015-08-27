package logic.input.wrappers;

import logic.input.Key;
import logic.input.State;
import logic.input.actions.Action;

public class MouseWrapper extends Wrapper {

  public MouseWrapper(Key key, Action action) {
    super(key, action);
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return executeWithStateIfSame(button, State.UP);
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return executeWithStateIfSame(button, State.DOWN);
  }

}