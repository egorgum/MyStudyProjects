import kotlin.random.Random
class Players(n:Int){
    var carts = mutableListOf<Int>()
        init {
        for (i in 1..n) {
            val cart = mutableSetOf<Int>()
            while (cart.size < 15) {
                when (Random.nextInt(9)) {
                    0 -> {
                        if (cart.calculateNorm(1, 10)) {
                            cart.add(Random.nextInt(1, 10))
                        }
                    }
                    1 -> {
                        if (cart.calculateNorm(11, 20)) {
                            cart.add(Random.nextInt(11, 20))
                        }
                    }
                    2 -> {
                        if (cart.calculateNorm(21, 30)) {
                            cart.add(Random.nextInt(21, 30))
                        }
                    }
                    3 -> {
                        if (cart.calculateNorm(31, 40)) {
                            cart.add(Random.nextInt(31, 40))
                        }
                    }
                    4 -> {
                        if (cart.calculateNorm(41, 50)) {
                            cart.add(Random.nextInt(41, 50))
                        }
                    }
                    5 -> {
                        if (cart.calculateNorm(51, 60)) {
                            cart.add(Random.nextInt(51, 60))
                        }
                    }
                    6 -> {
                        if (cart.calculateNorm(61, 70)) {
                            cart.add(Random.nextInt(61, 70))
                        }
                    }
                    7 -> {
                        if (cart.calculateNorm(71, 80)) {
                            cart.add(Random.nextInt(71, 80))
                        }
                    }
                    8 -> {
                        if (cart.calculateNorm(81, 90)) {
                            cart.add(Random.nextInt(81, 90))
                        }
                    }
                }
            }
            carts += cart
        }
    }
}

fun MutableSet<Int>.calculateNorm(f: Int, l: Int):Boolean{
    var i = 0
    this.forEach{if (it in f..l) i++}
    return i <=3
}