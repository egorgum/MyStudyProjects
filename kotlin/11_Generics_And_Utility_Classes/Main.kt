import kotlin.random.Random

fun main(){
    var teamFirst = Team()
    teamFirst.filling(5)
    var teamSecond = Team()
    teamSecond.filling(7)
    Battle(teamFirst = teamFirst, teamSecond = teamSecond).iteration()
}
fun Int.chance():Boolean{
    return if (this in 0..100){
        when (Random.nextInt(101)){
            in 1..this -> true
            else -> false
        }
    }
    else {
        println("Non-compliance with the limit")
        false
    }
}