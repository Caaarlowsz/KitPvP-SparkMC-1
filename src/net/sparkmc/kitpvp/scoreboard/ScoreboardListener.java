package net.sparkmc.kitpvp.scoreboard;

import java.util.WeakHashMap;

import net.sparkmc.kitpvp.KitPvP;
import net.sparkmc.kitpvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardListener implements Listener {
  private final WeakHashMap<Player, ScoreboardHelper> helper = new WeakHashMap();
  
  public ScoreboardHelper getScoreboardFor(Player player)
  {
    return (ScoreboardHelper)this.helper.get(player);
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    Player player = event.getPlayer();
    this.helper.remove(player);
  }
  
  @EventHandler
  public void onPlayerKick(PlayerKickEvent event)
  {
    Player player = event.getPlayer();
    this.helper.remove(player);
  }
  
  public void unregister(Scoreboard board, String name)
  {
    Team team = board.getTeam(name);
    if (team != null) {
      team.unregister();
    }
  }

  public void registerScoreboards(Player player) {
    Scoreboard bukkitScoreBoard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
    player.setScoreboard(bukkitScoreBoard);
    ScoreboardHelper scoreboardHelper = new ScoreboardHelper(bukkitScoreBoard, Utils.color(KitPvP.getKitpvp().getScoreboard().getCfg().getString("scoreboard-title")));
    this.helper.put(player, scoreboardHelper);
  }
  
  public void resendTab(Player player)
  {
    if (getScoreboardFor(player) == null) {
      return;
    }
    getScoreboardFor(player).getScoreBoard();
  }

  public WeakHashMap<Player, ScoreboardHelper> getHelper() {
    return helper;
  }

  public void setupScoreboard()
  {
    new BukkitRunnable()
    {
      public void run()
      {
        for (Player player : Bukkit.getOnlinePlayers()) {
          if (ScoreboardListener.this.helper.get(player) == null) {
            ScoreboardListener.this.registerScoreboards(player);
            return;
          }

          ScoreboardHelper scoreboardHelper = ScoreboardListener.this.getScoreboardFor(player);
          scoreboardHelper.clear();
          for (String asd : KitPvP.getKitpvp().getScoreboard().getCfg().getStringList("scoreboard-lines")) {
            scoreboardHelper.add(Utils.color(asd).replace("%streak%" , "" + KitPvP.getKitpvp().getStreak(player)));
          }
          
          scoreboardHelper.update(player);
        }
      }
    }.runTaskTimer(KitPvP.getKitpvp(), 1L, 1L);
  }
}
