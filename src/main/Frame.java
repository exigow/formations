import agents.InputAgent;
import agents.RenderAgent;
import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import logic.camera.rules.MovementRule;
import logic.camera.rules.Resolver;
import logic.camera.Camera;
import logic.input.StateListener;
import logic.input.Trigger;
import models.CoordinateSimple;
import models.Entity;
import models.World;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final Camera camera = new Camera();
  private final World world = new World();

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    input.listenTriggers();
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
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  private static void renderFps(RenderAgent agent) {
    int fps = Gdx.graphics.getFramesPerSecond();
    agent.batch.begin();
    agent.debugFont.draw(agent.batch, "FPS = " + fps, 0, 0);
    agent.batch.end();
  }

}
