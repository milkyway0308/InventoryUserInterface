package skywolf46.iui.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywolf46.iui.abstraction.InventoryUI;
import skywolf46.iui.abstraction.ItemProvider;

import java.util.*;

public class ItemCreator extends ItemProvider {
    private Material material;
    private int durability = 0;
    private int amount = 1;
    private CreatorMeta meta = null;

    public ItemCreator(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemCreator setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public int getDurability() {
        return durability;
    }

    public ItemCreator setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemCreator setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        if (meta != null) {
            ItemMeta meta = item.getItemMeta();
            this.meta.applyMeta(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    public CreatorMeta getMeta() {
        return (meta == null ? (meta = new CreatorMeta()) : meta);
    }

    @Override
    public ItemStack getItem(Player p, Inventory inv, InventoryUI ui, int slot, HashMap<String, Object> data) {
        return build();
    }

    public class CreatorMeta {
        private String display = null;
        private List<String> lore = null;
        private HashMap<Enchantment, Integer> ench = null;
        private List<ItemFlag> flags = new ArrayList<>();
        private boolean unbreakable = false;

        public ItemCreator item() {
            return ItemCreator.this;
        }

        public ItemStack build() {
            return ItemCreator.this.build();
        }

        public CreatorMeta setDisplayName(String display) {
            this.display = display;
            return this;
        }

        public CreatorMeta addLore(String lore) {
            getLore().add(lore);
            return this;
        }

        public CreatorMeta setLore(List<String> lore) {
            this.lore = lore;
            return this;
        }

        public List<String> getLore() {
            if (lore == null)
                lore = new ArrayList<>();
            return lore;
        }

        public CreatorMeta setEnchant(Enchantment ench, int lv) {
            if (this.ench == null)
                this.ench = new HashMap<>();
            if (lv == 0)
                this.ench.remove(ench);
            else
                this.ench.put(ench, lv);
            return this;
        }

        public CreatorMeta addFlags(ItemFlag... fl) {
            flags.addAll(Arrays.asList(fl));
            return this;
        }

        public CreatorMeta allFlag() {
            flags.addAll(Arrays.asList(ItemFlag.values()));
            return this;
        }

        public CreatorMeta unbreakable(boolean val) {
            this.unbreakable = val;
            return this;
        }

        public void applyMeta(ItemMeta meta) {
            if (display != null)
                meta.setDisplayName(display);
            if (lore != null)
                meta.setLore(lore);
            if (ench != null)
                for (Map.Entry<Enchantment, Integer> ench : this.ench.entrySet())
                    meta.addEnchant(ench.getKey(), ench.getValue(), true);
            if (flags.size() != 0)
                meta.addItemFlags(flags.toArray(new ItemFlag[0]));
            meta.setUnbreakable(unbreakable);
            apply(meta);
        }

        protected void apply(ItemMeta meta) {

        }

    }

    class SkullMeta extends CreatorMeta {
        @Override
        protected void apply(ItemMeta meta) {
            super.apply(meta);
        }
    }
}
