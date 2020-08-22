package skywolf46.iui.abstraction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class PageItemProvider extends ItemProvider {
    @Override
    public ItemStack getItem(Player p, Inventory inv, InventoryUI ui, int slot, HashMap<String, Object> data) {
        return getPageItem(p, inv, (PageInventoryUI) ui, data, slot);
    }

    public abstract ItemStack getPageItem(Player p, Inventory inv, PageInventoryUI ui, HashMap<String, Object> data, int slot);

}
