package agents;

import agents.helpers.ActionRegistrar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import logic.input.Key;
import logic.input.actions.Action;

public class InputAgent {

  private final ActionRegistrar registrar = new ActionRegistrar();

  public Vector2 windowSize() {
    float x = Gdx.graphics.getWidth();
    float y = Gdx.graphics.getHeight();
    Vector2 result = new Vector2();
    result.set(x, y);
    return result;
  }

  public Vector2 mouseWindow() {
    float x = Gdx.input.getX();
    float y = Gdx.input.getY();
    Vector2 result = new Vector2();
    result.set(x, y);
    return result;
  }

  public Vector2 mouse(OrthographicCamera camera) {
    return unproject(mouseWindow(), camera);
  }

  public Action register(Key key, Action action) {
    return registrar.register(key, action);
  }

  @SuppressWarnings("deprecation")
  private Vector2 unproject(Vector2 coordinate, OrthographicCamera camera) {
    Vector3 asVector = new Vector3(coordinate.x, coordinate.y, 0);
    Vector3 projected = camera.unproject(asVector);
    Vector2 result = new Vector2();
    result.set(projected.x, projected.y);
    return result;
  }

}
