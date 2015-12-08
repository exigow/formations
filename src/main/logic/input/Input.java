package logic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import logic.input.wrappers.KeyboardWrapper;
import logic.input.wrappers.MouseScrollWrapper;
import logic.input.wrappers.MouseWrapper;
import logic.input.wrappers.Wrapper;

public class Input {

  private Input() {
  }

  public static Vector2 mousePosition() {
    return new Vector2(Gdx.input.getX(), Gdx.input.getY());
  }


  private final static InputMultiplexer multiplexer = new InputMultiplexer();

  static {
    Gdx.input.setInputProcessor(multiplexer);
  }

  public static Action register(Key key, Action action) {
    Wrapper wrapper = instantiateWrapper(key, action);
    multiplexer.addProcessor(wrapper);
    return action;
  }

  public static boolean triggerKey(State state) {
    switch (state) {
      case UP:
        return true;
      case DOWN:
        return false;
    }
    throw new RuntimeException();
  }

  private static Wrapper instantiateWrapper(Key key, Action action) {
    if (key == Key.MOUSE_SCROLL)
      return new MouseScrollWrapper(key, action);
    if (key == Key.MOUSE_LEFT || key == Key.MOUSE_RIGHT)
      return new MouseWrapper(key, action);
    return new KeyboardWrapper(key, action);
  }


}
