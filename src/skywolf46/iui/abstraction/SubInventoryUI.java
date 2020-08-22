package skywolf46.iui.abstraction;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public abstract class SubInventoryUI extends InventoryUI {


    @Override
    public boolean isSubInventory() {
        return true;
    }

    @Override
    public void onBind(Inventory inv, HashMap<String, Object> dataMap) {

    }

}
