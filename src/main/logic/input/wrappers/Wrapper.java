package logic.input.wrappers;

import com.badlogic.gdx.InputAdapter;
import logging.Logger;
import logic.input.Key;
import logic.input.State;
import logic.input.actions.Action;

public class Wrapper extends InputAdapter {

  private final Key key;
  private final Action action;

  public Wrapper(Key key, Action action) {
    this.key = key;
    this.action = action;
  }

  protected boolean executeWithStateIfSame(int keyValue, State state) {
    if (key.gdxKey == keyValue) {
      Logger.INPUT.info(key + " is " + state);
      action.execute(state);
      return true;
    }
    return false;
  }

}
