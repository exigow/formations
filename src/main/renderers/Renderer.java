package renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import helpers.ConvexHull;

import java.util.Collection;

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

  public void renderCircle(Vector2 position, float radius, Color fill, Color outline) {
    int segments = 6;
    shape.setColor(fill);
    shape.begin(ShapeRenderer.ShapeType.Filled);
    shape.circle(position.x, position.y, radius, segments);
    shape.end();
    shape.setColor(outline);
    shape.begin(ShapeRenderer.ShapeType.Line);
    shape.circle(position.x, position.y, radius, segments);
    shape.end();
  }

  private void point(Vector2 position, Color color) {
    imr.color(color);
    imr.vertex(position.x, position.y, 0);
  }

}
