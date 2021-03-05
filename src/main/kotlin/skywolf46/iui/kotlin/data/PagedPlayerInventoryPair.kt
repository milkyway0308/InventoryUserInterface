package skywolf46.iui.kotlin.data

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import skywolf46.iui.kotlin.abstraction.PagedInventoryUI

class PagedPlayerInventoryPair(player: Player, inventory: Inventory) : PlayerInventoryPair(player, inventory) {
    fun page() = (inventory as PagedInventoryUI).getPage(inventory)
    fun page(page: Int) = (inventory as PagedInventoryUI).setPage(player, inventory, page)
}