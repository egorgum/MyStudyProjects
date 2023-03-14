import kotlin.random.Random
import kotlin.random.nextInt

class General: AbstractWarrior() {
    override val maxHealth: Int = 35
    override var health: Int = maxHealth
    override val weapon: AbstractWeapon = when (Random.nextInt(0..3)){
        0 -> Weapons.createUzi
        1 -> Weapons.createPistol
        2 -> Weapons.createMachineGun
        else -> Weapons.createRifle
    }
    override val accuracy: Int = 70
    override val miss: Int = 50

}