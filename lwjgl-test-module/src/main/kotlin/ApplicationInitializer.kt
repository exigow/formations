import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil

class ApplicationInitializer(private val config: Configuration, private val onFrame: (delta: Float) -> Unit) {

  private val timer = ApplicationTimer()

  fun run() {
    try {
      val w = init()
      loop(w)
      Callbacks.glfwFreeCallbacks(w);
      glfwDestroyWindow(w);
    } finally {
      glfwTerminate();
      glfwSetErrorCallback(null).free();
    }
  }

  private fun init(): Long {
    GLFWErrorCallback.createPrint(System.err).set();
    initialiseGLFW()
    glfwWindowHint(GLFW_VISIBLE, toGlfwValue(config.hidden));
    glfwWindowHint(GLFW_RESIZABLE, toGlfwValue(config.resizeable));
    val window = createWindow()
    center(window)
    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    glfwShowWindow(window);
    return window
  }

  private fun toGlfwValue(boolean: Boolean) = when(boolean) {
    true -> GLFW_TRUE
    false -> GLFW_FALSE
  }

  private fun loop(window: Long) {
    GL.createCapabilities()
    while (!glfwWindowShouldClose(window)) {
      timer.tick()
      onFrame.invoke(timer.deltaTime())
      glfwSwapBuffers(window)
      glfwPollEvents()
    }
  }

  private fun initialiseGLFW() {
    val result = glfwInit()
    if (!result)
      throw RuntimeException("Unable to initialize GLFW");
  }

  private fun createWindow(): Long {
    val window = glfwCreateWindow(config.width, config.height, config.title, MemoryUtil.NULL, MemoryUtil.NULL);
    if (window == MemoryUtil.NULL)
      throw RuntimeException("Failed to create the GLFW window");
    return window
  }

  private fun center(handle: Long) {
    val videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    glfwSetWindowPos(
      handle,
      (videoMode.width() - config.width) / 2,
      (videoMode.height() - config.height) / 2
    );
  }

  data class Configuration(
    val width: Int,
    val height: Int,
    val title: String = "unnamed",
    val resizeable: Boolean = true,
    val hidden: Boolean = false
  )

}