package skywolf46.iui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.iui.listener.InventoryListener;

public class InventoryUserInterface extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }
//
//    @$_("/test1243")
//    public static void cmd(Player p) {
//        Inventory inv = Bukkit.createInventory(null, 54, "Test");

//        p.openInventory(inv);
//    }


}
