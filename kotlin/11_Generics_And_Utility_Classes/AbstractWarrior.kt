abstract class AbstractWarrior: Warrior {
    abstract val maxHealth: Int
    abstract var health: Int
    abstract val weapon: AbstractWeapon
    abstract val accuracy: Int
    override var isKilled: Boolean = false

    fun attack(warrior: AbstractWarrior) {
        if (!warrior.miss.chance() || accuracy.chance()){
            var sum = 0
            weapon.getCartridge().forEach{item -> sum += item.realDamage()}
            warrior.getDamage(sum)
            println("$warrior taken $sum damage")
            if (warrior.health <= 0) {
                warrior.isKilled = true
            }
        }
        else{
            println("Missing")
            println("$warrior taken 0 damage")
        }
    }

    override fun getDamage(damage: Int) {
        health -= damage
    }
}