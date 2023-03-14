fun main(){
    val wheel = Wheel()
    wheel.pump(2.0)
    wheel.pump(6.0)
    wheel.pump(11.0)
    println(wheel.realPressure)
}