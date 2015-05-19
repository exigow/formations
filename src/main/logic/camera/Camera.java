package logic.camera;

import agents.InputAgent;
import world.attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import logic.camera.rules.MovementRule.Product;
import logic.camera.rules.Resolver;
import world.models.CoordinateSimple;

public class Camera {

  private final Resolver resolver;

  public Camera(Resolver resolver) {
    this.resolver = resolver;
  }

  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final Coordinate target = new CoordinateSimple();
  private final Coordinate eye = new CoordinateSimple();
  private float targetZ = 1f;
  private float eyeZ = targetZ;

  @Deprecated
  public OrthographicCamera getOrthographicCamera() {
    return camera;
  }

  public void updateMovementRules(InputAgent agent) {
    Product product = resolver.specify(agent);
    float planeFactor = 4f;
    float depthFactor = .0125f;
    float x = product.horizontal * planeFactor;
    float y = product.vertical * planeFactor;
    float z = product.depth * depthFactor;
    target.addPosition(x, y);
    targetZ += z;
  }

  public void update(float deltaTime) {
    float smooth = deltaTime * 8f;
    float deltaX = (target.getX() - eye.getX()) * smooth;
    float deltaY = (target.getY() - eye.getY()) * smooth;
    float deltaZ = (targetZ - eyeZ) * smooth;
    eye.addPosition(deltaX, deltaY);
    eyeZ += deltaZ;
    refreshCameraEye(camera, eye, eyeZ);
  }

  private static void refreshCameraEye(OrthographicCamera camera, Coordinate eye, float eyeZ) {
    camera.position.set(eye.getX(), eye.getY(), 0f);
    camera.zoom = eyeZ;
    camera.update();
  }

}
