import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Simulation(private val player_1: Players, private val player_2: Players) {

    private fun nameOfPlayer(player: Players):String{
        return when(player){
             player_1 -> "First player"
            else -> "Second player"
        }
    }

    private fun getSimulationState(): SimulationState{
        return if (player_1.carts.size == 0 && player_2.carts.size == 0){
            SimulationState.Draw

        } else if (player_1.carts.size > 0 && player_2.carts.size == 0){
            SimulationState.FirstWon

        } else if (player_2.carts.size > 0 && player_1.carts.size == 0){
            SimulationState.SecondWon

        } else{
            SimulationState.Progress
        }
    }

    suspend fun iteration() {
        println("${player_1.carts}")
        println("${player_2.carts}")
        if (player_1.carts.size == player_2.carts.size){
            runBlocking {
                launch {
                    Generator.generatedBarrels.collect{
                        println(it)
                        if (it in player_1.carts){
                            println("$it deleted from ${nameOfPlayer(player_1)}'s carts")
                            player_1.carts.removeIf { i-> i == it }
                        }
                        if (it in player_2.carts){
                            println("$it deleted from ${nameOfPlayer(player_2)}'s carts")
                            player_2.carts.removeIf { i-> i == it }
                        }
                        if (getSimulationState() != SimulationState.Progress){
                            println(getSimulationState().name)
                            cancel()
                        }
                    }
                }
            }
        }
        else println("The number of cards is not equal")
    }
}