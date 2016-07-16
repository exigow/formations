package core.formations

data class Node(val children: List<Node>) {

  companion object {

    fun empty() = Node(emptyList())

    fun with(vararg children: Node) = Node(children.asList())

    fun singleton(child: Node) = Node(listOf(child))

  }

  //fun add(next: Node) = copy(children + next)

  fun preorder(f: (Node) -> Unit) {
    if (children.isEmpty())
      return;
    f.invoke(this)
    children.forEach {
      it.preorder(f)
    }
  }

  /*override fun toString(): String = when(children.size) {
    0 -> "N"
    else -> "N, ${children.toString()}"
  }*/
}