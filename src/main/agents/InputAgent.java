package agents;

import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import models.CoordinateSimple;
import logic.input.Trigger;

public class InputAgent {

  private final Coordinate windowSize = new CoordinateSimple();
  private final Coordinate mouseWindow = new CoordinateSimple();

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

}
