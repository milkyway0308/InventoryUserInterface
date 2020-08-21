package skywolf46.iui.data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import skywolf46.iui.abstraction.InventoryUI;
import skywolf46.iui.abstraction.ItemProvider;
import skywolf46.iui.util.EmptyViewer;

public class ItemSlot {
    private ItemProvider prov;
    private int invSlot;
    private InventoryUI ui;

    public ItemSlot(InventoryUI ui, int invSlot) {
        this.ui = ui;
        this.invSlot = invSlot;
    }

    public ItemSlot setProvider(ItemProvider prov) {
        this.prov = prov;
        return this;
    }

    public ItemProvider getProvider() {
        return this.prov;
    }

    public InventoryUI getUi() {
        return ui;
    }

    public int getInvSlot() {
        return invSlot;
    }

    public void updateToInventory(Player p, EmptyViewer ev, Inventory inv) {
        ItemStack item = this.prov == null ? new ItemStack(Material.AIR) : this.prov.getItem(p, inv, getUi(), getInvSlot(), ev.getDataMap());
        if (item == null)
            return;
        inv.setItem(this.invSlot, item);
    }
}
