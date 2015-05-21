package agents.helpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import logging.Logger;
import logic.input.Key;
import logic.input.actions.Action;
import logic.input.wrappers.KeyboardWrapper;
import logic.input.wrappers.MouseWrapper;
import logic.input.wrappers.Wrapper;

public class ActionRegistrar {

  private final InputMultiplexer multiplexer = new InputMultiplexer();

  {
    Gdx.input.setInputProcessor(multiplexer);
  }

  public Action register(Key key, Action action) {
    Wrapper wrapper = instantiateWrapper(key, action);
    multiplexer.addProcessor(wrapper);
    Logger.INPUT.info("Registered action on " + key);
    return action;
  }

  private static Wrapper instantiateWrapper(Key key, Action action) {
    if (key == Key.MOUSE_LEFT || key == Key.MOUSE_RIGHT)
      return new MouseWrapper(key, action);
    return new KeyboardWrapper(key, action);
  }


}
