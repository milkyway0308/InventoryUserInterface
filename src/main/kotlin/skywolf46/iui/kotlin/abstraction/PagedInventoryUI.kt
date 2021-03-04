package skywolf46.iui.kotlin.abstraction

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class PagedInventoryUI(val pageName: String, title: String, invSize: Int) : InventoryUI(title, invSize) {

    override fun modify(pl: Player, inv: Inventory) {
        inv.setValue(pageName, 1)
        super.modify(pl, inv)
    }

    fun getPage(inv: Inventory) = inv.getValue(pageName) ?: 1

    fun setPage(pl: Player, inv: Inventory, page: Int) {
        inv.setValue(pageName, page)
        doUpdate(pl, inv)
    }

    fun nextPage(slot: Int, block: Player.(Inventory) -> ItemStack) {
        nextPage(slot, { true }, block)
    }


    fun nextPage(
        slot: Int,
        clicker: InventoryClickEvent.(Inventory) -> Boolean,
        block: Player.(Inventory) -> ItemStack,
    ) {
        click(slot) {
            isCancelled = true
            if (!clicker(this, it))
                return@click
            with(it.getUI() as PagedInventoryUI) {
                setPage(whoClicked as Player, inventory, this.getPage(it) + 1)
            }
        }
        update(slot, block)
    }


    fun previousPage(slot: Int, block: Player.(Inventory) -> ItemStack) {
        previousPage(slot, { true }, block)
    }


    fun previousPage(
        slot: Int,
        clicker: InventoryClickEvent.(Inventory) -> Boolean,
        block: Player.(Inventory) -> ItemStack,
    ) {
        click(slot) {
            isCancelled = true
            with(it.getUI() as PagedInventoryUI) {
                if(getPage(it) <= 1)
                    return@click
                if (!clicker(this@click, it))
                    return@click
                setPage(whoClicked as Player, inventory, this.getPage(it) + 1)
            }
        }
        update(slot, block)
    }
}