import agents.InputAgent;
import agents.RenderAgent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import logic.camera.Camera;
import logic.input.Trigger;
import models.Entity;
import models.World;
import renderers.RectangleRenderer;
import renderers.VariablesRenderer;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final VariablesRenderer variables = new VariablesRenderer();
  private final Camera camera = new Camera();
  private final World world = new World();
  private final Rectangle rect = new Rectangle();

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    input.update(camera);
    variables.update("fps", Gdx.graphics.getFramesPerSecond());
    switch (input.stateOf(Trigger.MOUSE_LEFT)) {
      case PRESS:
        rect.setPosition(input.getMouseWorld().getX(), input.getMouseWorld().getY());
        variables.update("rect", rect);
        break;
      case RELEASE:
        float deltaX = input.getMouseWorld().getX() - rect.getX();
        float deltaY = input.getMouseWorld().getY() - rect.getY();
        rect.setSize(deltaX, deltaY);
        fixRectangle(rect);
        variables.update("rect", rect);
        break;
    }
  }

  private static void fixRectangle(Rectangle rectangle) {
    // todo
  }

  public void render() {
    clearBackground();
    agent.setProjection(camera);
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : world.entities)
      entity.render(agent);
    agent.shape.end();
    variables.render(agent);
    RectangleRenderer.render(agent, rect);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
