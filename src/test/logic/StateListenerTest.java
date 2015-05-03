package logic;

import org.testng.annotations.Test;

import static logic.StateListener.State.*;
import static org.testng.Assert.assertEquals;

public class StateListenerTest {

  private final static boolean ON = true;
  private final static boolean OFF = false;

  @Test
  public void fullStateChain() throws Exception {
    StateListener listener = new StateListener();
    listener.listen(OFF);
    assertState(listener, WAIT);
    listener.listen(ON);
    assertState(listener, PRESS);
    listener.listen(ON);
    assertState(listener, HOLD);
    listener.listen(OFF);
    assertState(listener, RELEASE);
    listener.listen(OFF);
    assertState(listener, WAIT);
  }

  @Test
  public void noChange() throws Exception {
    StateListener listener = new StateListener();
    listener.listen(OFF);
    assertEquals(listener.state(), WAIT);
    listener.listen(OFF);
    assertEquals(listener.state(), WAIT);
  }

  private static void assertState(StateListener listener, StateListener.State state) {
    assertEquals(listener.state(), state);
  }

}