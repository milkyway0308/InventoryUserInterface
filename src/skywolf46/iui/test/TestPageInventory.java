package skywolf46.iui.test;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywolf46.iui.abstraction.InventoryUI;
import skywolf46.iui.abstraction.ItemProvider;
import skywolf46.iui.abstraction.ItemSlotHandler;
import skywolf46.iui.abstraction.PageItemProvider;
import skywolf46.iui.data.ItemSlot;
import skywolf46.iui.impl.PageInventoryUI;

import java.util.HashMap;

public class TestPageInventory extends PageInventoryUI {
    private int prev;
    private int next;

    public TestPageInventory(String pageName, int prev, int next) {
        super(pageName);
        this.prev = prev;
        this.next = next;
    }

    @Override
    public void initialize() {
        addPrevSlot(prev, new PrevItemProvider());
        addNextSlot(next, new NextItemProvider());
        addSlot(0, new PageItemProvider() {
            @Override
            public ItemStack getPageItem(Player p, Inventory inv, PageInventoryUI ui, HashMap<String, Object> data, int slot) {
                ItemStack item = new ItemStack(Material.ARROW);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§e페이지 §c" + (ui.getPage(data) + 1) + " §b슬롯 0");
                item.setItemMeta(meta);
                return item;
            }
        });
    }
}
