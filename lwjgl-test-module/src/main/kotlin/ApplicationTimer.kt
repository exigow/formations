
class ApplicationTimer {

  private var lastFrameTime: Long = -1
  private var delta: Float = 0f
  private var frameCounterStart: Long = 0
  private var frames: Int = 0
  private var fps: Int = 0

  fun tick() {
    val time = System.nanoTime()
    if (lastFrameTime == -1L)
      lastFrameTime = time
    delta = (time - lastFrameTime) / 1000000000.0f
    lastFrameTime = time
    if (time - frameCounterStart >= 1000000000) {
      fps = frames
      frames = 0
      frameCounterStart = time
    }
    frames++
  }

  fun deltaTime() = delta

  fun framesPerSecond() = fps

}