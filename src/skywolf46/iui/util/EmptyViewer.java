package skywolf46.iui.util;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import skywolf46.iui.abstraction.InventoryUI;

import java.util.*;

public class EmptyViewer implements HumanEntity {
    private UUID uid = UUID.randomUUID();
    private InventoryUI currentUI;
    private HashMap<String,Object> dataMap = new HashMap<>();

    public EmptyViewer(InventoryUI ui) {
        this.currentUI = ui;
    }

    public static EmptyViewer from(Inventory inv){
        for(HumanEntity he : inv.getViewers())
            if(he instanceof EmptyViewer)
                return (EmptyViewer) he;
        return null;
    }

    public void setUI(InventoryUI ui){
        this.currentUI = ui;
    }

    public InventoryUI getUI() {
        return currentUI;
    }

    public HashMap<String, Object> getDataMap() {
        return dataMap;
    }

    @Override
    public String getName() {
        return "FakeViewer";
    }

    @Override
    public PlayerInventory getInventory() {
        return null;
    }

    @Override
    public Inventory getEnderChest() {
        return null;
    }

    @Override
    public MainHand getMainHand() {
        return null;
    }

    @Override
    public boolean setWindowProperty(InventoryView.Property property, int i) {
        return false;
    }

    @Override
    public InventoryView getOpenInventory() {
        return null;
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        return null;
    }

    @Override
    public InventoryView openWorkbench(Location location, boolean b) {
        return null;
    }

    @Override
    public InventoryView openEnchanting(Location location, boolean b) {
        return null;
    }

    @Override
    public void openInventory(InventoryView inventoryView) {

    }

    @Override
    public InventoryView openMerchant(Villager villager, boolean b) {
        return null;
    }

    @Override
    public InventoryView openMerchant(Merchant merchant, boolean b) {
        return null;
    }

    @Override
    public void closeInventory() {

    }

    @Override
    public ItemStack getItemInHand() {
        return null;
    }

    @Override
    public void setItemInHand(ItemStack itemStack) {

    }

    @Override
    public ItemStack getItemOnCursor() {
        return null;
    }

    @Override
    public void setItemOnCursor(ItemStack itemStack) {

    }

    @Override
    public boolean hasCooldown(Material material) {
        return false;
    }

    @Override
    public int getCooldown(Material material) {
        return 0;
    }

    @Override
    public void setCooldown(Material material, int i) {

    }

    @Override
    public boolean isSleeping() {
        return false;
    }

