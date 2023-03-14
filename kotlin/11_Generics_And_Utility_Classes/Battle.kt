import kotlin.random.Random
import kotlin.random.nextInt

class Battle(var teamFirst:Team, var teamSecond: Team) {
    private var battleIsOver = false

    private fun nameOfTeam(team: Team):String{
        return when(team){
            teamFirst -> "First team"
            else -> "Second team"
        }
    }

    private fun getBattleState(): BattleState{
        return if (teamFirst.team.size == 0 && teamSecond.team.size == 0){
            BattleState.Draw
        } else if (teamFirst.team.size > 0 && teamSecond.team.size == 0){
            BattleState.FirstWon
        } else if (teamSecond.team.size > 0 && teamFirst.team.size == 0){
            BattleState.SecondWon
        } else{
            BattleState.Progress.counting(teamFirst,teamSecond)
            BattleState.Progress
        }
    }

    private fun iterationForTeam(team1: Team, team2: Team){
        println("${nameOfTeam(team1)}'s move")
        for (i in team1.team.indices.reversed()){
            val rand = Random.nextInt(0..team2.team.lastIndex)
            println("${team1.team[i]} is attacking ${team2.team[rand]}")
            team1.team[i].attack(team2.team[rand])
            println("His health is ${team2.team[rand].health}")
            if (team2.team[rand].isKilled) {
                println("${team2.team[rand]} is died")
                team2.team.remove(team2.team[rand])
            }
            if (getBattleState() != BattleState.Progress){
                getBattleState()
                battleIsOver = true
                break
            }
            println()
        }
    }

    fun iteration(){
        println(nameOfTeam(teamFirst))
        teamFirst.team.forEach { println("$it from First team has weapon ${it.weapon.name}") }
        println()
        println(nameOfTeam(teamSecond))
        teamSecond.team.forEach { println("$it from Second team has weapon ${it.weapon.name}")}
        println()
        println("Battle is starting")
        getBattleState()
        while (!battleIsOver){
            println()
            iterationForTeam(teamFirst,teamSecond)
            if (getBattleState() != BattleState.Progress){ // Эта проверка не лишняя, без неё пишется лишний текст в случае победы первой команды
                getBattleState()
                battleIsOver = true
                break
            }
            println()
            iterationForTeam(teamSecond,teamFirst)
        }
    }
}