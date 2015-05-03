import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import models.Entity;
import rendering.RenderAgent;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final RenderAgent agent = new RenderAgent();
  private final Collection<Entity> entities = new ArrayList<Entity>() {{
    float scale = 256;
    for (int i = 0; i++ < 128;) {
      add(new Entity(rnd() * scale, rnd() * scale, 0, 8));
    }
  }};

  private static float rnd() {
    return -1 + (float) Math.random() * 2;
  }

  public void update(float deltaTime) {
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
