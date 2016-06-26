package commons.math

import org.testng.annotations.Test
import rendering.shapes.ShapesFactory

class Sandbox {

  @Test fun asd() {

    println("square " + ShapesFactory.square())
    println("rectangle " + ShapesFactory.rectangle(Vec2(32f, 8f)))
    println("diamond " + ShapesFactory.diamond())
    println("diamond scaled " + ShapesFactory.diamond(Vec2(32, 64)))



  }

}