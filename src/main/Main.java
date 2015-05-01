import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import rendering.RenderAgent;

public class Main {

  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final RenderAgent agent = new RenderAgent();

  public void render(float deltaTime) {
    clearBackground();
    agent.setProjectionMatrix(camera.combined);
    renderFps(agent);
  }

  private static void clearBackground() {
    Gdx.gl20.glClearColor(.125f, .125f, .125f, 1f);
    Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  private static void renderFps(RenderAgent agent) {
    int fps = Gdx.graphics.getFramesPerSecond();
    agent.batch.begin();
    agent.debugFont.draw(agent.batch, "FPS = " + fps, 0, 0);
    agent.batch.end();
  }

}
