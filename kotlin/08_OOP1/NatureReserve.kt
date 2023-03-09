class NatureReserve {
    var listOfAnimals = mutableListOf(
        Fish(5,5,5,"Fredy"),
        Fish(7,10,9,"Filip"),
        Fish(4,3,6,"Frank"),

        Dog(7,5,6,"Dima"),
        Dog(9,8,4,"Diana"),

        Animal(4,18,10,"Anna") ,
        Animal(3,3,8,"Andrey"),

        Bird(5,3,11,"Brenda"),
        Bird(9,5,5,"Billy"),
        Bird(7,9,3,"Bob"),
        Bird(4,7,6,"Boris"),
        Bird(5,4,9,"Brad"))
    fun life(list: MutableList<Animal>, n: Int){
        var i = n
        while (i > 0){
            for (a in list.indices.reversed()){
                when((1..4).random()){
                    1 -> list[a].sleeping()
                    2 -> list[a].eating()
                    3 -> list[a].movement()
                    4 -> list.add(list[a].birth())
                }
                if (list[a].isTooOld){
                    println("${list[a].name} is dead")
                    list.remove(list[a])
                }
                if (list.size == 0) break
            }
            i -= 1
        }
        println("In NatureReserve ${list.size} animals")
    }
}