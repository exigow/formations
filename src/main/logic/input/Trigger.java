package logic.input;

import com.badlogic.gdx.Input;

public enum Trigger {

  MOUSE_LEFT(Input.Buttons.LEFT),
  MOUSE_RIGHT(Input.Buttons.RIGHT),
  W(Input.Keys.W),
  S(Input.Keys.S),
  A(Input.Keys.A),
  D(Input.Keys.D);

  public final int gdxKey;

  Trigger(int gdxKey) {
    this.gdxKey = gdxKey;
  }

}
