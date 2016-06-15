package commons

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class Logger {

  ACTION,
  EVENT_REGISTRY,
  ACTION_REGISTRY,
  APPLICATION;

  fun log(message: String) {
    val time = toFixedLength(formattedNow(), 12)
    val label = toFixedLength(this.name, 12)
    println("$time: [$label]: $message")
  }

  private companion object {

    fun formattedNow() = LocalDateTime.now().format(DateTimeFormatter.ISO_TIME);

    fun toFixedLength(source: String, length: Int): String {
      if (source.length > length)
        return source.substring(0, length)
      return source + " ".repeat(length - source.length)
    }

  }

}