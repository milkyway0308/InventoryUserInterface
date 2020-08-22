package skywolf46.iui.abstraction;


import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import skywolf46.iui.data.ItemSlot;
import skywolf46.iui.util.EmptyViewer;
import skywolf46.iui.util.UnmodifiableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class InventoryUI {
    private List<InventoryUpdateHandler> updator = new ArrayList<>();
    private List<InventoryUI> subInventory = new ArrayList<>();
    private List<String> inventoryIndex = new ArrayList<>();
    private List<ItemSlotHandler> globalHandler = new ArrayList<>();
    private HashMap<Integer, List<ItemSlotHandler>> handler = new HashMap<>();
    private HashMap<Integer, ItemSlot> slots = new HashMap<>();
    private InventoryUI parent = null;
    private boolean init = false;
    private List<Integer> currentSlot = new ArrayList<>();

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
        currentSlot.clear();
    }

    public InventoryUI addBefore(String index, String name, InventoryUI ui) {
        return appendInventory(name, ui, inventoryIndex.indexOf(index));
    }

    public InventoryUI addAfter(String index, String name, InventoryUI ui) {
        return appendInventory(name, ui, inventoryIndex.indexOf(index) + 1);
    }

    public InventoryUI get(String index) {
        int x = inventoryIndex.indexOf(index);
        if (x != -1)
            return subInventory.get(x);
        return null;
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

    public InventoryUI addListener(InventoryUpdateHandler handler) {
        updator.add(0, handler);
        return this;
    }

    public InventoryUI addListener(ItemSlotHandler handler) {
        handler.applyUI(this);
        getParent().globalHandler.add(0, handler);
        return this;
    }

    public InventoryUI addListener(int slot, ItemSlotHandler handler) {
        handler.applyUI(this);
        getParent().handler.computeIfAbsent(slot, a -> new ArrayList<>()).add(0, handler);
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

    public InventoryUI onClose(InventoryCloseEvent e, EmptyViewer ev) {
        for (ItemSlotHandler is : globalHandler) {
            is.handle(e, ev.getDataMap());
            if (is.stopEventProcess())
                return this;
        }
        return this;
    }

    private ItemSlot getRawSlot(int rawSlot) {
        return this.parent != null ? this.parent.getRawSlot(rawSlot) : this.slots.get(rawSlot);
    }

    public InventoryUI onDrag(InventoryDragEvent e, EmptyViewer ev) {
        if (e.getInventorySlots().size() == 1) {
            InventoryClickEvent el = new InventoryClickEvent(e.getView(), InventoryType.SlotType.CONTAINER, e.getRawSlots().iterator().next(), ClickType.LEFT, InventoryAction.PLACE_ALL);
            onClick(el, ev);
            if(el.isCancelled())
                e.setCancelled(true);
        } else {
            List<Integer> raw = new UnmodifiableList<>(new ArrayList<>(e.getRawSlots()));
            List<ItemSlot> iss = new ArrayList<>();
            raw.forEach(re -> {
                if (re >= e.getInventory().getSize()) {
                    iss.add(null);
                } else {
                    iss.add(getSlot(re));
                }
            });
            List<ItemSlot> isl = new UnmodifiableList<>(iss);
            for (ItemSlotHandler is : globalHandler) {
                is.handle(e, ev.getDataMap(), isl, raw, -1);
                if (is.stopEventProcess())
                    return this;
            }
            for (int i = 0; i < raw.size(); i++) {
                if (handler.containsKey(raw.get(i)))
                    for (ItemSlotHandler is : handler.get(raw.get(i))) {
                        is.handle(e, ev.getDataMap(), isl, raw, raw.get(i));
                        if (is.stopEventProcess())
                            return this;
                    }
            }
        }
        return this;
    }

    public void setSlot(int sl, ItemProvider prov) {
        if (sl <= -1)
            return;
        this.currentSlot.add(sl);
        getParent().slots.put(sl, new ItemSlot(this, sl).setProvider(prov));
    }

    public void setSlot(int sl, ItemSlot dat) {
        if (sl <= -1)
            return;
        this.currentSlot.add(sl);
        getParent().slots.put(sl, dat);
    }

    public void setSlotFromUI(int sl, ItemSlot dat) {
        setSlot(getUIBasedSlot(sl), dat);
    }

    public ItemSlot getSlot(int slot) {
        return getSlot(this, slot);
    }


    public ItemSlot getSlotFromUI(int slot) {
        return getSlot(this, slot);
    }

    private ItemSlot getSlot(InventoryUI from, int slot) {
        return this.parent != null ? this.parent.getSlot(slot) : this.slots.computeIfAbsent(slot, s -> {
//            System.out.println("Added " + slot + " to " + from.getClass().getSimpleName());
            from.currentSlot.add(slot);
            return new ItemSlot(this, s);
        });
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

    public InventoryUI updateSingle(Player p, Inventory inv) {
        if (!getParent().init)
            init();
        EmptyViewer ev = EmptyViewer.from(inv);
        handleUpdate(p, inv, ev);
        for (int i : currentSlot)
            getSlot(i).updateToInventory(p, ev, inv);
        return this;
    }

    private void update(Player p, Inventory inv) {
        EmptyViewer ev = EmptyViewer.from(inv);
        handleUpdate(p, inv, ev);

        for (ItemSlot is : getParent().slots.values())
            is.updateToInventory(p, ev, inv);
    }

    private void handleUpdate(Player p, Inventory inv, EmptyViewer ev) {
        for (InventoryUpdateHandler uh : updator)
            uh.onUpdate(p, inv, this, ev.getDataMap());
        for (InventoryUI ui : subInventory)
            ui.handleUpdate(p, inv, ev);
    }

    public final InventoryUI getParent() {
        return parent == null ? this : parent.getParent();
    }

    public int getUIBasedRawSlot(int slot) {
        return 0;
    }

    public int getUIBasedSlot(int slot) {
        return 0;
    }

    public abstract int getUISlotAmount();

    public abstract boolean isSubInventory();

    public abstract void onBind(Inventory inv, HashMap<String, Object> dataMap);

    public abstract void initialize();


}
