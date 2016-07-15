package core.formations.graphs


object GraphFactory {

  fun generateCenteredWingsGraph(amount: Int): Node {
    if (amount == 1)
      return Node.empty()
    val headlessAmount = amount - 1
    val leftAmount = headlessAmount / 2
    val rightAmount = headlessAmount - leftAmount;
    println("input = $amount, left = $leftAmount, right = $rightAmount, head = 1")

    return linkRecursive(amount);
  }

  private fun linkRecursive(nTimes: Int): Node = when(nTimes) {
    0 -> Node.empty()
    else -> Node.singleton(linkRecursive(nTimes - 1))
  }

  data class Node(val children: List<Node>) {

    companion object {

      fun empty() = Node(emptyList())

      fun with(vararg children: Node) = Node(children.asList())

      fun singleton(child: Node) = Node(listOf(child))

    }

    fun add(next: Node) = copy(children + next)

    /*override fun toString(): String = when(children.size) {
      0 -> "N"
      else -> "N, ${children.toString()}"
    }*/
  }

}