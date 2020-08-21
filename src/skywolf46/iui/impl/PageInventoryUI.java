package skywolf46.iui.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import skywolf46.iui.abstraction.InventoryUI;
import skywolf46.iui.abstraction.ItemProvider;
import skywolf46.iui.abstraction.ItemSlotHandler;
import skywolf46.iui.data.ItemSlot;
import skywolf46.iui.util.EmptyViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PageInventoryUI extends InventoryUI {

    private List<Integer> slots = new ArrayList<>();
    private List<Integer> prevSlot = new ArrayList<>();
    private List<Integer> nextSlot = new ArrayList<>();
    private String pageName;

    public PageInventoryUI(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public int getUIBasedSlot(int rawSlot) {
        return (prevSlot.contains(rawSlot) ? -3 : (nextSlot.contains(rawSlot) ? -2 : slots.indexOf(rawSlot)));
    }

    @Override
    public boolean isSubInventory() {
        return true;
    }

    public PageInventoryUI addSlot(int slot, ItemProvider pr) {
        System.out.println("addSlot " + slot);
        slots.add(slot);
        super.setSlot(slot, new ItemSlot(this, slot).setProvider(pr));
        return this;
    }


    public PageInventoryUI addNextSlot(int slot, ItemProvider pr) {
        nextSlot.add(slot);
        super.setSlot(slot, new ItemSlot(this, slot).setProvider(pr));
        super.addListener(slot, new ItemSlotHandler() {
            @Override
            public void handle(InventoryClickEvent e, HashMap<String, Object> dataMap, ItemSlot slot, int uiBaseSlot) {
                e.setCancelled(true);
                int page = getPage(dataMap);
                dataMap.put(pageName, page + 1);
                updateInventory((Player) e.getWhoClicked(), e.getInventory());
            }
        });
        return this;
    }

    public PageInventoryUI addPrevSlot(int slot, ItemProvider pr) {
        prevSlot.add(slot);
        super.setSlot(slot, new ItemSlot(this, slot).setProvider(pr));
        super.addListener(slot, new ItemSlotHandler() {
            @Override
            public void handle(InventoryClickEvent e, HashMap<String, Object> dataMap, ItemSlot slot, int uiBaseSlot) {
                e.setCancelled(true);
                int page = getPage(dataMap);
                if (page <= 1)
                    return;
                dataMap.put(pageName, page - 1);
                updateInventory((Player) e.getWhoClicked(), e.getInventory());
            }
        });
        return this;
    }

    public PageInventoryUI setPage(Player p, Inventory inv, int page) {
        HashMap<String, Object> dat = EmptyViewer.from(inv).getDataMap();
        dat.put(pageName, page);
        super.updateInventory(p, inv);
        return this;
    }

    public int getPage(Inventory inv) {
        return (int) EmptyViewer.from(inv).getDataMap().get(pageName);
    }

    public int getPage(EmptyViewer ev) {
        return (int) ev.getDataMap().get(pageName);
    }

    public int getPage(HashMap<String, Object> data) {
        return (int) data.get(pageName);
    }

    @Override
    public void onBind(Inventory inv, HashMap<String, Object> dataMap) {
        dataMap.put(pageName, 1);
    }

    public String getPageName() {
        return pageName;
    }
}
