package skywolf46.iui.test;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywolf46.iui.abstraction.ItemProvider;
import skywolf46.iui.abstraction.PageItemProvider;
import skywolf46.iui.impl.PageInventoryUI;

import java.util.HashMap;

public class NextItemProvider extends PageItemProvider {
    @Override
    public ItemStack getPageItem(Player p, Inventory inv, PageInventoryUI ui, HashMap<String, Object> data, int slot) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§e페이지 §c" + (ui.getPage(data) + 1) + " §e>");
        item.setItemMeta(meta);
        ui.addSlot(0, new NextItemProvider());
        ui.addSlot(2, new NextItemProvider());
        ui.addSlot(4, new NextItemProvider());
        ui.getSlotFromUI(0);
        ui.getSlotFromUI(1);
        ui.getSlotFromUI(2);
        return item;
    }
}
