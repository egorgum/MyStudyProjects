class Fish(energyArg: Int,
           weightArg: Int,
           maxAgeArg: Int,
           nameArg: String
): Animal(energyArg, weightArg, maxAgeArg, nameArg) {
    override fun movement() {
        super.movement()
        println("Swimming")
    }
    override fun birth():Fish{
        val babyFish = Fish(energyArg = (1..10).random(), weightArg = (1..5).random(), maxAgeArg = this.maxAge, nameArg = this.name)
        println("A new fish, $name, was born. His weight is $weight kg, his energy is $energy. The maximum age is $maxAge years")
        return babyFish
    }
}