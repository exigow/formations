package logic.input;

import logic.input.states.Tick;

import static logic.input.states.Tick.*;

public class TickListener {

  private Tick actual = ON_WAIT;

  public void listen(boolean triggered) {
    if (triggered) {
      perform(ON_PRESS);
      perform(ON_WAIT);
    } else {
      perform(ON_RELEASE);
      perform(ON_HOLD);
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
      case ON_WAIT:
        return ON_PRESS;
      case ON_PRESS:
        return ON_HOLD;
      case ON_HOLD:
        return ON_RELEASE;
      case ON_RELEASE:
        return ON_WAIT;
    }
    return null;
  }

}
