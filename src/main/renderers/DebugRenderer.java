package renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import world.Entity;

import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.*;

public class DebugRenderer {

  public final ShapeRenderer shape = new ShapeRenderer();

  public void renderEntities(Set<Entity> entities) {
    shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities) {
      shape.circle(entity.position.x, entity.position.y, entity.size);
      float toX = entity.position.x + cos(entity.angle * degreesToRadians) * entity.size;
      float toY = entity.position.y + sin(entity.angle * degreesToRadians) * entity.size;
      shape.line(entity.position.x, entity.position.y, toX, toY);
    }
    shape.end();
  }

  public void renderSelection(Set<Entity> entities, int border) {
    shape.begin(ShapeRenderer.ShapeType.Line);
    for (Entity entity : entities)
      shape.circle(entity.position.x, entity.position.y, entity.size + border);
    shape.end();
  }

  public void renderRectangle(Rectangle rectangle) {
    shape.begin(ShapeRenderer.ShapeType.Line);
    shape.rect(rectangle.getX(), rectangle.getY(), rectangle.width, rectangle.height);
    shape.end();
  }

  public void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
