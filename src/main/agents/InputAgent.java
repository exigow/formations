package agents;

import attributes.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import logic.camera.Camera;
import logic.input.StateListener;
import logic.input.states.State;
import models.CoordinateSimple;
import logic.input.Trigger;

import java.util.HashMap;
import java.util.Map;

public class InputAgent {

  private final Coordinate windowSize = new CoordinateSimple();
  private final Coordinate mouseWindow = new CoordinateSimple();
  private final Coordinate mouseWorld = new CoordinateSimple();
  private final Map<Trigger, StateListener> listeners = initialiseMap(Trigger.values());

  private static Map<Trigger, StateListener> initialiseMap(Trigger[] triggers) {
    Map<Trigger, StateListener> map = new HashMap<>();
    for (Trigger trigger : triggers)
      map.put(trigger, new StateListener());
    return map;
  }

  public void update(Camera camera) {
    updateWindowSize();
    updateMouseWindow();
    updateMouseWorld(camera);
    listenTriggers();
  }

  @SuppressWarnings("deprecation")
  private void updateMouseWorld(Camera camera) {
    Vector3 asVector = new Vector3(mouseWindow.getX(), mouseWindow.getY(), 0f);
    Vector3 projected = camera.getOrthographicCamera().unproject(asVector);
    mouseWorld.set(projected.x, projected.y);
  }

  private void listenTriggers() {
    for (Trigger trigger : listeners.keySet()) {
      boolean pressed = isGdxPressed(trigger);
      StateListener listener = listeners.get(trigger);
      listener.listen(pressed);
    }
  }

  public Coordinate getWindowSize() {
    return windowSize;
  }

  private void updateWindowSize() {
    float x = Gdx.graphics.getWidth();
    float y = Gdx.graphics.getHeight();
    windowSize.set(x, y);
  }

  private void updateMouseWindow() {
    float x = Gdx.input.getX();
    float y = Gdx.input.getY();
    mouseWindow.set(x, y);
  }

  public Coordinate getMouseWindow() {
    return mouseWindow;
  }

  public Coordinate getMouseWorld() {
    return mouseWorld;
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
