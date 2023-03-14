import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

object Generator {
    val scope =  CoroutineScope(Job() + Dispatchers.Default)
    val set = mutableSetOf<Int>()
    private val generatedBarrelsMut = MutableStateFlow(0)
    val generatedBarrels = generatedBarrelsMut.asStateFlow()

    init {
        scope.launch {
            while (coroutineContext.isActive){
                if (set.size < 90){
                    val a = Random.nextInt(1, 91)
                    if (a !in set){
                        generatedBarrelsMut.value = a
                        set.add(a)
                    }
                    delay(10)
                }
                else{
                    println("empty")
                    scope.cancel()
                }
            }
        }
    }
}