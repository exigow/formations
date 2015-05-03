package agents;

import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import attributes.CoordinateImpl;

public class InputAgent {

  private final Coordinate windowSize = new CoordinateImpl();
  private final Coordinate mouseWindow = new CoordinateImpl();

  public Coordinate getWindowSize() {
    float x = Gdx.graphics.getWidth();
    float y = Gdx.graphics.getHeight();
    windowSize.set(x, y);
    return windowSize;
  }

  public Coordinate getMouseWindow() {
    float x = Gdx.input.getX();
    float y = Gdx.input.getY();
    mouseWindow.set(x, y);
    return mouseWindow;
  }

  public boolean isKeyPressed(int key) {
    return Gdx.input.isKeyPressed(key);
  }

  public boolean isButtonPressed(int button) {
    return Gdx.input.isButtonPressed(button);
  }

}
