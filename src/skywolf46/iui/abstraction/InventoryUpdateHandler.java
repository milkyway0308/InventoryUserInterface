package skywolf46.iui.abstraction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

@FunctionalInterface
public interface InventoryUpdateHandler {
    void onUpdate(Player p, Inventory inv, InventoryUI ui, HashMap<String, Object> dataMap);
}
