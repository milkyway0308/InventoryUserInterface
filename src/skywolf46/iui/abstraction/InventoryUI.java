package skywolf46.iui.abstraction;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import skywolf46.iui.data.ItemSlot;
import skywolf46.iui.util.EmptyViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class InventoryUI {
    private List<InventoryUI> subInventory = new ArrayList<>();
    private List<String> inventoryIndex = new ArrayList<>();
    private List<ItemSlotHandler> globalHandler = new ArrayList<>();
    private HashMap<Integer, List<ItemSlotHandler>> handler = new HashMap<>();
    private HashMap<Integer, ItemSlot> slots = new HashMap<>();
    private InventoryUI parent = null;
    private boolean init = false;

    public InventoryUI add(String name, InventoryUI ui) {
        if (!ui.isSubInventory())
            throw new IllegalStateException();
        if (inventoryIndex.contains(name)) {
            throw new IllegalStateException();
        }
        inventoryIndex.add(name);
        subInventory.add(ui);
        return initUI(ui);
    }

    public InventoryUI addFirst(String name, InventoryUI ui) {
        if (!ui.isSubInventory())
            throw new IllegalStateException();
        if (inventoryIndex.contains(name)) {
            throw new IllegalStateException();
        }
        inventoryIndex.add(0, name);
        subInventory.add(0, ui);
        return initUI(ui);
    }

    private InventoryUI initUI(InventoryUI ui) {
        ui.parent = this;
        deinitialize();
        for (InventoryUI uix : subInventory)
            uix.deinitialize();
        getParent().init();
        return this;
    }

    private void init() {
        init = true;
        initialize();
        for (InventoryUI ui : subInventory)
            ui.init();
    }

    private void deinitialize() {
        globalHandler.clear();
        handler.clear();
        slots.clear();
    }

    public InventoryUI addBefore(String index, String name, InventoryUI ui) {
        return appendInventory(name, ui, inventoryIndex.indexOf(name));
    }

    public InventoryUI addAfter(String index, String name, InventoryUI ui) {
        return appendInventory(name, ui, inventoryIndex.indexOf(name) + 1);
    }

    private InventoryUI appendInventory(String name, InventoryUI ui, int slot) {
        if (!ui.isSubInventory())
            throw new IllegalStateException();
        if (!inventoryIndex.contains(name)) {
            throw new IllegalStateException();
        }
        inventoryIndex.add(slot, name);
        subInventory.add(slot, ui);
        return initUI(ui);
    }

    public InventoryUI addListener(ItemSlotHandler handler) {
        getParent().globalHandler.add(handler);
        return this;
    }

    public InventoryUI addListener(int slot, ItemSlotHandler handler) {
        getParent().handler.computeIfAbsent(slot, a -> new ArrayList<>()).add(handler);
        return this;
    }

    public InventoryUI onClick(InventoryClickEvent e, EmptyViewer ev) {
        boolean isPlayerInventory = e.getRawSlot() >= e.getInventory().getSize();
        ItemSlot sl = isPlayerInventory ? null : getRawSlot(e.getRawSlot());
        int base = e.getRawSlot();
        for (ItemSlotHandler is : globalHandler) {
            is.handle(e, ev.getDataMap(), sl, base);
            if (is.stopEventProcess())
                return this;
        }
        if (handler.containsKey(base))
            for (ItemSlotHandler is : handler.get(base)) {
                is.handle(e, ev.getDataMap(), sl, base);
                if (is.stopEventProcess())
                    return this;
            }
//        for (InventoryUI ui : subInventory)
//            ui.onClick(e, ev);
        return this;
    }

    private ItemSlot getRawSlot(int rawSlot) {
        return this.parent != null ? this.parent.getSlot(rawSlot) : this.slots.get(rawSlot);
    }

    public InventoryUI onDrag(InventoryDragEvent e, EmptyViewer evp) {
        if (e.getInventorySlots().size() == 1) {
            InventoryClickEvent ev = new InventoryClickEvent(e.getView(), InventoryType.SlotType.CONTAINER, e.getRawSlots().iterator().next(), ClickType.LEFT, InventoryAction.PLACE_ALL);
            onClick(ev, evp);
        }
        return this;
    }

    public void setSlot(int sl, ItemSlot dat) {
        if (sl <= -1)
            return;
        getParent().slots.put(sl, dat);
    }

    public void setSlotFromUI(int sl, ItemSlot dat) {
        setSlot(getUIBasedSlot(sl), dat);
    }

    public ItemSlot getSlot(int slot) {
        return this.parent != null ? this.parent.getSlot(slot) : this.slots.computeIfAbsent(slot, s -> new ItemSlot(this, s));
    }

    public ItemSlot getSlotFromUI(int slot) {
        return this.parent != null ? this.parent.getSlot(getUIBasedSlot(slot)) : this.slots.computeIfAbsent(getUIBasedSlot(slot), s -> new ItemSlot(this, s));
    }

    public InventoryUI bindInventory(Inventory inv) {
        if (parent != null)
            throw new IllegalStateException();
        for (HumanEntity he : inv.getViewers())
            if (he instanceof EmptyViewer) {
                EmptyViewer ev = (EmptyViewer) he;
                ev.setUI(this);
                bind(inv, ev);
                return this;
            }
        EmptyViewer ev = new EmptyViewer(this);
        inv.getViewers().add(ev);
        bind(inv, ev);
        return this;
    }

    private void bind(Inventory inv, EmptyViewer ev) {
        onBind(inv, ev.getDataMap());
        for (InventoryUI ui : subInventory)
            ui.bind(inv, ev);
    }

    public InventoryUI updateInventory(Player p, Inventory inv) {
        if (!getParent().init)
            init();
        getParent().update(p, inv);
        return this;
    }

    private void update(Player p, Inventory inv) {
        EmptyViewer ev = EmptyViewer.from(inv);

        for (ItemSlot is : getParent().slots.values())
            is.updateToInventory(p, ev, inv);
    }

    protected final InventoryUI getParent() {
        return parent == null ? this : parent.getParent();
    }

    public abstract int getUIBasedSlot(int rawSlot);

    public abstract boolean isSubInventory();

    public abstract void onBind(Inventory inv, HashMap<String, Object> dataMap);

    public abstract void initialize();


}
