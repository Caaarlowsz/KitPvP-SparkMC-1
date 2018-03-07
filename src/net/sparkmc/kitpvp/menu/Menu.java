package net.sparkmc.kitpvp.menu;

import net.sparkmc.kitpvp.KitPvP;
import net.sparkmc.kitpvp.kits.Kit;
import net.sparkmc.kitpvp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Menu {

    public static void KitSelector(Player player) {
        Inventory kit = Bukkit.createInventory(null , 54 , "Kit Selector");

        for (Kit kits : KitPvP.getKitpvp().getKitHashMap().values()) {
            kit.addItem(new ItemBuilder().setDisplayName("&e" + kits.getName()).setType(kits.getItems().get(0).getType()).getItem());
        }

        player.openInventory(kit);
    }
}
