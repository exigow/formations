package logic.input.actions;

import logic.input.State;

public class TickAction implements Action {

  private Boolean tick = false;

  @Override
  public void execute(State state) {
    tick = stateOf(state);
  }

  private static boolean stateOf(State state) {
    switch (state) {
      case UP:
        return true;
      case DOWN:
        return false;
    }
    return false;
  }

  public boolean isPressed() {
    return tick;
  }

}
