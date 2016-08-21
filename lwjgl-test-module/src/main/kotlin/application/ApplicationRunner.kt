package application

import game.Main

object ApplicationRunner {

  @JvmStatic fun main(args: Array<String>) {
    val config = ApplicationInitializer.Configuration(
      width = 640,
      height = 480,
      resizeable = true
    )
    var main: Main? = null
    ApplicationInitializer(config,
      onCreate = {
        println("a")
        main = Main()
      },
      onFrame = {
        delta ->
        main!!.onFrame(delta)
      }
    ).run()
  }

}
