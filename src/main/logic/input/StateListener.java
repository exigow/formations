package logic.input;

import logic.input.states.State;

import static logic.input.states.State.*;

public class StateListener {

  private State actual = WAIT;

  public void listen(boolean triggered) {
    if (triggered) {
      perform(PRESS);
      perform(WAIT);
    } else {
      perform(RELEASE);
      perform(HOLD);
    }
  }

  public State state() {
    return actual;
  }

  private void changeTo(State state) {
    actual = state;
  }

  private void perform(State state) {
    if (actual == state)
        changeTo(nextFor(state));
  }

  private static State nextFor(State state) {
    switch (state) {
      case WAIT:
        return PRESS;
      case PRESS:
        return HOLD;
      case HOLD:
        return RELEASE;
      case RELEASE:
        return WAIT;
    }
    return null;
  }

}
