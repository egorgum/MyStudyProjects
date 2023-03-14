class Wheel{
    var realPressure: Double = 0.00
    fun pump(i: Double){
        try {
            if ( realPressure + i > 10 || i <=0){
                throw IncorrectPressure()
            }
            else realPressure += i
            checking(realPressure)
            println("When pumping $i atmospheres, the procedure was successful. Operation is possible")
        }
        catch (t: IncorrectPressure){
            println("When pumping $i atmospheres, the procedure failed")
        }
        catch (t: TooHighPressure){
            println("When pumping $i atmospheres, the procedure was successful. Operation is impossible, the pressure exceeds normal")
        }
        catch (t: TooLowPressure){
            println("When pumping $i atmospheres, the procedure was successful. Operation is impossible, the pressure is below normal")
        }
}

    private fun checking(pressure: Double){
        if (pressure < 1.6){
            throw TooLowPressure()
        }
        if (pressure > 2.5){
            throw TooHighPressure()
        }
    }

}