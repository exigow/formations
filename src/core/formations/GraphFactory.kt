package core.formations


object GraphFactory {

  /**
   * 1: 1
   * 2: 1-2
   * 3: 3-1-2
   * n: ...-5-3-1-2-4-...
   */
  fun centeredWings(amount: Int): Node = when(amount) {
    1 -> Node.empty()
    2 -> linkRecursively(1)
    else -> {
      val headlessAmount = amount - 1
      val leftAmount = headlessAmount / 2
      val rightAmount = headlessAmount - leftAmount;
      val leftWing = queue(leftAmount)
      val rightWing = queue(rightAmount)
      Node.with(leftWing, rightWing);
    }
  }

  /**
   * 1: 1
   * 2: 1-2
   * 3: 1-2-3
   * n: 1-2-3-4-5-...
   */
  fun queue(amount: Int): Node = linkRecursively(amount - 1)

  private fun linkRecursively(nTimes: Int): Node = when(nTimes) {
    0 -> Node.empty()
    else -> Node.singleton(linkRecursively(nTimes - 1))
  }

}