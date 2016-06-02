import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration


object ApplicationEntry {

  @JvmStatic fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = 1280
    config.height = 800
    config.samples = 8
    config.title = "Test"
    LwjglApplication(LazyInitialisationAdapter(), config)
  }

  private class LazyInitialisationAdapter : ApplicationAdapter() {

    private var frame: Main? = null

    override fun create() {
      frame = Main()
    }

    override fun render() = frame!!.onRender()

  }

}