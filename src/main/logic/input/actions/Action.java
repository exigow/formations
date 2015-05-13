package logic.input.actions;

import logic.input.State;

@FunctionalInterface
public interface Action {

  void execute(State state);

}
