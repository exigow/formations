import core.formations.graphs.GraphFactory
import org.testng.annotations.Test


class Test {

  @Test
  fun test() {
    val result = GraphFactory.generateCenteredWingsGraph(5);
    println(result)
  }

}