package logic;

import logic.input.TickListener;
import logic.input.states.Tick;
import org.testng.annotations.Test;

import static logic.input.states.Tick.*;
import static org.testng.Assert.assertEquals;

public class TickListenerTest {

  private final static boolean ON = true;
  private final static boolean OFF = false;

  @Test
  public void fullStateChain() throws Exception {
    TickListener listener = new TickListener();
    listener.listen(OFF);
    assertState(listener, ON_WAIT);
    listener.listen(ON);
    assertState(listener, ON_PRESS);
    listener.listen(ON);
    assertState(listener, ON_HOLD);
    listener.listen(OFF);
    assertState(listener, ON_RELEASE);
    listener.listen(OFF);
    assertState(listener, ON_WAIT);
  }

  @Test
  public void noChange() throws Exception {
    TickListener listener = new TickListener();
    listener.listen(OFF);
    assertEquals(listener.state(), ON_WAIT);
    listener.listen(OFF);
    assertEquals(listener.state(), ON_WAIT);
  }

  private static void assertState(TickListener listener, Tick tick) {
    assertEquals(listener.state(), tick);
  }

}