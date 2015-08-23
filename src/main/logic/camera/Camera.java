package logic.camera;

import agents.InputAgent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import logic.camera.rules.MovementRule.Product;
import logic.camera.rules.Resolver;

public class Camera {

  private final Resolver resolver;
  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final Vector2 target = new Vector2();
  private final Vector2 eye = new Vector2();
  private float targetZ = 1f;
  private float eyeZ = targetZ;
  private static final float MAX_ZOOM = 4f;
  private static final float MIN_ZOOM = .0125f;

  public Camera(Resolver resolver) {
    this.resolver = resolver;
  }

  @Deprecated
  public OrthographicCamera getOrthographicCamera() {
    return camera;
  }

  public void updateMovementRules(InputAgent agent) {
    Product product = resolver.specify(agent);
    float planeFactor = 16f;
    float depthFactor = .05f;
    float x = product.horizontal * planeFactor;
    float y = product.vertical * planeFactor;
    float z = product.depth * depthFactor;
    target.add(x, y);
    targetZ += z;
    targetZ = MathUtils.clamp(targetZ, MIN_ZOOM, MAX_ZOOM);
  }

  public void update(float deltaTime) {
    float smooth = deltaTime * 8f;
    float deltaX = (target.x - eye.x) * smooth;
    float deltaY = (target.y - eye.y) * smooth;
    float deltaZ = (targetZ - eyeZ) * smooth;
    eye.add(deltaX, deltaY);
    eyeZ += deltaZ;
    refreshCameraEye(camera, eye, eyeZ);
  }

  private static void refreshCameraEye(OrthographicCamera camera, Vector2 eye, float eyeZ) {
    camera.position.set(eye.x, eye.y, 0f);
    camera.zoom = eyeZ;
    camera.update();
  }

}
