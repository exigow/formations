package input.camera;

import com.badlogic.gdx.Input;
import input.InputAgent;

public class ManualKeyboardRule implements MovementRule {

  private final static int UP = Input.Keys.W;
  private final static int DOWN = Input.Keys.S;
  private final static int LEFT = Input.Keys.A;
  private final static int RIGHT = Input.Keys.D;

  @Override
  public Product specify(InputAgent agent) {
    int x = specifyDimension(agent.isKeyPressed(RIGHT), agent.isKeyPressed(LEFT));
    int y = specifyDimension(agent.isKeyPressed(UP), agent.isKeyPressed(DOWN));
    return new Product(x, y);
  }

  private static int specifyDimension(boolean positive, boolean negative) {
    if (positive)
      return 1;
    if (negative)
      return -1;
    return 0;
  }

}
