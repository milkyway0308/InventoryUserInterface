@file:Suppress("unused")

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
import skywolf46.extrautility.util.invoke
import skywolf46.extrautility.util.runNonNull
import skywolf46.iui.kotlin.data.PlayerInventoryPair
import skywolf46.iui.util.EmptyViewer
import kotlin.reflect.KClass

abstract class InventoryUI(val title: String, val invSize: Int) {
    private val clicker: MutableMap<Int, InventoryClickEvent.(PlayerInventoryPair) -> Unit> = HashMap()
    private val innerClicker: MutableMap<Int, InventoryClickEvent.(PlayerInventoryPair) -> Unit> = HashMap()
    private val outerClicker: MutableMap<Int, InventoryClickEvent.(PlayerInventoryPair) -> Unit> = HashMap()
    private val dragger: MutableMap<Int, InventoryDragEvent.(PlayerInventoryPair) -> Unit> = HashMap()
    private val afterDragger: MutableList<InventoryDragEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
    private val innerDragger: MutableMap<Int, InventoryDragEvent.(PlayerInventoryPair) -> Unit> = HashMap()
    private val outerDragger: MutableMap<Int, InventoryDragEvent.(PlayerInventoryPair) -> Unit> = HashMap()
    private val updater: MutableMap<Int, PlayerInventoryPair.() -> ItemStack> = HashMap()
    private val globalClicker: MutableList<InventoryClickEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
    private val globalInnerClicker: MutableList<InventoryClickEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
    private val globalOuterClicker: MutableList<InventoryClickEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
    private val globalDragger: MutableList<InventoryDragEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
    private val globalInnerDragger: MutableList<InventoryDragEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
    private val globalOuterDragger: MutableList<InventoryDragEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
    private val globalCloser: MutableList<InventoryCloseEvent.(PlayerInventoryPair) -> Unit> = ArrayList()
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
        globalClicker.forEach { it(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory)) }
        clicker[ev.rawSlot]?.invoke(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory))

        (ev.rawSlot < ev.inventory.size) {
            globalInnerClicker.forEach { it(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory)) }
            innerClicker[ev.rawSlot]?.invoke(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory))
        }.ifFalse {
            globalOuterClicker.forEach { it(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory)) }
            outerClicker[ev.slot]?.invoke(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory))
        }
        subInventory.values.forEach { inv ->
            inv.onClick(ev)
        }
    }


    fun onClick(ev: InventoryDragEvent) {
        globalDragger.forEach { it(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory)) }
        ev.inventorySlots.forEach {
            dragger[it]?.invoke(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory))
            (it < ev.inventory.size) {
                globalInnerDragger.forEach { it(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory)) }
                innerDragger[it]?.invoke(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory))
            }.ifFalse {
                globalOuterDragger.forEach { it(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory)) }
                outerDragger[it - ev.inventory.size]?.invoke(ev,
                    PlayerInventoryPair(ev.whoClicked as Player, ev.inventory))
            }
        }
        afterDragger.forEach { it(ev, PlayerInventoryPair(ev.whoClicked as Player, ev.inventory)) }
        subInventory.values.forEach { inv ->
            inv.onClick(ev)
        }
    }

    fun onClose(ev: InventoryCloseEvent) {
        globalCloser.forEach { it(ev, PlayerInventoryPair(ev.player as Player, ev.inventory)) }
        subInventory.values.forEach { inv ->
            inv.onClose(ev)
        }
    }

    fun doUpdate(pl: Player, inv: Inventory) {
        if (inv.getUI() != this)
            throw IllegalStateException("Try to update UI to incorrect inventory")
        updater.forEach {
            val ita = inv.getItem(it.key)
            val next = it.value(PlayerInventoryPair(pl, inv))
            (ita == next).ifFalse {
                runNonNull(next) {
                    inv.setItem(it.key, this)
                }.ifFalse {
                    inv.setItem(it.key, ItemStack(Material.AIR))
                }
            }
        }
        updater.values.forEach {
            it(PlayerInventoryPair(pl, inv))
        }
    }

    fun click(slot: Int, block: InventoryClickEvent.(PlayerInventoryPair) -> Unit) {
        clicker[slot] = block
    }

    fun click(block: InventoryClickEvent.(PlayerInventoryPair) -> Unit) {
        globalClicker.add(block)
    }

    fun innerClick(slot: Int, block: InventoryClickEvent.(PlayerInventoryPair) -> Unit) {
        innerClicker[slot] = block
    }

    fun innerClick(block: InventoryClickEvent.(PlayerInventoryPair) -> Unit) {
        globalInnerClicker.add(block)
    }

    fun outerClick(slot: Int, block: InventoryClickEvent.(PlayerInventoryPair) -> Unit) {
        outerClicker[slot] = block
    }

    fun outerClick(block: InventoryClickEvent.(PlayerInventoryPair) -> Unit) {
        globalOuterClicker.add(block)
    }

    fun drag(block: InventoryDragEvent.(PlayerInventoryPair) -> Unit) {
        globalDragger.add(block)
    }


    fun drag(slot: Int, block: InventoryDragEvent.(PlayerInventoryPair) -> Unit) {
        dragger[slot] = block
    }

    fun innerDrag(slot: Int, block: InventoryDragEvent.(PlayerInventoryPair) -> Unit) {
        innerDragger[slot] = block
    }

    fun innerDrag(block: InventoryDragEvent.(PlayerInventoryPair) -> Unit) {
        globalInnerDragger.add(block)
    }

    fun outerDrag(slot: Int, block: InventoryDragEvent.(PlayerInventoryPair) -> Unit) {
        outerDragger[slot] = block
    }

    fun outerDrag(block: InventoryDragEvent.(PlayerInventoryPair) -> Unit) {
        globalOuterDragger.add(block)
    }

    fun afterDrag(slot: Int, block: InventoryDragEvent.(PlayerInventoryPair) -> Unit) {
        afterDragger[slot] = block
    }

    fun close(block: InventoryCloseEvent.(PlayerInventoryPair) -> Unit) {
        globalCloser.add(block)
    }


    fun update(slot: Int, block: PlayerInventoryPair.() -> ItemStack) {
        updater[slot] = block
    }


    fun create(pl: Player): Inventory {
        val inv = Bukkit.createInventory(null, invSize, title)
        modify(pl, inv)
        return inv
    }

    fun createOnly(pl: Player): Inventory {
        val inv = Bukkit.createInventory(null, invSize, title)
        modifyOnly(pl, inv)
        return inv
    }

    open fun modify(pl: Player, inv: Inventory) {
        (EmptyViewer.from(inv) ?: run {
            EmptyViewer(this).apply { inv.viewers.add(this) }
        }).uI = this
        inv.updateUI(pl)
    }


    fun modifyOnly(pl: Player, inv: Inventory) {
        (EmptyViewer.from(inv) ?: run {
            EmptyViewer(this).apply { inv.viewers.add(this) }
        }).uI = this
    }

    fun ready() = InventoryReady(this)

    class InventoryReady(val ui: InventoryUI) {
        val map: MutableMap<String, Any> = HashMap()

        fun append(str: String, data: Any) {
            map[str] = data
        }

        fun toInventory(pl: Player): Inventory {
            val inv = ui.createOnly(pl)
            map.forEach { (key, value) ->
                inv.setValue(key, value)
            }
            inv.updateUI(pl)
            return inv
        }
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
        return obj
    }