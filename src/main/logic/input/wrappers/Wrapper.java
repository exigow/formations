package logic.input.wrappers;

import com.badlogic.gdx.InputAdapter;
import logic.input.Action;
import logic.input.Key;
import logic.input.State;

public abstract class Wrapper extends InputAdapter {

  private final Key key;
  protected final Action action;

  public Wrapper(Key key, Action action) {
    this.key = key;
    this.action = action;
  }

  protected boolean executeWithStateIfSame(int keyValue, State state) {
    if (key.gdxKey == keyValue) {
      action.execute(state);
      return true;
    }
    return false;
  }

}
