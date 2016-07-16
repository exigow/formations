package core.formations

import core.formations.GraphFactory.centeredWings
import core.formations.GraphFactory.queue
import org.testng.Assert.assertEquals
import org.testng.annotations.Test


class GraphFactoryTest {

  @Test
  fun centeredWingsNodes() {
    assertEquals(centeredWings(1), leaf())
    assertEquals(centeredWings(2), nodes(leaf()))
    assertEquals(centeredWings(3), nodes(leaf(), leaf()))
    assertEquals(centeredWings(4), nodes(leaf(), nodes(leaf())))
    assertEquals(centeredWings(5), nodes(nodes(leaf()), nodes(leaf())))
  }

  @Test
  fun queueNodes() {
    assertEquals(queue(1), leaf())
    assertEquals(queue(2), nodes(leaf()))
    assertEquals(queue(3), nodes(nodes(leaf())))
    assertEquals(queue(4), nodes(nodes(nodes(leaf()))))
  }

  private fun leaf() = Node.empty()

  private fun nodes(vararg children: Node) = Node(children.asList())

}