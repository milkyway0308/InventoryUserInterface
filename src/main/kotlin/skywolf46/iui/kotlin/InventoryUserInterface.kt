package skywolf46.iui.kotlin

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import skywolf46.iui.kotlin.listener.InventoryListener

class InventoryUserInterface : JavaPlugin() {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(InventoryListener(), this)
    }
}