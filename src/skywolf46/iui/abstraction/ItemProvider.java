package skywolf46.iui.abstraction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class ItemProvider {
    public abstract ItemStack getItem(Player p, Inventory inv, InventoryUI ui, int slot, HashMap<String, Object> data);
}
