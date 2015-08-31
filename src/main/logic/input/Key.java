package logic.input;

import com.badlogic.gdx.Input;

public enum Key {

  MOUSE_LEFT(Input.Buttons.LEFT),
  MOUSE_RIGHT(Input.Buttons.RIGHT),
  MOUSE_SCROLL(0),
  KEY_W(Input.Keys.W),
  KEY_S(Input.Keys.S),
  KEY_A(Input.Keys.A),
  KEY_D(Input.Keys.D),
  KEY_E(Input.Keys.E),
  KEY_Q(Input.Keys.Q),
  SPACE(Input.Keys.SPACE),
  SHIFT_LEFT(Input.Keys.SHIFT_LEFT);

  public final int gdxKey;

  Key(int gdxKey) {
    this.gdxKey = gdxKey;
  }

}
