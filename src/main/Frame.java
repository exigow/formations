import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import helpers.WorldDebugInitializer;
import logic.CameraController;
import renderers.DebugRenderer;
import world.World;

public class Frame {

  private final Vector3 cameraEye = new Vector3(0, 0, 1);
  private final Vector3 cameraEyeTarget = new Vector3(cameraEye);
  private final DebugRenderer renderer = new DebugRenderer();
  private final OrthographicCamera camera = new OrthographicCamera();
  private final World world = WorldDebugInitializer.init();
  private final CameraController controller = new CameraController();

  {
    camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void update() {
    cameraEyeTarget.add(controller.actualMovementVector().scl(8, 8, .1f));
    cameraEye.x += (cameraEyeTarget.x - cameraEye.x) * .25f;
    cameraEye.y += (cameraEyeTarget.y - cameraEye.y) * .25f;
    cameraEye.z += (cameraEyeTarget.z - cameraEye.z) * .25f;
    camera.position.set(cameraEye.x, cameraEye.y, 0);
    camera.zoom = cameraEye.z;
    camera.update();
  }

  public void render() {
    renderer.renderWorld(world, camera.combined);
  }

}
