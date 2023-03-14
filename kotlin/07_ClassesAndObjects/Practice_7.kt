fun main(){
    val lg = TV("Lg","first",5.0)
    val samsung = TV("Samsung","F10",10.0)

    println("Brand - ${lg.brand},  model -${lg.model}, size -${lg.size}")
    println(lg.state)
    lg.volumeUp()
    lg.onOff()
    for (i in 1..11){
        lg.volumeUp()
    }
    lg.onOff()
    lg.channelSelection()
    lg.onOff()
    for (i in 1..11){
        lg.channelSwitchingUp()
    }
    println("\nBrand - ${samsung.brand},  model -${samsung.model}, size -${samsung.size}")
    samsung.onOff()
    for (i in 1..11){
        samsung.volumeUp()
    }
    for (i in 1..11){
        samsung.volumeDown()
    }
    for (i in 1..11){
        samsung.channelSwitchingDown()
    }

}