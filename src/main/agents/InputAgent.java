package agents;

import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import logic.input.StateListener;
import logic.input.states.State;
import models.CoordinateSimple;
import logic.input.Trigger;

import java.util.HashMap;
import java.util.Map;

public class InputAgent {

  private final Coordinate windowSize = new CoordinateSimple();
  private final Coordinate mouseWindow = new CoordinateSimple();
  private final Map<Trigger, StateListener> listeners = initialiseMap(Trigger.values());

  private static Map<Trigger, StateListener> initialiseMap(Trigger[] triggers) {
    Map<Trigger, StateListener> map = new HashMap<>();
    for (Trigger trigger : triggers)
      map.put(trigger, new StateListener());
    return map;
  }

  public void listenTriggers() {
    for (Trigger trigger : listeners.keySet()) {
      boolean pressed = isGdxPressed(trigger);
      StateListener listener = listeners.get(trigger);
      listener.listen(pressed);
    }
  }

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

  private static boolean isGdxPressed(Trigger trigger) {
    if (trigger.type == Trigger.InputType.KEYBOARD)
      return Gdx.input.isKeyPressed(trigger.gdxKey);
    return Gdx.input.isButtonPressed(trigger.gdxKey);
  }

  public State stateOf(Trigger trigger) {
    return listeners.get(trigger).state();
  }

  public boolean isPressed(Trigger trigger) {
    return stateOf(trigger).isPressed();
  }

}
