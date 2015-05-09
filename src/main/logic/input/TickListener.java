package logic.input;

import logic.input.states.Tick;

import static logic.input.states.Tick.*;

public class TickListener {

  private Tick actual = WAIT;

  public void listen(boolean triggered) {
    if (triggered) {
      perform(PRESS);
      perform(WAIT);
    } else {
      perform(RELEASE);
      perform(HOLD);
    }
  }

  public Tick state() {
    return actual;
  }

  private void changeTo(Tick tick) {
    actual = tick;
  }

  private void perform(Tick tick) {
    if (actual == tick)
        changeTo(nextFor(tick));
  }

  private static Tick nextFor(Tick tick) {
    switch (tick) {
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
