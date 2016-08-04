package assets.templates


internal object TemplateValidator {

  fun ensureThat(condition: Boolean) {
    if (!condition)
      throw TemplateValidationException("condition failed")
  }

  fun ensureNotEmpty(string: String) {
    if (string.isEmpty())
      throw TemplateValidationException("string [$string] must be not empty")
  }

  fun ensurePositive(value: Float) {
    if (value < 0f)
      throw TemplateValidationException("value [$value] must be positive")
  }

  fun ensureRange(value: Float, range: ClosedRange<Float>) {
    val min = range.start
    val max = range.endInclusive
    if (value < 0f || value > 1f)
      throw TemplateValidationException("value [$value] must be in range [$min to $max]")
  }

  private class TemplateValidationException(message: String) : RuntimeException(message)

}