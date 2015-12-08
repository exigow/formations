package rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import world.Ship;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

class RenderUtils {

  private static final Matrix4 PROJECTION = new Matrix4();
  private static final ImmediateModeRenderer20 RENDERER = new ImmediateModeRenderer20(false, true, 0);
  private static final ShapeRenderer SHAPE = new ShapeRenderer();

  private RenderUtils() {
  }

  static {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    Gdx.gl.glLineWidth(2);
  }

  public static void setProjection(Matrix4 projection) {
    PROJECTION.set(projection);
    SHAPE.setProjectionMatrix(projection);
  }

  public static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  public static void renderHull(Collection<Vector2> positions, Color fill, Color outline) {
    Vector2[] hulls = ConvexHullCalculator.calculateConvexHull(positions);
    RENDERER.begin(PROJECTION, GL20.GL_TRIANGLE_FAN);
    for (Vector2 hull : hulls)
      point(hull, fill);
    RENDERER.end();
    RENDERER.begin(PROJECTION, GL20.GL_LINE_LOOP);
    for (Vector2 hull : hulls)
      point(hull, outline);
    RENDERER.end();
  }

  public static void renderShip(Ship ship, float border, Color fill, Color outline) {
    Vector2 position = ship.place.position;
    SHAPE.setColor(fill);
    SHAPE.begin(ShapeRenderer.ShapeType.Filled);
    renderCircle(position, ship.size + border);
    SHAPE.end();
    SHAPE.setColor(outline);
    SHAPE.begin(ShapeRenderer.ShapeType.Line);
    renderCircle(position, ship.size + border);
    float dir = ship.place.direction;
    Vector2 vector = new Vector2(cosDeg(dir), sinDeg(dir)).scl(32);
    SHAPE.line(position.x, position.y, position.x + vector.x, position.y + vector.y);
    renderLine(position, ship.destination.position);
    SHAPE.end();
  }

  private static void renderLine(Vector2 from, Vector2 to) {
    SHAPE.line(from.x, from.y, to.x, to.y);
  }

  private static void renderCircle(Vector2 where, float size) {
    SHAPE.circle(where.x, where.y, size);
  }

  private static void point(Vector2 position, Color color) {
    RENDERER.color(color);
    RENDERER.vertex(position.x, position.y, 0);
  }

}
