sealed class FireType{
    object SingleShot: FireType()
    data class Burst(var size:Int): FireType()
}
