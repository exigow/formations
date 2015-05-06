package logic.camera;

import agents.InputAgent;
import attributes.Coordinate;
import attributes.CoordinateImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import logic.camera.rules.ManualKeyboardRule;
import logic.camera.rules.ManualMouseRule;
import logic.camera.rules.MovementRule.Product;
import logic.camera.rules.Resolver;

public class Camera {

  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final Coordinate target = new CoordinateImpl();
  private final Coordinate eye = new CoordinateImpl();
  private final Resolver resolver = new Resolver(new ManualKeyboardRule(), new ManualMouseRule());

  @Deprecated
  public OrthographicCamera getOrthographicCamera() {
    return camera;
  }

  public void updateMovementRules(InputAgent agent) {
    Product product = resolver.specify(agent);
    float factor = 4f;
    float x = product.horizontal * factor;
    float y = product.vertical * factor;
    target.set(x, y);
  }

  public void update(float deltaTime) {
    float smooth = deltaTime * 8f;
    float deltaX = (target.getX() - eye.getX()) * smooth;
    float deltaY = (target.getY() - eye.getY()) * smooth;
    camera.translate(eye.getX(), eye.getY());
    camera.update();
    eye.add(deltaX, deltaY);
  }

}
