import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import rendering.BatchingRenderAgent;
import rendering.Vec2;

import static rendering.Color.Preset.BLUE;
import static rendering.Color.Preset.RED;
import static rendering.Color.of;
import static rendering.Statement.circle;

public class Runner {

  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = 1024;
    config.height = 640;
    config.samples = 8;
    config.title = "Test";
    new LwjglApplication(new Wrapper(), config);
  }

  private static class Wrapper implements ApplicationListener {

    //private Frame frame;

    @Override
    public void create() {
      //frame = new Frame();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
      new BatchingRenderAgent().reset()
        .render(circle(Vec2.of(4, 17), 16).color(of(RED)).outline(of(BLUE)).outlineWidth(2))
        .render(circle(Vec2.zero(), 4))
        .flush();
      //frame.update();
      //frame.render();
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
