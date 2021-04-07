package skywolf46.iui.kotlin.data

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import skywolf46.iui.kotlin.abstraction.InventoryUI
import skywolf46.iui.kotlin.abstraction.PagedInventoryUI
import skywolf46.iui.kotlin.abstraction.getUI

data class PagedPlayerInventoryPair(val player: Player, val inventory: Inventory) {
    fun page() = (inventory.getUI() as PagedInventoryUI).getPage(inventory)
    fun page(page: Int) = (inventory.getUI() as PagedInventoryUI).setPage(player, inventory, page)
    fun pageStart(itemPerPage: Int) = (page() - 1) * itemPerPage
    fun pageEnd(itemPerPage: Int) = page() * itemPerPage
    fun pageRange(itemPerPage: Int) = (page() - 1) * itemPerPage..page() * itemPerPage
    fun <T : InventoryUI> T.ui(): T = (inventory.getUI() as T?)!!

}