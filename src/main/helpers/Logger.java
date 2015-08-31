package helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

  public static void log(String message) {
    LocalDateTime time = LocalDateTime.now();
    String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    present(formattedTime + " " + message);
  }

  private static void present(String message) {
    System.out.println(message);
  }

}