    @Override
    public int getSleepTicks() {
        return 0;
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public void setGameMode(GameMode gameMode) {

    }

    @Override
    public boolean isBlocking() {
        return false;
    }

    @Override
    public boolean isHandRaised() {
        return false;
    }

    @Override
    public int getExpToLevel() {
        return 0;
    }

    @Override
    public Entity getShoulderEntityLeft() {
        return null;
    }

    @Override
    public void setShoulderEntityLeft(Entity entity) {

    }

    @Override
    public Entity getShoulderEntityRight() {
        return null;
    }

    @Override
    public void setShoulderEntityRight(Entity entity) {

    }

    @Override
    public double getEyeHeight() {
        return 0;
    }

    @Override
    public double getEyeHeight(boolean b) {
        return 0;
    }

    @Override
    public Location getEyeLocation() {
        return null;
    }

    @Override
    public List<Block> getLineOfSight(Set<Material> set, int i) {
        return null;
    }

    @Override
    public Block getTargetBlock(Set<Material> set, int i) {
        return null;
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(Set<Material> set, int i) {
        return null;
    }

    @Override
    public int getRemainingAir() {
        return 0;
    }

    @Override
    public void setRemainingAir(int i) {

    }

    @Override
    public int getMaximumAir() {
        return 0;
    }

    @Override
    public void setMaximumAir(int i) {

    }

    @Override
    public int getMaximumNoDamageTicks() {
        return 0;
    }

    @Override
    public void setMaximumNoDamageTicks(int i) {

    }

    @Override
    public double getLastDamage() {
        return 0;
    }

    @Override
    public void setLastDamage(double v) {

    }

    @Override
    public int getNoDamageTicks() {
        return 0;
    }

    @Override
    public void setNoDamageTicks(int i) {

    }

    @Override
    public Player getKiller() {
        return null;
    }

    @Override
    public boolean addPotionEffect(PotionEffect potionEffect) {
        return false;
    }

    @Override
    public boolean addPotionEffect(PotionEffect potionEffect, boolean b) {
        return false;
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> collection) {
        return false;
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType potionEffectType) {
        return false;
    }

    @Override
    public PotionEffect getPotionEffect(PotionEffectType potionEffectType) {
        return null;
    }

    @Override
    public void removePotionEffect(PotionEffectType potionEffectType) {

    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return null;
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return false;
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return false;
    }

    @Override
    public void setRemoveWhenFarAway(boolean b) {

    }

    @Override
    public EntityEquipment getEquipment() {
        return null;
    }

    @Override
    public void setCanPickupItems(boolean b) {

    }

    @Override
    public boolean getCanPickupItems() {
        return false;
    }

    @Override
    public boolean isLeashed() {
        return false;
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        return null;
    }

    @Override
    public boolean setLeashHolder(Entity entity) {
        return false;
    }

    @Override
    public boolean isGliding() {
        return false;
    }

    @Override
    public void setGliding(boolean b) {

    }

    @Override
    public void setAI(boolean b) {

    }

    @Override
    public boolean hasAI() {
        return false;
    }

    @Override
    public void setCollidable(boolean b) {

    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public AttributeInstance getAttribute(Attribute attribute) {
        return null;
    }

    @Override
    public void damage(double v) {

    }

    @Override
    public void damage(double v, Entity entity) {

    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public void setHealth(double v) {

    }

    @Override
    public double getMaxHealth() {
        return 0;
    }

    @Override
    public void setMaxHealth(double v) {

    }

    @Override
    public void resetMaxHealth() {

    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Location getLocation(Location location) {
        return null;
    }

    @Override
    public void setVelocity(Vector vector) {

    }

    @Override
    public Vector getVelocity() {
        return null;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public boolean isOnGround() {
        return false;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public boolean teleport(Location location) {
        return false;
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
        return false;
    }

    @Override
    public boolean teleport(Entity entity) {
        return false;
    }

    @Override
    public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause teleportCause) {
        return false;
    }

    @Override
    public List<Entity> getNearbyEntities(double v, double v1, double v2) {
        return null;
    }

    @Override
    public int getEntityId() {
        return 0;
    }

    @Override
    public int getFireTicks() {
        return 0;
    }

    @Override
    public int getMaxFireTicks() {
        return 0;
    }

    @Override
    public void setFireTicks(int i) {

    }

    @Override
    public void remove() {

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void sendMessage(String s) {

    }

    @Override
    public void sendMessage(String[] strings) {

    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public Entity getPassenger() {
        return null;
    }

    @Override
    public boolean setPassenger(Entity entity) {
        return false;
    }

    @Override
    public List<Entity> getPassengers() {
        return null;
    }

    @Override
    public boolean addPassenger(Entity entity) {
        return false;
    }

    @Override
    public boolean removePassenger(Entity entity) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean eject() {
        return false;
    }

    @Override
    public float getFallDistance() {
        return 0;
    }

    @Override
    public void setFallDistance(float v) {

    }

    @Override
    public void setLastDamageCause(EntityDamageEvent entityDamageEvent) {

    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return null;
    }

    @Override
    public UUID getUniqueId() {
        return uid;
    }

    @Override
    public int getTicksLived() {
        return 0;
    }

    @Override
    public void setTicksLived(int i) {

    }

    @Override
    public void playEffect(EntityEffect entityEffect) {

    }

    @Override
    public EntityType getType() {
        return null;
    }

    @Override
    public boolean isInsideVehicle() {
        return false;
    }

    @Override
    public boolean leaveVehicle() {
        return false;
    }

    @Override
    public Entity getVehicle() {
        return null;
    }

    @Override
    public void setCustomNameVisible(boolean b) {

    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    @Override
    public void setGlowing(boolean b) {

    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public void setInvulnerable(boolean b) {

    }

    @Override
    public boolean isInvulnerable() {
        return false;
    }

    @Override
    public boolean isSilent() {
        return false;
    }

    @Override
    public void setSilent(boolean b) {

    }

    @Override
    public boolean hasGravity() {
        return false;
    }

    @Override
    public void setGravity(boolean b) {

    }

    @Override
    public int getPortalCooldown() {
        return 0;
    }

    @Override
    public void setPortalCooldown(int i) {

    }

    @Override
    public Set<String> getScoreboardTags() {
        return null;
    }

    @Override
    public boolean addScoreboardTag(String s) {
        return false;
    }

    @Override
    public boolean removeScoreboardTag(String s) {
        return false;
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return null;
    }

    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public String getCustomName() {
        return null;
    }

    @Override
    public void setCustomName(String s) {

    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {

    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return null;
    }

    @Override
    public boolean hasMetadata(String s) {
        return false;
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {

    }

    @Override
    public boolean isPermissionSet(String s) {
        return false;
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return false;
    }

    @Override
    public boolean hasPermission(String s) {
        return false;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return false;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        return null;
    }

    @Override
    public void removeAttachment(PermissionAttachment permissionAttachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public void setOp(boolean b) {

    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass) {
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass, Vector vector) {
        return null;
    }
}
