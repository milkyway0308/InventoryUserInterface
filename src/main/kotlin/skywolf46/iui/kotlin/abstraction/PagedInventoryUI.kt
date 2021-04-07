package skywolf46.iui.kotlin.abstraction

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
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
        nextPageListener(slot, { true }, block)
    }


    fun closePage(pair: PagedPlayerInventoryPair.(InventoryCloseEvent) -> Unit) {
        close {
            pair(PagedPlayerInventoryPair(it.player, inventory), this)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun nextPageListener(
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
        previousPageListener(slot, { true }, block)
    }

    fun pageUpdate(slot: Int, block: PagedPlayerInventoryPair.() -> ItemStack) {
        update(slot) {
            block(PagedPlayerInventoryPair(player, inventory))
        }
    }

    fun pageClick(slot: Int, block: PagedPlayerInventoryPair.(InventoryClickEvent) -> Unit) {
        click(slot) {
            block(PagedPlayerInventoryPair(whoClicked as Player, inventory), this)
        }
    }


    fun pageInnerClick(slot: Int, block: PagedPlayerInventoryPair.(InventoryClickEvent) -> Unit) {
        innerClick(slot) {
            block(PagedPlayerInventoryPair(whoClicked as Player, inventory), this)
        }
    }


    fun pageOuterClick(slot: Int, block: PagedPlayerInventoryPair.(InventoryClickEvent) -> Unit) {
        outerClick(slot) {
            block(PagedPlayerInventoryPair(whoClicked as Player, inventory), this)
        }
    }


    fun pageOuterDrag(slot: Int, block: PagedPlayerInventoryPair.(InventoryDragEvent) -> Unit) {
        outerDrag(slot) {
            block(PagedPlayerInventoryPair(whoClicked as Player, inventory), this)
        }
    }

    fun pageInnerDrag(slot: Int, block: PagedPlayerInventoryPair.(InventoryDragEvent) -> Unit) {
        innerDrag(slot) {
            block(PagedPlayerInventoryPair(whoClicked as Player, inventory), this)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun previousPageListener(
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