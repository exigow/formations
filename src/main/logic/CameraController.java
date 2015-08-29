package logic;

import com.badlogic.gdx.math.Vector3;
import logic.input.Input;
import logic.input.Key;
import logic.input.State;

public class CameraController {

  private boolean upKeyTrigger = false;
  private boolean downKeyTrigger = false;
  private boolean leftKeyTrigger = false;
  private boolean rightKeyTrigger = false;
  private int scrollValue = 0;

  public CameraController() {
    Input.register(Key.KEY_W, state -> upKeyTrigger = triggerKey(state));
    Input.register(Key.KEY_S, state -> downKeyTrigger = triggerKey(state));
    Input.register(Key.KEY_A, state -> leftKeyTrigger = triggerKey(state));
    Input.register(Key.KEY_D, state -> rightKeyTrigger = triggerKey(state));
    Input.register(Key.MOUSE_SCROLL, state -> scrollValue += scrollAcceleration(state));
  }

  private static boolean triggerKey(State state) {
    switch (state) {
      case UP:
        return true;
      case DOWN:
        return false;
    }
    throw new RuntimeException();
  }

  private static int scrollAcceleration(State state) {
    switch (state) {
      case UP:
        return 1;
      case DOWN:
        return -1;
    }
    throw new RuntimeException();
  }

  public Vector3 actualMovementVector() {
    Vector3 vector = new Vector3();
    vector.x = valueOfDimension(leftKeyTrigger, rightKeyTrigger);
    vector.y = -valueOfDimension(upKeyTrigger, downKeyTrigger);
    vector.z = scrollValue;
    scrollValue = 0;
    return vector;
  }

  private static int valueOfDimension(boolean minus, boolean plus) {
    if (plus && minus)
      return 0;
    if (plus)
      return 1;
    if (minus)
      return -1;
    return 0;
  }

}
