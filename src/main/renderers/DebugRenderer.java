package renderers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import world.models.Entity;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.*;

public class DebugRenderer {
  
  public final ShapeRenderer shape = new ShapeRenderer();

  public void renderEntities(Collection<Entity> entities) {
    shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities) {
      shape.circle(entity.position.x, entity.position.y, entity.size);
      float toX = entity.position.x + cos(entity.angle * degreesToRadians) * entity.size;
      float toY = entity.position.y + sin(entity.angle * degreesToRadians) * entity.size;
      shape.line(entity.position.x, entity.position.y, toX, toY);
    }
    shape.end();
  }

  public void renderSelection(Collection<Entity> entities, int border) {
    shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities)
      shape.circle(entity.position.x, entity.position.y, entity.size + border);
    shape.end();
  }

  public void renderRectangle(Rectangle rectangle) {
    shape.begin(ShapeRenderer.ShapeType.Line);
    float toX = rectangle.x + rectangle.width;
    float toY = rectangle.y + rectangle.height;
    shape.line(rectangle.getX(), rectangle.getY(), toX, toY);
    shape.rect(rectangle.getX(), rectangle.getY(), rectangle.width, rectangle.height);
    shape.end();
  }


}
