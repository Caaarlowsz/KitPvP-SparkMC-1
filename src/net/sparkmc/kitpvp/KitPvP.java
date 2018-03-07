package net.sparkmc.kitpvp;

import net.sparkmc.kitpvp.configuration.Configuration;
import net.sparkmc.kitpvp.kits.Kit;
import net.sparkmc.kitpvp.scoreboard.ScoreboardListener;
import net.sparkmc.kitpvp.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;

public class KitPvP extends JavaPlugin {

    private static KitPvP kitpvp;
    private Configuration configuration;
    private Configuration scoreboard;
    private Configuration arena;
    private HashMap<String, Kit> kitHashMap;
    private ScoreboardListener scoreboardListener;

    /*/
    Streak
     */
    private final TreeMap<Player, Integer> streak = new TreeMap<Player , Integer>();

    /*
    Kit Players
     */
    private final LinkedHashSet<Player> player = new LinkedHashSet<Player>();

    @Override
    public void onEnable() {
        kitpvp = this;

        this.kitHashMap = new HashMap<String, Kit>();
        this.setupKits();

        this.configuration = new Configuration(this , "config");
        this.scoreboard = new Configuration(this , "scoreboard");
        this.arena = new Configuration(this , "arena");

        this.scoreboardListener = new ScoreboardListener();
        this.scoreboardListener.setupScoreboard();


    }

    @Override
    public void onDisable() {}

    public Configuration getConfiguration() {
        return configuration;
    }

    public Configuration getScoreboard() {
        return scoreboard;
    }

    public Configuration getArena() {
        return arena;
    }

    public HashMap<String, Kit> getKitHashMap() {
        return kitHashMap;
    }

    public static KitPvP getKitpvp() {
        return kitpvp;
    }

    public TreeMap<Player, Integer> getStreak() {
        return streak;
    }

    public Integer getStreak(Player player) {
        if (this.getStreak().get(player) == null) {
            return 0;
        }
        return this.getStreak().get(player);
    }

    public Location getPlayersLoc() {
        if (this.getArena().getCfg().getString("Arena." + 1 + ".GhastLoc") == null) {
            System.out.print("You have to setup the location , no location!");
            return null;
        }
        return Utils.getLocationString(this.getArena().getCfg().getString("Arena." + 1 + ".Location"));
    }

    public LinkedHashSet<Player> getPlayer() {
        return player;
    }

    public ScoreboardListener getScoreboardListener() {
        return scoreboardListener;
    }

    public void setupKits() {
        File dir = new File(getDataFolder(), "kits");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if ((dir.exists()) && (dir.isDirectory())) {
            File[] arrayOfFile;
            int j = (arrayOfFile = dir.listFiles()).length;
            for (int i = 0; i < j; i++) {
                File kitsFile = arrayOfFile[i];
                String name = kitsFile.getName().replaceAll(".yml", "");
                kitHashMap.put(name, new Kit(name, this));
            }
        }
    }
}
