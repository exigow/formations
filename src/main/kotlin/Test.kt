import org.apache.commons.math3.random.MersenneTwister

object Test {

  @JvmStatic fun main(args: Array<String>) {


    for (i in 0..128) {
      val t = Math.abs(MersenneTwister(i).nextInt()) % 4
      //val r = Random.randomize(i.toLong()) % 16
      println(t)

    }


  }

}