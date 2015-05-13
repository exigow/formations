package agents;

import agents.helpers.ActionRegistrar;
import world.attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import logic.camera.Camera;
import logic.input.Key;
import logic.input.actions.Action;
import world.models.CoordinateSimple;

public class InputAgent {

  private final ActionRegistrar registrar = new ActionRegistrar();

  public Coordinate windowSize() {
    float x = Gdx.graphics.getWidth();
    float y = Gdx.graphics.getHeight();
    Coordinate result = new CoordinateSimple();
    result.setPosition(x, y);
    return result;
  }

  public Coordinate mouseWindow() {
    float x = Gdx.input.getX();
    float y = Gdx.input.getY();
    Coordinate result = new CoordinateSimple();
    result.setPosition(x, y);
    return result;
  }

  public Coordinate mouse(Camera camera) {
    return unproject(mouseWindow(), camera);
  }

  public Action register(Key key, Action action) {
    return registrar.register(key, action);
  }

  @SuppressWarnings("deprecation")
  private Coordinate unproject(Coordinate coordinate, Camera camera) {
    Vector3 asVector = new Vector3(coordinate.getX(), coordinate.getY(), 0f);
    Vector3 projected = camera.getOrthographicCamera().unproject(asVector);
    Coordinate result = new CoordinateSimple();
    result.setPosition(projected.x, projected.y);
    return result;
  }

}
