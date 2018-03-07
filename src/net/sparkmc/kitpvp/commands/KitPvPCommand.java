package net.sparkmc.kitpvp.commands;

import net.sparkmc.kitpvp.KitPvP;
import net.sparkmc.kitpvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitPvPCommand implements CommandExecutor {

    private String cmd;

    public KitPvPCommand(String cmd) {
        this.cmd = cmd;

        KitPvP.getKitpvp().getCommand(this.cmd).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        final Player player = (Player) sender;

        if (!player.isOp()) {
            return true;
        }

        if (cmd.getName().equals(this.cmd)) {
            if (args.length < 1) {
                player.sendMessage(Utils.color("&e/kitpvp setspawn"));
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("setspawn")) {
                KitPvP.getKitpvp().getArena().getCfg().set("Arena." + 1 + ".Location", Utils.getStringLocation(player.getLocation()));
                KitPvP.getKitpvp().getArena().saveCfg();

                player.sendMessage(Utils.color("&a¡Player's Location Sucessfully Setted!"));
                return true;
            }
        }
        return false;
    }
}
