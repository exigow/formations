package logic.input.states;

public enum  State {

  WAIT(false), PRESS(true), HOLD(true), RELEASE(true);


  private final boolean pressed;

  State(boolean pressed) {
    this.pressed = pressed;
  }

  public boolean isPressed() {
    return pressed;
  }

}
