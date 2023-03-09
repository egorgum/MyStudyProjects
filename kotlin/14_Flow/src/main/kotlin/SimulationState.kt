import kotlinx.coroutines.cancel

sealed class SimulationState {
    abstract val name: String
    object FirstWon: SimulationState(){
        override var name = "First won"
        init {
            Generator.scope.cancel()
        }
    }

    object SecondWon: SimulationState(){
        override var name = "Second won"
        init {
            Generator.scope.cancel()
        }
    }

    object Draw: SimulationState(){
        override var name = "Draw"
        init {
            Generator.scope.cancel()
        }
    }

    object Progress: SimulationState(){
        override var name = "Progress"
    }


}