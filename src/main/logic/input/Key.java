package logic.input;

import com.badlogic.gdx.Input;

public enum Key {

  MOUSE_LEFT(Input.Buttons.LEFT),
  MOUSE_RIGHT(Input.Buttons.RIGHT),
  KEY_W(Input.Keys.W),
  KEY_S(Input.Keys.S),
  KEY_A(Input.Keys.A),
  KEY_D(Input.Keys.D);

  public final int gdxKey;

  Key(int gdxKey) {
    this.gdxKey = gdxKey;
  }

}
