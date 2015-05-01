import attributes.AttributeOperations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import models.Entity;

public class Main {

  private final OrthographicCamera camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final BitmapFont font = new BitmapFont(Gdx.files.internal("data/comfortaa.fnt"));
  private final SpriteBatch batch = new SpriteBatch();

  public void render(float deltaTime) {
    clearBackground();
    batch.setProjectionMatrix(camera.combined);
    renderFps(batch, font);

    Entity a = new Entity();
    Entity b = new Entity();
    AttributeOperations.lengthBetween(a, b);
    AttributeOperations.collisionBetween(a, b);
  }

  private static void clearBackground() {
    Gdx.gl20.glClearColor(.125f, .125f, .125f, 1f);
    Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  private static void renderFps(SpriteBatch batch, BitmapFont font) {
    int fps = Gdx.graphics.getFramesPerSecond();
    batch.begin();
    font.draw(batch, "FPS = " + fps, 0, 0);
    batch.end();
  }

}
