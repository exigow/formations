package renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import helpers.ConvexHull;
import world.Entity;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

public class Renderer {

  private final Matrix4 projection = new Matrix4();
  private final ImmediateModeRenderer20 imr = new ImmediateModeRenderer20(false, true, 0);
  private final ShapeRenderer shape = new ShapeRenderer();

  private Renderer() {
  }

  public static Renderer setup() {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    //Gdx.gl.glLineWidth(2);
    return new Renderer();
  }

  public void setProjection(Matrix4 projection) {
    this.projection.set(projection);
    shape.setProjectionMatrix(projection);
  }

  public void renderHull(Collection<Vector2> positions, Color fill, Color outline) {
    Vector2[] hulls = ConvexHull.convexHull(positions);
    imr.begin(projection, GL20.GL_TRIANGLE_FAN);
    for (Vector2 hull : hulls)
      point(hull, fill);
    imr.end();
    imr.begin(projection, GL20.GL_LINE_LOOP);
    for (Vector2 hull : hulls)
      point(hull, outline);
    imr.end();
  }

  public void renderEntity(Entity entity, float border, Color fill, Color outline) {
    Vector2 position = entity.position;
    int segments = 6;
    shape.setColor(fill);
    shape.begin(ShapeRenderer.ShapeType.Filled);
    shape.circle(position.x, position.y, entity.size + border, segments);
    shape.end();
    shape.setColor(outline);
    shape.begin(ShapeRenderer.ShapeType.Line);
    shape.circle(position.x, position.y, entity.size + border, segments);
    Vector2 vector = new Vector2(cosDeg(entity.direction), sinDeg(entity.direction)).scl(32);
    shape.line(position.x, position.y, position.x + vector.x, position.y + vector.y);
    shape.end();
  }

  private void point(Vector2 position, Color color) {
    imr.color(color);
    imr.vertex(position.x, position.y, 0);
  }

}
