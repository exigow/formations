package logic.camera;

import com.badlogic.gdx.Input;
import agents.InputAgent;

public class ManualKeyboardRule implements MovementRule {

  private final static int UP = Input.Keys.W;
  private final static int DOWN = Input.Keys.S;
  private final static int LEFT = Input.Keys.A;
  private final static int RIGHT = Input.Keys.D;

  @Override
  public Product specify(InputAgent agent) {
    boolean up = agent.isKeyPressed(UP);
    boolean down = agent.isKeyPressed(DOWN);
    boolean left = agent.isKeyPressed(LEFT);
    boolean right = agent.isKeyPressed(RIGHT);
    int x = specifyDimension(right, left);
    int y = specifyDimension(up, down);
    return new Product(x, y);
  }

  private static int specifyDimension(boolean positive, boolean negative) {
    if (positive)
      return POSITIVE;
    if (negative)
      return NEGATIVE;
    return NEUTRAL;
  }

}
