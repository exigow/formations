package renderers;

import agents.RenderAgent;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import world.models.Entity;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.*;

public class EntityRenderer {

  public static void render(RenderAgent agent, Collection<Entity> entities) {
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities)
      renderEntity(agent, entity);
    agent.shape.end();
  }

  private static void renderEntity(RenderAgent agent, Entity entity) {
    agent.shape.circle(entity.position.x, entity.position.y, entity.getRadius());
    float toX = entity.position.x + cos(entity.getAngle() * degreesToRadians) * entity.getRadius();
    float toY = entity.position.y + sin(entity.getAngle() * degreesToRadians) * entity.getRadius();
    agent.shape.line(entity.position.x, entity.position.y, toX, toY);
  }

}
