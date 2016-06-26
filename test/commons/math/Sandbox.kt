package commons.math

import org.testng.annotations.Test
import rendering.shapes.ShapeFactory

class Sandbox {

  @Test fun asd() {

    println("square " + ShapeFactory.square())
    println("rectangle " + ShapeFactory.rectangle(Vec2(32f, 8f)))
    println("diamond " + ShapeFactory.diamond())
    println("diamond scaled " + ShapeFactory.diamond(Vec2(32, 64)))
    println("cross " + ShapeFactory.cross())

  }

}