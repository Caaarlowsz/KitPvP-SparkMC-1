package net.sparkmc.kitpvp.kits;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sparkmc.kitpvp.KitPvP;
import net.sparkmc.kitpvp.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

  private String name;
  private String nameKit;
  private String permission;
  private Integer hitpoints;
  List<ItemStack> itemsKit = new ArrayList();
  private File file;
  private FileConfiguration config;
  private KitPvP main;
  
  public Kit(String nameKit, KitPvP main) {
    this.main = main;
    this.name = nameKit;
    this.file = new File(this.main.getDataFolder(), "kits/" + nameKit + ".yml");
    this.config = YamlConfiguration.loadConfiguration(this.file);
    this.nameKit = Utils.color(this.config.getString("name"));
    this.permission = Utils.color(this.config.getString("permission"));
    this.hitpoints = this.config.getInt("pointsonhit");
    for (String item : this.config.getStringList("item-list")) {
      this.itemsKit.add(Utils.readItemString(item));
    }
  }
  
  public List<String> serializeV(List<String> e)
  {
    List<String> b = Lists.newArrayList();
    for (String c : e) {
      b.add(ChatColor.translateAlternateColorCodes('&', c));
    }
    return b;
  }

  public Integer getHitpoints() {
    return hitpoints;
  }

  public static Kit getPermissionKit(Player player) {
    for (Kit kits : KitPvP.getKitpvp().getKitHashMap().values()) {
      if (player.hasPermission(kits.getPermission())) {
        return kits;
      }
    }
    return null;
  }

  public String getPermission()
  {
    return this.permission;
  }
  
  public List<ItemStack> getItems()
  {
    return this.itemsKit;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getNameKit()
  {
    return this.nameKit;
  }
}