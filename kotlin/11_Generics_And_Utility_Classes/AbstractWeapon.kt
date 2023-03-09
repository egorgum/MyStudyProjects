
abstract class AbstractWeapon {
    abstract val  name: String
    abstract  val maxCartridges: Int
    abstract  val fireType: FireType
    var magazine = Stack<Ammo>()
    private var magazineIsEmpty: Boolean = true
    abstract fun makeCartridge(): Ammo
    private fun reloading(){
        val newMagazine = Stack<Ammo>()
        for (i in 0 until maxCartridges){
            newMagazine.push(makeCartridge())
        }
        magazine = newMagazine
        magazineIsEmpty = false
    }
    fun getCartridge(): List<Ammo>{
        val cartridges = mutableListOf<Ammo>()
        if (magazineIsEmpty) {
            reloading()
            println("The player is reloading the weapon")
            return cartridges
        }
        else{
            when(fireType){
                is FireType.SingleShot -> {
                    cartridges.add(makeCartridge())
                    magazine.pop()
                    if (magazine.stack.size == 0){
                        magazineIsEmpty = true
                    }
                }
                is FireType.Burst -> {
                    if((fireType as FireType.Burst).size < magazine.stack.size){
                        for (i in 0..(fireType as FireType.Burst).size){
                            cartridges.add(makeCartridge())
                            magazine.pop()
                        }
                    }
                    else{
                        for (i in 0..magazine.stack.size){
                            cartridges.add(makeCartridge())
                            magazine.pop()
                        }
                        magazineIsEmpty = true
                    }
                }
            }
            return cartridges
        }
    }
}