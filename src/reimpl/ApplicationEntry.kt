import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import commons.Logger


object ApplicationEntry {

  @JvmStatic fun main(args: Array<String>) {
    Logger.APPLICATION.log("Starting.")
    val config = createCofing()
    LwjglApplication(LazyInitialisationAdapter(), config)
  }

  private fun createCofing(): LwjglApplicationConfiguration {
    val config = LwjglApplicationConfiguration()
    config.width = 1280
    config.height = 640
    return config
  }

  private class LazyInitialisationAdapter : ApplicationAdapter() {

    private var frame: Main? = null

    override fun create() {
      Logger.APPLICATION.log("Initialising game.")
      frame = Main()
    }

    override fun render() = frame!!.onRender()

  }

}