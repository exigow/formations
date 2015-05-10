package logic.input.adapter;

import logic.input.TriggerAction;
import logic.input.Trigger;

public class TriggerAdapter {

  public static Adapter instantiate(Trigger trigger, TriggerAction action) {
    if (trigger.type == InputType.KEYBOARD)
      return new KeyAdapter(trigger, action);
    if (trigger.type == InputType.MOUSE)
      return new MouseAdapter(trigger, action);
    return null;
  }

  private static class KeyAdapter extends Adapter {

    public KeyAdapter(Trigger trigger, TriggerAction action) {
      super(trigger, action);
    }

    @Override
    public boolean keyDown(int keycode) {
      if (isThisKey(keycode))
        action.onPress();
      return false;
    }

    @Override
    public boolean keyUp(int keycode) {
      if (isThisKey(keycode))
        action.onRelease();
      return false;
    }

  }

  private static class MouseAdapter extends Adapter {

    public MouseAdapter(Trigger trigger, TriggerAction action) {
      super(trigger, action);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
      if (isThisKey(button))
        action.onPress();
      return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
      if (isThisKey(button))
        action.onRelease();
      return false;
    }

  }

}
