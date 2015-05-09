package logic.input;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public enum Trigger {

  MOUSE_LEFT(Buttons.LEFT, InputType.MOUSE),
  MOUSE_RIGHT(Buttons.RIGHT, InputType.MOUSE),
  KEY_W(Keys.W, InputType.KEYBOARD),
  KEY_S(Keys.S, InputType.KEYBOARD),
  KEY_A(Keys.A, InputType.KEYBOARD),
  KEY_D(Keys.D, InputType.KEYBOARD);

  public final int gdxKey;
  public final InputType type;

  Trigger(int gdxKey, InputType type) {
    this.gdxKey = gdxKey;
    this.type = type;
  }

  public enum InputType {

    KEYBOARD, MOUSE

  }

}
