package logic.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController {

  private final Vector3 cameraEye = new Vector3(0, 0, 1);
  private final Vector3 cameraEyeTarget = new Vector3(cameraEye);
  private final OrthographicCamera orthographicCamera = new OrthographicCamera();
  private final CameraMovementResolver resolver = new CameraMovementResolver();

  {
    orthographicCamera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void update() {
    cameraEyeTarget.add(resolver.actualMovementVector().scl(8, 8, .1f));
    cameraEye.x += (cameraEyeTarget.x - cameraEye.x) * .25f;
    cameraEye.y += (cameraEyeTarget.y - cameraEye.y) * .25f;
    cameraEye.z += (cameraEyeTarget.z - cameraEye.z) * .25f;
    orthographicCamera.position.set(cameraEye.x, cameraEye.y, 0);
    orthographicCamera.zoom = cameraEye.z;
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
