package skywolf46.iui.impl;

import org.bukkit.inventory.Inventory;
import skywolf46.iui.abstraction.InventoryUI;

import java.util.HashMap;

public class BaseInventoryUI extends InventoryUI {
    @Override
    public int getUIBasedSlot(int rawSlot) {
        return rawSlot;
    }

    @Override
    public int getUISlotAmount() {
        return 0;
    }

    @Override
    public boolean isSubInventory() {
        return false;
    }

    @Override
    public void onBind(Inventory inv, HashMap<String, Object> dataMap) {

    }

    @Override
    public void initialize() {

    }
}
