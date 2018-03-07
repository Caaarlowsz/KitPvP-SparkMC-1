package net.sparkmc.kitpvp.events;

import net.sparkmc.kitpvp.KitPvP;
import net.sparkmc.kitpvp.kits.Kit;
import net.sparkmc.kitpvp.menu.Menu;
import net.sparkmc.kitpvp.utils.ItemBuilder;
import net.sparkmc.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEvents implements Listener {

    @EventHandler
    public void PJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        event.setJoinMessage(null);

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        KitPvP.getKitpvp().getStreak().put(player , 0);

        player.getInventory().addItem(new ItemBuilder().setType(Material.DIAMOND_AXE).setDisplayName("&eKit Selector").setLores("&e7¡Click to select a kit!").getItem());
    }

    @EventHandler
    public void PInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (!KitPvP.getKitpvp().getPlayer().contains(player)) {
            if (player.getItemInHand().getType() == Material.DIAMOND_AXE) {
                Menu.KitSelector(player);
            }
        }
    }
    @EventHandler
    public void PQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        event.setQuitMessage(null);

        KitPvP.getKitpvp().getPlayer().remove(player);
    }

    @EventHandler
    public void DamageByPlayer(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            final Player player = (Player) event.getEntity();
            final Player damager = (Player) event.getDamager();

            double damage = event.getFinalDamage();

            damager.sendMessage(Utils.color("&eYou killed the player &c" + player.getName()));

            KitPvP.getKitpvp().getStreak().put(damager , KitPvP.getKitpvp().getStreak(damager) + 1);

            if (!KitPvP.getKitpvp().getPlayer().contains(player)) {
                event.setCancelled(true);
                return;
            }

            if (player.getHealth() - damage <= 0) {
                player.teleport(KitPvP.getKitpvp().getPlayersLoc());

                KitPvP.getKitpvp().getStreak().put(player , 0);

                KitPvP.getKitpvp().getPlayer().remove(player);

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void Drop(PlayerDropItemEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void Damage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();

            double damage = event.getFinalDamage();

            if (!KitPvP.getKitpvp().getPlayer().contains(player)) {
                event.setCancelled(true);
                return;
            }

            if (player.getHealth() - damage <= 0) {
                player.teleport(KitPvP.getKitpvp().getPlayersLoc());

                KitPvP.getKitpvp().getStreak().put(player , 0);

                KitPvP.getKitpvp().getPlayer().remove(player);

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void Click(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }


        if (event.getInventory().getName().equals("Kit Selector")) {
            event.setCancelled(true);

            for (Kit kits : KitPvP.getKitpvp().getKitHashMap().values()) {
                if (event.getCurrentItem().getType() == kits.getItems().get(0).getType()) {
                    player.sendMessage(Utils.color("&aYou selected the kit &e" + kits.getName()));

                    for (ItemStack asd : kits.getItems()) {
                        player.getInventory().addItem(asd);
                    }

                    player.teleport(KitPvP.getKitpvp().getPlayersLoc());

                    KitPvP.getKitpvp().getPlayer().add(player);
                }
            }
        }
    }
}
