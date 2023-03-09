class Bird(energyArg: Int,
           weightArg: Int,
           maxAgeArg: Int,
           nameArg: String
): Animal(energyArg, weightArg, maxAgeArg, nameArg) {
    override fun movement() {
        super.movement()
        println("Flying")
    }
    override fun birth():Bird{
        val babyBird = Bird(energyArg = (1..10).random(), weightArg = (1..5).random(), maxAgeArg = this.maxAge, nameArg = this.name)
        println("A new bird, $name, was born. His weight is $weight kg, his energy is $energy. The maximum age is $maxAge years")
        return babyBird
    }
}