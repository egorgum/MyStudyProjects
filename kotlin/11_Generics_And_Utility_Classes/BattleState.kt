 sealed class BattleState {
    object FirstWon: BattleState(){
        init {
            println("First team won")
        }
    }
     object SecondWon: BattleState(){
         init {
             println("Second team won")
         }
     }
     object Progress: BattleState(){
         fun counting(teamFirst: Team,teamSecond: Team){
             println("In first team ${teamFirst.team.size} persons")
             println("In second team ${teamSecond.team.size} persons")
         }
     }
     object Draw: BattleState(){
         init {
             println("Draw")
         }
     }
}