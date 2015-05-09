package renderers;

import agents.RenderAgent;
import attributes.Coordinate;
import attributes.Radius;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Set;

public class SelectionRenderer {

  public static <T extends Coordinate & Radius> void render(RenderAgent agent, Set<T> entities, int border) {
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (T entity : entities)
      agent.shape.circle(entity.getX(), entity.getY(), entity.getRadius() + border);
    agent.shape.end();
  }

}
