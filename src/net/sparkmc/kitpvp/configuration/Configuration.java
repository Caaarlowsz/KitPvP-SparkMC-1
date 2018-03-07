package net.sparkmc.kitpvp.configuration;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {

  private YamlConfiguration yml;
  private File file;
  JavaPlugin plugin;

  public Configuration(JavaPlugin plugin, String name) {
    this.plugin = plugin;
    this.file = new File(plugin.getDataFolder(), name + ".yml");
    if (!this.file.exists() && true) {
      this.plugin.saveResource(name + ".yml", false);
    }
    yml = YamlConfiguration.loadConfiguration(file);
    this.file.getParentFile().mkdirs();
  }

  public FileConfiguration getCfg() {
    return yml;
  }

  public void saveCfg() {
    try {
      yml.save(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void reloadCfg() {
    try {
      yml.load(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}