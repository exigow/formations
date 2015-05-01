import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Runner {

  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = 1024;
    config.height = 640;
    config.samples = 8;
    new LwjglApplication(new Wrapper(), config);
  }

  private static class Wrapper implements ApplicationListener {

    private Main main;

    @Override
    public void create() {
      main = new Main();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
      float deltaTime = Gdx.graphics.getDeltaTime();
      main.render(deltaTime);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

  }


}
