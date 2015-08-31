package logic.input;

@FunctionalInterface
public interface Action {

  void execute(State state);

}
