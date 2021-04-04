package skywolf46.iui.util

import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.block.Block
import org.bukkit.block.PistonMoveReaction
import org.bukkit.entity.*
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause
import org.bukkit.inventory.*
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import skywolf46.iui.kotlin.abstraction.IDataStore
import skywolf46.iui.kotlin.abstraction.InventoryUI
import java.util.*
import kotlin.collections.HashMap

operator fun <T : Any> Inventory.get(str: String): T? {
    return EmptyViewer.from(this)?.get(str)
}

operator fun <T : Any> Inventory.set(str: String, data: T): T? {
    EmptyViewer.from(this)?.set(str, data)
    return data
}

 fun <T : Any> Inventory.remove(str: String): T? {
    return EmptyViewer.from(this)?.remove(str)
}

class EmptyViewer(var uI: InventoryUI) : HumanEntity, IDataStore {
    private val uid = UUID.randomUUID()
    private val dataMap = HashMap<String, Any>()

    override fun has(str: String): Boolean = dataMap.containsKey(str)

    @Suppress("UNCHECKED_CAST")

    override operator fun <T : Any> get(str: String): T? = dataMap[str] as T?

    override operator fun <T : Any> set(str: String, data: T): T {
        dataMap[str] = data
        return data
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> remove(str: String): T? = dataMap.remove(str) as T?

    override fun getName(): String {
        return "FakeViewer"
    }

    override fun getInventory(): PlayerInventory? {
        return null
    }

    override fun getEnderChest(): Inventory? {
        return null
    }

    override fun getMainHand(): MainHand? {
        return null
    }

    override fun setWindowProperty(property: InventoryView.Property, i: Int): Boolean {
        return false
    }

    override fun getOpenInventory(): InventoryView? {
        return null
    }

    override fun openInventory(inventory: Inventory): InventoryView? {
        return null
    }

    override fun openWorkbench(location: Location, b: Boolean): InventoryView? {
        return null
    }

    override fun openEnchanting(location: Location, b: Boolean): InventoryView? {
        return null
    }

    override fun openInventory(inventoryView: InventoryView) {}
    override fun openMerchant(villager: Villager, b: Boolean): InventoryView? {
        return null
    }

    override fun openMerchant(merchant: Merchant, b: Boolean): InventoryView? {
        return null
    }

    override fun closeInventory() {}
    override fun getItemInHand(): ItemStack? {
        return null
    }

    override fun setItemInHand(itemStack: ItemStack) {}
    override fun getItemOnCursor(): ItemStack? {
        return null
    }

    override fun setItemOnCursor(itemStack: ItemStack) {}
    override fun hasCooldown(material: Material): Boolean {
        return false
    }

    override fun getCooldown(material: Material): Int {
        return 0
    }

    override fun setCooldown(material: Material, i: Int) {}
    override fun isSleeping(): Boolean {
        return false
    }

    override fun getSleepTicks(): Int {
        return 0
    }

    override fun getGameMode(): GameMode? {
        return null
    }

    override fun setGameMode(gameMode: GameMode) {}
    override fun isBlocking(): Boolean {
        return false
    }

    override fun isHandRaised(): Boolean {
        return false
    }

    override fun getExpToLevel(): Int {
        return 0
    }

    override fun getShoulderEntityLeft(): Entity? {
        return null
    }

    override fun setShoulderEntityLeft(entity: Entity) {}
    override fun getShoulderEntityRight(): Entity? {
        return null
    }

    override fun setShoulderEntityRight(entity: Entity) {}
    override fun getEyeHeight(): Double {
        return .0
    }

    override fun getEyeHeight(b: Boolean): Double {
        return .0
    }

    override fun getEyeLocation(): Location? {
        return null
    }

    override fun getLineOfSight(set: Set<Material>, i: Int): List<Block>? {
        return null
    }

    override fun getTargetBlock(set: Set<Material>, i: Int): Block? {
        return null
    }

    override fun getLastTwoTargetBlocks(set: Set<Material>, i: Int): List<Block>? {
        return null
    }

    override fun getRemainingAir(): Int {
        return 0
    }

    override fun setRemainingAir(i: Int) {}
    override fun getMaximumAir(): Int {
        return 0
    }

    override fun setMaximumAir(i: Int) {}
    override fun getMaximumNoDamageTicks(): Int {
        return 0
    }

    override fun setMaximumNoDamageTicks(i: Int) {}
    override fun getLastDamage(): Double {
        return .0
    }

    override fun setLastDamage(v: Double) {}
    override fun getNoDamageTicks(): Int {
        return 0
    }

    override fun setNoDamageTicks(i: Int) {}
    override fun getKiller(): Player? {
        return null
    }

    override fun addPotionEffect(potionEffect: PotionEffect): Boolean {
        return false
    }

    override fun addPotionEffect(potionEffect: PotionEffect, b: Boolean): Boolean {
        return false
    }

    override fun addPotionEffects(collection: Collection<PotionEffect>): Boolean {
        return false
    }

    override fun hasPotionEffect(potionEffectType: PotionEffectType): Boolean {
        return false
    }

    override fun getPotionEffect(potionEffectType: PotionEffectType): PotionEffect? {
        return null
    }

    override fun removePotionEffect(potionEffectType: PotionEffectType) {}
    override fun getActivePotionEffects(): Collection<PotionEffect>? {
        return null
    }

    override fun hasLineOfSight(entity: Entity): Boolean {
        return false
    }

    override fun getRemoveWhenFarAway(): Boolean {
        return false
    }

    override fun setRemoveWhenFarAway(b: Boolean) {}
    override fun getEquipment(): EntityEquipment? {
        return null
    }

    override fun setCanPickupItems(b: Boolean) {}
    override fun getCanPickupItems(): Boolean {
        return false
    }

    override fun isLeashed(): Boolean {
        return false
    }

    @Throws(IllegalStateException::class)
    override fun getLeashHolder(): Entity? {
        return null
    }

    override fun setLeashHolder(entity: Entity): Boolean {
        return false
    }

    override fun isGliding(): Boolean {
        return false
    }

    override fun setGliding(b: Boolean) {}
    override fun setAI(b: Boolean) {}
    override fun hasAI(): Boolean {
        return false
    }

    override fun setCollidable(b: Boolean) {}
    override fun isCollidable(): Boolean {
        return false
    }

    override fun getAttribute(attribute: Attribute): AttributeInstance? {
        return null
    }

    override fun damage(v: Double) {}
    override fun damage(v: Double, entity: Entity) {}
    override fun getHealth(): Double {
        return .0
    }

    override fun setHealth(v: Double) {}
    override fun getMaxHealth(): Double {
        return .0
    }

    override fun setMaxHealth(v: Double) {}
    override fun resetMaxHealth() {}
    override fun getLocation(): Location? {
        return null
    }

    override fun getLocation(location: Location): Location? {
        return null
    }

    override fun setVelocity(vector: Vector) {}
    override fun getVelocity(): Vector? {
        return null
    }

    override fun getHeight(): Double {
        return .0
    }

    override fun getWidth(): Double {
        return .0
    }

    override fun isOnGround(): Boolean {
        return false
    }

    override fun getWorld(): World? {
        return null
    }

    override fun teleport(location: Location): Boolean {
        return false
    }

    override fun teleport(location: Location, teleportCause: TeleportCause): Boolean {
        return false
    }

    override fun teleport(entity: Entity): Boolean {
        return false
    }

    override fun teleport(entity: Entity, teleportCause: TeleportCause): Boolean {
        return false
    }

    override fun getNearbyEntities(v: Double, v1: Double, v2: Double): List<Entity>? {
        return null
    }

    override fun getEntityId(): Int {
        return 0
    }

    override fun getFireTicks(): Int {
        return 0
    }

    override fun getMaxFireTicks(): Int {
        return 0
    }

    override fun setFireTicks(i: Int) {}
    override fun remove() {}
    override fun isDead(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return false
    }

    override fun sendMessage(s: String) {}
    override fun sendMessage(strings: Array<String>) {}
    override fun getServer(): Server? {
        return null
    }

    override fun getPassenger(): Entity? {
        return null
    }

    override fun setPassenger(entity: Entity): Boolean {
        return false
    }

    override fun getPassengers(): List<Entity>? {
        return null
    }

    override fun addPassenger(entity: Entity): Boolean {
        return false
    }

    override fun removePassenger(entity: Entity): Boolean {
        return false
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun eject(): Boolean {
        return false
    }

    override fun getFallDistance(): Float {
        return 0f
    }

    override fun setFallDistance(v: Float) {}
    override fun setLastDamageCause(entityDamageEvent: EntityDamageEvent) {}
    override fun getLastDamageCause(): EntityDamageEvent? {
        return null
    }

    override fun getUniqueId(): UUID {
        return uid
    }

    override fun getTicksLived(): Int {
        return 0
    }

    override fun setTicksLived(i: Int) {}
    override fun playEffect(entityEffect: EntityEffect) {}
    override fun getType(): EntityType? {
        return null
    }

    override fun isInsideVehicle(): Boolean {
        return false
    }

    override fun leaveVehicle(): Boolean {
        return false
    }

    override fun getVehicle(): Entity? {
        return null
    }

    override fun setCustomNameVisible(b: Boolean) {}
    override fun isCustomNameVisible(): Boolean {
        return false
    }

    override fun setGlowing(b: Boolean) {}
    override fun isGlowing(): Boolean {
        return false
    }

    override fun setInvulnerable(b: Boolean) {}
    override fun isInvulnerable(): Boolean {
        return false
    }

    override fun isSilent(): Boolean {
        return false
    }

    override fun setSilent(b: Boolean) {}
    override fun hasGravity(): Boolean {
        return false
    }

    override fun setGravity(b: Boolean) {}
    override fun getPortalCooldown(): Int {
        return 0
    }

    override fun setPortalCooldown(i: Int) {}
    override fun getScoreboardTags(): Set<String>? {
        return null
    }

    override fun addScoreboardTag(s: String): Boolean {
        return false
    }

    override fun removeScoreboardTag(s: String): Boolean {
        return false
    }

    override fun getPistonMoveReaction(): PistonMoveReaction? {
        return null
    }

    override fun spigot(): Entity.Spigot? {
        return null
    }

    override fun getCustomName(): String? {
        return null
    }

    override fun setCustomName(s: String) {}
    override fun setMetadata(s: String, metadataValue: MetadataValue) {}
    override fun getMetadata(s: String): List<MetadataValue>? {
        return null
    }

    override fun hasMetadata(s: String): Boolean {
        return false
    }

    override fun removeMetadata(s: String, plugin: Plugin) {}
    override fun isPermissionSet(s: String): Boolean {
        return false
    }

    override fun isPermissionSet(permission: Permission): Boolean {
        return false
    }

    override fun hasPermission(s: String): Boolean {
        return false
    }

    override fun hasPermission(permission: Permission): Boolean {
        return false
    }

    override fun addAttachment(plugin: Plugin, s: String, b: Boolean): PermissionAttachment? {
        return null
    }

    override fun addAttachment(plugin: Plugin): PermissionAttachment? {
        return null
    }

    override fun addAttachment(plugin: Plugin, s: String, b: Boolean, i: Int): PermissionAttachment? {
        return null
    }

    override fun addAttachment(plugin: Plugin, i: Int): PermissionAttachment? {
        return null
    }

    override fun removeAttachment(permissionAttachment: PermissionAttachment) {}
    override fun recalculatePermissions() {}
    override fun getEffectivePermissions(): Set<PermissionAttachmentInfo>?? {
        return null
    }

    override fun isOp(): Boolean {
        return false
    }

    override fun setOp(b: Boolean) {}
    override fun <T : Projectile?> launchProjectile(aClass: Class<out T>): T? {
        return null
    }

    override fun <T : Projectile?> launchProjectile(aClass: Class<out T>, vector: Vector): T? {
        return null
    }

    companion object {
        fun from(inv: Inventory): EmptyViewer? {
            for (he in inv.viewers) if (he is EmptyViewer) return he
            return null
        }
    }
}