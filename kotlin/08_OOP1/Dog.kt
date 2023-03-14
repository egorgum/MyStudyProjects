class Dog(energyArg: Int,
          weightArg: Int,
          maxAgeArg: Int,
          nameArg: String
): Animal(energyArg, weightArg, maxAgeArg, nameArg) {
    override fun movement() {
        super.movement()
        println("Running")
    }
    override fun birth():Dog{
        val babyDog = Dog(energyArg = (1..10).random(), weightArg = (1..5).random(), maxAgeArg = this.maxAge, nameArg = this.name)
        println("A new dog, $name, was born. His weight is $weight kg, his energy is $energy. The maximum age is $maxAge years")
        return babyDog
    }
}