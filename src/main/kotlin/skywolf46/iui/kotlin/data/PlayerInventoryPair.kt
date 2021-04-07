package skywolf46.iui.kotlin.data

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import skywolf46.iui.kotlin.abstraction.InventoryUI
import skywolf46.iui.kotlin.abstraction.getUI

data class PlayerInventoryPair(val player: Player, val inventory: Inventory) {
    fun <T : InventoryUI> T.ui(): T = (inventory.getUI() as T?)!!
}