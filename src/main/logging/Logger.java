package logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

public enum Logger {

  INPUT;

  private final String name;

  {
    name = createName();
    register();
  }

  public void info(String msg) {
    juliLogger().info(msg);
  }

  private String createName() {
    return Logger.class.getName() + "." + name();
  }

  private java.util.logging.Logger juliLogger() {
    return java.util.logging.Logger.getLogger(name);
  }

  private void register() {
    java.util.logging.Logger logger = juliLogger();
    logger.addHandler(handler());
    logger.setUseParentHandlers(false);
  }

  private static Handler handler() {
    Handler handler = new ConsoleHandler();
    handler.setFormatter(new ConsoleFormatter());
    return handler;
  }

}
