package models;

import attributes.Coordinate;
import attributes.CoordinateImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import input.camera.MovementRule;

public class Camera {

  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final Coordinate target = new CoordinateImpl();
  private final Coordinate eye = new CoordinateImpl();

  @Deprecated
  public OrthographicCamera getOrthographicCamera() {
    return camera;
  }

  public void addMovement(MovementRule.Product product) {
    float factor = 4f;
    float x = product.horizontal * factor;
    float y = product.vertical * factor;
    target.set(x, y);
  }

  public void update(float deltaTime) {
    float smooth = deltaTime * 16f;
    float deltaX = (target.getX() - eye.getX()) * smooth;
    float deltaY = (target.getY() - eye.getY()) * smooth;
    eye.add(deltaX, deltaY);
    camera.translate(eye.getX(), eye.getY());
    camera.update();
  }

}
