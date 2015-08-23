package renderers;

import agents.RenderAgent;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import world.models.Entity;

import java.util.Collection;

public class SelectionRenderer {

  public static void render(RenderAgent agent, Collection<Entity> entities, int border) {
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities)
      agent.shape.circle(entity.position.x, entity.position.y, entity.getRadius() + border);
    agent.shape.end();
  }

}
