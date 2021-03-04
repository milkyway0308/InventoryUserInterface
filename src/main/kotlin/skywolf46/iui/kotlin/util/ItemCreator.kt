package skywolf46.iui.kotlin.util

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

import kotlin.collections.HashMap

class ItemCreator(var material: Material)  {
    var durability = 0
        private set
    var amount = 1
        private set
    private var meta: CreatorMeta? = null
    fun setMaterial(material: Material): ItemCreator {
        this.material = material
        return this
    }

    fun setDurability(durability: Int): ItemCreator {
        this.durability = durability
        return this
    }

    fun setAmount(amount: Int): ItemCreator {
        this.amount = amount
        return this
    }

    fun build(): ItemStack {
        val item = ItemStack(material, amount,
            durability.toShort())
        if (meta != null) {
            val meta = item.itemMeta
            this.meta!!.applyMeta(meta)
            item.itemMeta = meta
        }
        return item
    }

    fun getMeta(): CreatorMeta {
        return if (meta == null) CreatorMeta().also { meta = it } else meta!!
    }


    open inner class CreatorMeta {
        private var display: String? = null
        private var lore: MutableList<String>? = null
        private var ench: HashMap<Enchantment, Int>? = null
        private val flags: MutableList<ItemFlag> = ArrayList()
        private var unbreakable = false
        fun item(): ItemCreator {
            return this@ItemCreator
        }

        fun build(): ItemStack {
            return this@ItemCreator.build()
        }

        fun setDisplayName(display: String?): CreatorMeta {
            this.display = display
            return this
        }

        fun addLore(lore: String): CreatorMeta {
            getLore().add(lore)
            return this
        }

        fun setLore(lore: MutableList<String>?): CreatorMeta {
            this.lore = lore
            return this
        }

        fun getLore(): MutableList<String> {
            if (lore == null) lore = ArrayList()
            return lore as MutableList<String>
        }

        fun setEnchant(ench: Enchantment, lv: Int): CreatorMeta {
            if (this.ench == null) this.ench = HashMap()
            if (lv == 0) this.ench!!.remove(ench) else this.ench!![ench] = lv
            return this
        }

        fun addFlags(vararg fl: ItemFlag): CreatorMeta {
            flags.addAll(listOf(*fl))
            return this
        }

        fun allFlag(): CreatorMeta {
            flags.addAll(listOf(*ItemFlag.values()))
            return this
        }

        fun unbreakable(`val`: Boolean): CreatorMeta {
            unbreakable = `val`
            return this
        }

        fun applyMeta(meta: ItemMeta) {
            if (display != null) meta.displayName = display
            if (lore != null) meta.lore = lore
            if (ench != null) for ((key, value) in ench!!) meta.addEnchant(
                key, value, true)
            if (flags.size != 0) meta.addItemFlags(*flags.toTypedArray())
            meta.isUnbreakable = unbreakable
            apply(meta)
        }

        protected open fun apply(meta: ItemMeta?) {}
    }

    internal inner class SkullMeta : CreatorMeta() {
        override fun apply(meta: ItemMeta?) {
            super.apply(meta)
        }
    }
}