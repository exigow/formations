package agents;

import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import attributes.CoordinateImpl;
import mappings.Trigger;

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

  public boolean isKeyboardKeyPressed(Trigger key) {
    return Gdx.input.isKeyPressed(key.gdxKey);
  }

  public boolean isMouseButtonPressed(Trigger button) {
    return Gdx.input.isButtonPressed(button.gdxKey);
  }

  /*
    private final static int LEFT_BUTTON = Input.Buttons.LEFT;
  private final static int RIGHT_BUTTON = Input.Buttons.RIGHT;
  private final StateListener left = new StateListener();
  private final StateListener right = new StateListener();
  private final Coordinate position = new CoordinateImpl();

  public void update() {
    updatePosition(position);
    boolean leftTrigger = Gdx.input.isMouseButtonPressed(LEFT_BUTTON);
    boolean rightTrigger = Gdx.input.isMouseButtonPressed(RIGHT_BUTTON);
    left.listen(leftTrigger);
    right.listen(rightTrigger);
  }

  private static void updatePosition(Coordinate position) {
    float x = Gdx.input.getX();
    float y = Gdx.input.getY();
    position.set(x, y);
  }

   */

}
