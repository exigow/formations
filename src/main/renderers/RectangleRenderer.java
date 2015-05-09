package renderers;

import agents.RenderAgent;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class RectangleRenderer {

  public static void render(RenderAgent agent, Rectangle rectangle) {
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    float toX = rectangle.x + rectangle.width;
    float toY = rectangle.y + rectangle.height;
    agent.shape.line(rectangle.getX(), rectangle.getY(), toX, toY);
    agent.shape.rect(rectangle.getX(), rectangle.getY(), rectangle.width, rectangle.height);
    agent.shape.end();
  }

}
