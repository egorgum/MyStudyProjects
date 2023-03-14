enum class Ammo(
    private var damage: Int,
    private val criticalDamageChance: Int,
    private val criticalDamageCoefficient: Int) {
    PISTOL_CARTRIDGE(2, 30, 5),
    MACHINE_GUN_CARTRIDGE(2,20, 4),
    RIFLE_CARTRIDGE(3, 10, 4);
    fun realDamage(): Int{
        val criticalDamage = damage * criticalDamageCoefficient
        return if (criticalDamageChance.chance()) criticalDamage
        else damage
    }
}