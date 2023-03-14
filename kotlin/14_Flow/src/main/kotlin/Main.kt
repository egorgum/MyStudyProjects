suspend fun main(){
    val firstPlayer = Players(2)
    val secondPlayer = Players(2)
    Simulation(firstPlayer, secondPlayer).iteration()
}