package skywolf46.iui.abstraction;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import skywolf46.iui.data.ItemSlot;
import skywolf46.iui.util.EmptyViewer;

import java.util.HashMap;
import java.util.List;

public abstract class ItemSlotHandler {
    private InventoryUI ui;

    public void handle(InventoryClickEvent e, HashMap<String, Object> dataMap, ItemSlot slot, int uiBaseSlot) {

    }

    public void handle(InventoryDragEvent e, HashMap<String, Object> dataMap, List<ItemSlot> slot, List<Integer> uiBaseSlot, int currentSlot) {

    }

    public void handle(InventoryCloseEvent e, HashMap<String, Object> dataMap) {

    }

    public boolean stopEventProcess() {
        return false;
    }

    void applyUI(InventoryUI ui) {
        this.ui = ui;
    }

    public InventoryUI getUI() {
        return ui;
    }
}
