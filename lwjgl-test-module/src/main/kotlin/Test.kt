import org.lwjgl.opengl.GL11.*

object Test {

  @JvmStatic fun main(args: Array<String>) {
    val config = ApplicationInitializer.Configuration(
      width = 640,
      height = 480,
      resizeable = true
    )
    ApplicationInitializer(config, onFrame = {
      glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
      glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }).run()
  }

}