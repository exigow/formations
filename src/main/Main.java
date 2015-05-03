import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import input.InputAgent;
import input.camera.MovementRule;
import input.camera.MovementRuleResolver;
import models.Entity;
import rendering.RenderAgent;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final MovementRule mover = new MovementRuleResolver();
  private final Collection<Entity> entities = new ArrayList<Entity>() {{
    for (int i = 0; i++ < 128;)
      add(Entity.random());
  }};

  public void update(float deltaTime) {
    MovementRule.Product product = mover.specify(input);
    System.out.println(product.horizontal + ", " + product.vertical);
    camera.position.x += product.horizontal;
    camera.position.y += product.vertical;
    camera.update();
  }

  public void render() {
    clearBackground();
    agent.setProjectionMatrix(camera.combined);
    renderFps(agent);
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities)
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
