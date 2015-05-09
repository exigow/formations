package logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleFormatter extends Formatter {

  private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  @Override
  public String format(LogRecord record) {
    String formattedDate = TIME_FORMAT.format(dateOf(record));
    return "[" + formattedDate + "] " + formatMessage(record) + LINE_SEPARATOR;
  }

  private static Date dateOf(LogRecord record) {
    return new Date(record.getMillis());
  }

}
