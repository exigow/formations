import agents.InputAgent;
import agents.RenderAgent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import logic.camera.MovementRule;
import logic.camera.MovementRuleResolver;
import models.Camera;
import models.Entity;
import models.World;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final Camera camera = new Camera();
  private final MovementRule mover = new MovementRuleResolver();
  private final World world = new World();

  public void update(float deltaTime) {
    MovementRule.Product product = mover.specify(input);
    camera.addMovement(product);
    camera.update(deltaTime);
  }

  public void render() {
    clearBackground();
    agent.setProjection(camera);
    renderFps(agent);
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : world.entities)
      entity.render(agent);
    agent.shape.end();
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.125f, .125f, .125f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  private static void renderFps(RenderAgent agent) {
    int fps = Gdx.graphics.getFramesPerSecond();
    agent.batch.begin();
    agent.debugFont.draw(agent.batch, "FPS = " + fps, 0, 0);
    agent.batch.end();
  }

}
