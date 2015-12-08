package logic.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController {

  private final Vector3 eye = new Vector3(0, 0, 1);
  private final Vector3 target = new Vector3(eye);
  private final OrthographicCamera orthographicCamera = new OrthographicCamera();
  private final CameraMovementResolver resolver = new CameraMovementResolver();

  {
    orthographicCamera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void update() {
    Vector3 movement = resolver.actualMovementVector().scl(8, 8, .1f);
    target.add(movement);
    moveEyeToTarget();
    updateOrthographicCameraState();
  }

  private void moveEyeToTarget() {
    eye.x += (target.x - eye.x) * .25f;
    eye.y += (target.y - eye.y) * .25f;
    eye.z += (target.z - eye.z) * .25f;
  }

  private void updateOrthographicCameraState() {
    orthographicCamera.position.set(eye.x, eye.y, 0);
    orthographicCamera.zoom = eye.z;
    orthographicCamera.update();
  }

  public Vector2 unproject(Vector2 position) {
    Vector3 boxed = new Vector3(position.x, position.y, 0);
    Vector3 unprojected = orthographicCamera.unproject(boxed);
    return new Vector2(unprojected.x, unprojected.y);
  }

  public Matrix4 matrix() {
    return orthographicCamera.combined;
  }

}
