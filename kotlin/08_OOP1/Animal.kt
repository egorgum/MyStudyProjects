import kotlin.random.Random

open class Animal(var energy: Int, var weight: Int, val maxAge: Int, val name: String) {
    private var age = (1 until maxAge).random()
    var isTooOld: Boolean = age >= maxAge

    fun sleeping(){
        energy += 5
        age += 1
        println("$name is sleeping")
        isTooOld = age >= maxAge
    }


    fun eating(){
        energy += 3
        weight += 1
        age += 1
        tryIncrementAge()
        println("$name is eating")
        isTooOld = age >= maxAge
    }

    open fun movement(){
        if (!isTooOld || weight > 0 || energy >0) {
            energy -= 5
            weight -= 1
            age += 1
            tryIncrementAge()
            println("$name is moving ")
            isTooOld = age >= maxAge
        }
    }
    private fun tryIncrementAge(){
        if(Random.nextBoolean())age +=1
    }

    open fun birth():Animal{
        val babyAnimal = Animal(energy = (1..10).random(), weight = (1..5).random(),maxAge = this.maxAge, name = this.name)
        println("A new animal, $name, was born. His weight is $weight kg, his energy is $energy. The maximum age is $maxAge years")
        return babyAnimal
    }
}