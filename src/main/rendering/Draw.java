package rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class Draw {

  private final static BitmapFont DEFAULT_FONT = new BitmapFont(Gdx.files.internal("data/comfortaa.fnt"), true);
  private final static SpriteBatch BATCH = new SpriteBatch();
  private static final ShapeRenderer SHAPE = new ShapeRenderer();

  public static void updateProjection(Matrix4 projection) {
    BATCH.setProjectionMatrix(projection);
    SHAPE.setProjectionMatrix(projection);
  }

  public static void drawText(String text, float x, float y) {
    BATCH.begin();
    DEFAULT_FONT.draw(BATCH, text, x, y);
    BATCH.end();
  }

  public static void drawCircleFilled(float x, float y, float radius) {
    drawCircle(x, y, radius, ShapeType.Filled);
  }

  public static void drawCircleOutline(float x, float y, float radius) {
    drawCircle(x, y, radius, ShapeType.Line);
  }

  private static void drawCircle(float x, float y, float radius, ShapeType type) {
    SHAPE.begin(type);
    SHAPE.circle(x, y, radius);
    SHAPE.end();
  }

  public static void drawLine(float fromX, float fromY, float toX, float toY) {
    SHAPE.begin(ShapeType.Line);
    SHAPE.line(fromX, fromY, toX, toY);
    SHAPE.end();
  }

  public static void clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
