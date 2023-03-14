import kotlinx.coroutines.*

fun main() = runBlocking{
    val r = launch { Fibonacci.take(100334534) }
    val w = launch { Fibonacci.take(100) }
    val c = launch { Fibonacci.take(686) }

    launch { while (r.isActive || w.isActive || c.isActive){
        print(".")
        delay(10)
    } }
    println("Program start")
}



object Fibonacci {
    suspend fun take(n: Int){
        currentCoroutineContext().isActive
        var a = 0.toBigInteger()
        var b = 1.toBigInteger()
        var i = 1
        withTimeout(3000){
            try {
                while (i != n){
                    delay(10)
                    val c = a + b
                    a = b
                    b = c
                    i++
                }
            }
            catch (t: TimeoutCancellationException){
                println("Calculation was too long")
            }
        }
        println("At the position \"$n\" - the number \"$a\"")
    }
}