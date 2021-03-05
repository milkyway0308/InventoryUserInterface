package skywolf46.iui.kotlin.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import skywolf46.extrautility.util.runNonNull
import skywolf46.iui.kotlin.abstraction.getUI

class InventoryListener : Listener {
    @EventHandler
    private fun InventoryClickEvent.onEvent() {
        runNonNull(inventory.getUI()) {
            onClick(this@onEvent)
        }
    }

    @EventHandler
    private fun InventoryDragEvent.onEvent() {
        runNonNull(inventory.getUI()) {
            onClick(this@onEvent)
        }
    }
}