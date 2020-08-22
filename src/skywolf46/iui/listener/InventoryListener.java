package skywolf46.iui.listener;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import skywolf46.iui.util.EmptyViewer;

public class InventoryListener implements Listener {
    @EventHandler
    public void listen(InventoryClickEvent e) {
        for (HumanEntity he : e.getViewers()) {
            if (he instanceof EmptyViewer) {
                ((EmptyViewer) he).getUI().onClick(e, (EmptyViewer) he);
                return;
            }
        }
    }

    @EventHandler
    public void listen(InventoryDragEvent e) {
        for (HumanEntity he : e.getViewers()) {
            if (he instanceof EmptyViewer) {
                ((EmptyViewer) he).getUI().onDrag(e, (EmptyViewer) he);
                return;
            }
        }
    }

    @EventHandler
    public void listen(InventoryCloseEvent e) {
        for (HumanEntity he : e.getViewers()) {
            if (he instanceof EmptyViewer) {
                ((EmptyViewer) he).getUI().onClose(e, (EmptyViewer) he);
                return;
            }
        }
    }
}
