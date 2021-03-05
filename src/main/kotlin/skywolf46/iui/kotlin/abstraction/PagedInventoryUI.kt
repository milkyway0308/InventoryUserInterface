package skywolf46.iui.kotlin.abstraction

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import skywolf46.iui.kotlin.data.PagedPlayerInventoryPair

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

    fun nextPage(slot: Int, block: PagedPlayerInventoryPair.() -> ItemStack) {
        nextPage(slot, { true }, block)
    }


    @Suppress("UNCHECKED_CAST")
    fun nextPage(
        slot: Int,
        clicker: InventoryClickEvent.(Inventory) -> Boolean,
        block: PagedPlayerInventoryPair.() -> ItemStack,
    ) {
        click(slot) {
            isCancelled = true
            if (!clicker(this, it.inventory))
                return@click
            with(inventory.getUI() as PagedInventoryUI) {
                setPage(whoClicked as Player, inventory, this.getPage(inventory) + 1)
            }
        }
        update(slot) {
            return@update block(PagedPlayerInventoryPair(player, inventory))
        }
    }


    fun previousPage(slot: Int, block: PagedPlayerInventoryPair.() -> ItemStack) {
        previousPage(slot, { true }, block)
    }


    @Suppress("UNCHECKED_CAST")
    fun previousPage(
        slot: Int,
        clicker: InventoryClickEvent.(PagedInventoryUI) -> Boolean,
        block: PagedPlayerInventoryPair.() -> ItemStack,
    ) {
        click(slot) {
            isCancelled = true
            with(it.inventory.getUI() as PagedInventoryUI) {
                if (getPage(it.inventory) <= 1)
                    return@click
                if (!clicker(this@click, this))
                    return@click
                setPage(whoClicked as Player, inventory, this.getPage(it.inventory) + 1)
            }
        }
        update(slot) {
            return@update block(PagedPlayerInventoryPair(player, inventory))
        }
    }
}