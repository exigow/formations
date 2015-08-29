package logic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import logic.input.actions.Action;
import logic.input.wrappers.KeyboardWrapper;
import logic.input.wrappers.MouseScrollWrapper;
import logic.input.wrappers.MouseWrapper;
import logic.input.wrappers.Wrapper;

public class Input {

  private Input() {
  }

  public static Vector2 windowSize() {
    float x = Gdx.graphics.getWidth();
    float y = Gdx.graphics.getHeight();
    Vector2 result = new Vector2();
    result.set(x, y);
    return result;
  }

  public static Vector2 mouseWindow() {
    float x = Gdx.input.getX();
    float y = Gdx.input.getY();
    Vector2 result = new Vector2();
    result.set(x, y);
    return result;
  }

  public static Vector2 mouse(OrthographicCamera camera) {
    return unproject(mouseWindow(), camera);
  }

  private static Vector2 unproject(Vector2 coordinate, OrthographicCamera camera) {
    Vector3 asVector = new Vector3(coordinate.x, coordinate.y, 0);
    Vector3 projected = camera.unproject(asVector);
    Vector2 result = new Vector2();
    result.set(projected.x, projected.y);
    return result;
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

  private static Wrapper instantiateWrapper(Key key, Action action) {
    if (key == Key.MOUSE_SCROLL)
      return new MouseScrollWrapper(key, action);
    if (key == Key.MOUSE_LEFT || key == Key.MOUSE_RIGHT)
      return new MouseWrapper(key, action);
    return new KeyboardWrapper(key, action);
  }


}
