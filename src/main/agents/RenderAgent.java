package agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import models.Camera;

public class RenderAgent {

  public final SpriteBatch batch = new SpriteBatch();
  public final ShapeRenderer shape = new ShapeRenderer();
  public final BitmapFont debugFont = new BitmapFont(Gdx.files.internal("data/comfortaa.fnt"));

  public void setProjection(Camera camera) {
    Matrix4 projection = camera.getOrthographicCamera().combined;
    batch.setProjectionMatrix(projection);
    shape.setProjectionMatrix(projection);
  }

}
