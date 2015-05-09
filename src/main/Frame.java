import agents.InputAgent;
import agents.RenderAgent;
import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import logic.camera.Camera;
import logic.input.Trigger;
import models.CoordinateSimple;
import models.Entity;
import models.World;
import renderers.VariablesRenderer;

public class Frame {

  private final RenderAgent agent = new RenderAgent();
  private final InputAgent input = new InputAgent();
  private final VariablesRenderer variables = new VariablesRenderer();
  private final Camera camera = new Camera();
  private final World world = new World();
  private final Coordinate from = new CoordinateSimple();

  public void update(float deltaTime) {
    camera.updateMovementRules(input);
    camera.update(deltaTime);
    input.listenTriggers();
    variables.present("fps", Gdx.graphics.getFramesPerSecond());
    switch (input.stateOf(Trigger.MOUSE_LEFT)) {
      case PRESS:
        from.set(input.getMouseWindow());
        variables.present("from", from);
        break;
      case RELEASE:
        Coordinate to = new CoordinateSimple();
        to.set(input.getMouseWindow());
        Rectangle rect = toRectangle(from, to);
        System.out.println(rect);
        variables.present("to", to);
        break;
    }
  }

  private static Rectangle toRectangle(Coordinate from, Coordinate to) {
    float deltaX = from.getX() - to.getX();
    float deltaY = from.getY() - to.getY();
    return new Rectangle(from.getX(), from.getY(), deltaX, deltaY);
  }

  public void render() {
    clearBackground();
    agent.setProjection(camera);
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : world.entities)
      entity.render(agent);
    agent.shape.end();
    variables.render(agent);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
