class TV(val brand: String, val model: String, val size: Double) {
    var state = false
        //Доступно только для чтения
        private set
    private var channels = Channels.getRandomChannels()//Недоступно для чтения и установки
    private var volume = 0
    private var channel = channels[0]
    //Функция включения и выключения
    fun onOff() {
        if (!state){
            state = true
            println("The TV $brand $model is On")
        }
        else {state = false
            println("The TV $brand $model is Off")
        }
    }
    //Функция прибавления громкости
    fun volumeUp() {
        if (!state) println("Turn on the TV $brand $model")
        else {
            if (volume + 1 >= maxVolume) {
                volume = maxVolume
                println("volume = $maxVolume")
            } else {
                volume += 1
                println("Volume = ${this.volume}")
            }
        }
    }
    //Функция убавления громкости
    fun volumeDown() {
        if (!state) println("Turn on the TV $brand $model")
        else {
            if (volume - 1 <= minVolume) {
                volume = minVolume
                println("Volume = $minVolume")
            } else {
                volume -= 1
                println("Volume = ${this.volume}")
            }
        }
    }
    //Функция выбора канала
    fun channelSelection() {
        if (!state) {
            state = true
            println("The TV $brand $model is On")
        }
        print("Select a channel: ")
        var n: Int = readLine()?.toIntOrNull() ?: return
        if (n in 0..channels.lastIndex) {
            channel = channels[n - 1]
            println("${channels.indexOf(channel) + 1} - \"${channel}\"")
        }
        else {
            while (n !in 0..channels.lastIndex){
                print("Select another channel: ")
                n = readLine()?.toIntOrNull()?: return
            }
            channel = channels[n - 1]
            println("${channels.indexOf(channel) + 1} - \"${channel}\"")
        }
    }
    //Функция переключения канала вперед
    fun channelSwitchingUp() {
        if (!state) {
            state = true
            println("The TV $brand $model is On")
        }
        channel = if (channels.indexOf(channel) == channels.lastIndex) channels[0]
        else {
            channels[channels.indexOf(channel) + 1]
        }
        println("${channels.indexOf(channel) + 1} - \"${channel}\"")
    }
    //Функция переключения каналов назад
    fun channelSwitchingDown() {
        if (!state) {
            state = true
            println("The TV $brand $model is On")
        }
        channel = if (channels.indexOf(channel) == 0) channels[channels.lastIndex]
        else {
            channels[channels.indexOf(channel) - 1]
        }
        println("${channels.indexOf(channel) + 1} - \"${channel}\"")
    }

    //Значения максимальной и минимальной громкости
    companion object {
        private const val maxVolume = 10
        private const val minVolume = 0
    }
}