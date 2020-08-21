package skywolf46.iui.test;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywolf46.iui.abstraction.PageItemProvider;
import skywolf46.iui.impl.PageInventoryUI;

import java.util.HashMap;

public class PrevItemProvider extends PageItemProvider {

    @Override
    public ItemStack getPageItem(Player p, Inventory inv, PageInventoryUI ui, HashMap<String, Object> data, int slot) {
        int page = ui.getPage(data);
        if (page <= 1)
            return null;
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§e< 페이지 §c" + (ui.getPage(data) - 1));
        item.setItemMeta(meta);
        return item;
    }
}
