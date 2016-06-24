import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import commons.Logger


object ApplicationEntry {

  @JvmStatic fun main(args: Array<String>) {
    Logger.APPLICATION.log("Starting.")
    val config = createConfig()
    LwjglApplication(LazyInitialisationAdapter(), config)
  }

  private fun createConfig(): LwjglApplicationConfiguration {
    val config = LwjglApplicationConfiguration()
    config.width = 1600
    config.height = 900
    config.samples = 8
    //config.foregroundFPS = 10
    return config
  }

  private class LazyInitialisationAdapter : ApplicationAdapter() {

    private var frame: Main? = null

    override fun create() {
      Logger.APPLICATION.log("Initialising game.")
      frame = Main()
    }

    override fun render() = frame!!.onFrame()

  }

}