package logic.input.states;

public enum Tick {

  ON_WAIT(false),
  ON_PRESS(true),
  ON_HOLD(true),
  ON_RELEASE(true);

  private final boolean pressed;

  Tick(boolean pressed) {
    this.pressed = pressed;
  }

  public boolean isPressed() {
    return pressed;
  }

}
