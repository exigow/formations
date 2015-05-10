package logic.input.adapter;

import logic.input.TriggerAction;
import com.badlogic.gdx.InputAdapter;
import logic.input.Trigger;

public abstract class Adapter extends InputAdapter {

  protected final TriggerAction action;
  private final Trigger trigger;

  public Adapter(Trigger trigger, TriggerAction action) {
    this.trigger = trigger;
    this.action = action;
  }

  protected boolean isThisKey(int keyCode) {
    return trigger.gdxKey == keyCode;
  }

}
