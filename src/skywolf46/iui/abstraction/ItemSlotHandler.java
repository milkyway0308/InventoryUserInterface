package skywolf46.iui.abstraction;

import org.bukkit.event.inventory.InventoryClickEvent;
import skywolf46.iui.data.ItemSlot;
import skywolf46.iui.util.EmptyViewer;

import java.util.HashMap;

public abstract class ItemSlotHandler {

    public abstract void handle(InventoryClickEvent e, HashMap<String, Object> dataMap, ItemSlot slot, int uiBaseSlot);

    public boolean stopEventProcess() {
        return true;
    }
}
