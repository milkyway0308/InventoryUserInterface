package skywolf46.iui.kotlin.abstraction

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import skywolf46.extrautility.util.ifFalse
import skywolf46.extrautility.util.runNonNull
import skywolf46.iui.util.EmptyViewer
import kotlin.reflect.KClass

abstract class InventoryUI(val title: String, val invSize: Int) {
    private val clicker: MutableMap<Int, InventoryClickEvent.(Inventory) -> Unit> = HashMap()
    private val dragger: MutableMap<Int, InventoryDragEvent.(Inventory) -> Unit> = HashMap()
    private val updater: MutableMap<Int, Player.(Inventory) -> ItemStack> = HashMap()
    private val globalClicker: MutableList<InventoryClickEvent.(Inventory) -> Unit> = ArrayList()
    private val globalDragger: MutableList<InventoryDragEvent.(Inventory) -> Unit> = ArrayList()
    private val globalCloser: MutableList<InventoryCloseEvent.(Inventory) -> Unit> = ArrayList()
    private val subInventory: MutableMap<String, InventoryUI> = HashMap()
    private val subInventoryClass: MutableMap<KClass<out InventoryUI>, MutableList<InventoryUI>> = HashMap()

    fun append(invName: String, inv: InventoryUI) {
        subInventory[invName] = inv
        subInventoryClass.computeIfAbsent(inv::class) { ArrayList() }.add(inv)
    }

    fun of(invName: String): InventoryUI? = subInventory[invName]

    fun <T : InventoryUI> of(cls: KClass<InventoryUI>): MutableList<InventoryUI>? =
        subInventoryClass[cls]?.let { return ArrayList(it) }

    fun onClick(ev: InventoryClickEvent) {
        globalClicker.forEach { it(ev, ev.inventory) }
        clicker[ev.rawSlot]?.invoke(ev, ev.inventory)
        subInventory.values.forEach { inv ->
            inv.onClick(ev)
        }
    }


    fun onClick(ev: InventoryDragEvent) {
        globalDragger.forEach { it(ev, ev.inventory) }
        ev.inventorySlots.forEach {
            dragger[it]?.invoke(ev, ev.inventory)
        }
        subInventory.values.forEach { inv ->
            inv.onClick(ev)
        }
    }

    fun onClose(ev: InventoryCloseEvent) {
        globalCloser.forEach { it(ev, ev.inventory) }
        subInventory.values.forEach { inv ->
            inv.onClose(ev)
        }
    }

    fun doUpdate(pl: Player, inv: Inventory) {
        if (inv.getUI() != this)
            throw IllegalStateException("Try to update UI to incorrect inventory")
        updater.forEach {
            val ita = inv.getItem(it.key)
            val next = it.value(pl, inv)
            if (ita == next)
                return@forEach
            runNonNull(next) {
                inv.setItem(it.key, this)
            }.ifFalse {
                inv.setItem(it.key, ItemStack(Material.AIR))
            }
        }
        updater.values.forEach {
            it(pl, inv)
        }
    }

    fun click(slot: Int, block: InventoryClickEvent.(Inventory) -> Unit) {
        clicker[slot] = block
    }


    fun drag(slot: Int, block: InventoryDragEvent.(Inventory) -> Unit) {
        dragger[slot] = block

    }

    fun close(block: InventoryCloseEvent.(Inventory) -> Unit) {
        globalCloser.add(block)
    }


    fun update(slot: Int, block: Player.(Inventory) -> ItemStack) {
        updater[slot] = block
    }

    fun create(pl: Player): Inventory {
        val inv = Bukkit.createInventory(null, invSize, title)
        modify(pl, inv)
        return inv
    }

    open fun modify(pl: Player, inv: Inventory) {
        (EmptyViewer.from(inv) ?: let {
            val ev = EmptyViewer(this)
            inv.viewers.add(ev)
            ev
        }).uI = this
        inv.updateUI(pl)
    }

}

fun Inventory.updateUI(pl: Player): Inventory {
    getUI()?.doUpdate(pl, this)
    return this
}

fun Inventory.getUI(): InventoryUI? =
    EmptyViewer.from(this)?.let {
        return it.uI
    }

fun <T : Any> Inventory.getValue(key: String): T? =
    EmptyViewer.from(this)?.let {
        it[key]
    }

fun <T : Any> Inventory.setValue(key: String, obj: T): T? =
    EmptyViewer.from(this)?.let {
        it[key] = obj
        obj
    }