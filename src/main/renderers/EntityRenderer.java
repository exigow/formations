package renderers;

import agents.RenderAgent;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import models.Entity;

import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.*;

public class EntityRenderer {

  public static void render(RenderAgent agent, Set<Entity> entities) {
    agent.shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities)
      renderEntity(agent, entity);
    agent.shape.end();
  }

  private static void renderEntity(RenderAgent agent, Entity entity) {
    agent.shape.circle(entity.getX(), entity.getY(), entity.getRadius());
    float toX = entity.getX() + cos(entity.getAngle() * degreesToRadians) * entity.getRadius();
    float toY = entity.getY() + sin(entity.getAngle() * degreesToRadians) * entity.getRadius();
    agent.shape.line(entity.getX(), entity.getY(), toX, toY);
  }

}
