package logic.input;

import agents.InputAgent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import logic.input.states.State;

public enum Trigger {

  MOUSE_LEFT(Buttons.LEFT, InputType.MOUSE),
  MOUSE_RIGHT(Buttons.RIGHT, InputType.MOUSE),
  W(Keys.W, InputType.KEYBOARD),
  S(Keys.S, InputType.KEYBOARD),
  A(Keys.A, InputType.KEYBOARD),
  D(Keys.D, InputType.KEYBOARD);

  private final int gdxKey;
  private final StateListener listener = new StateListener();
  private final InputType type;

  Trigger(int gdxKey, InputType type) {
    this.gdxKey = gdxKey;
    this.type = type;
  }

  public State state() {
    return listener.state();
  }

  public static void listenAll() {
    for (Trigger trigger : values()) {
      boolean pressed = isPressed(trigger);
      trigger.listener.listen(pressed);
    }
  }

  private static boolean isPressed(Trigger trigger) {
    if (trigger.type == InputType.KEYBOARD)
      return Gdx.input.isKeyPressed(trigger.gdxKey);
    return Gdx.input.isButtonPressed(trigger.gdxKey);
  }

  private enum InputType {

    KEYBOARD, MOUSE

  }

}
