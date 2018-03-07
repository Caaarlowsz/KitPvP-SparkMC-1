package net.sparkmc.kitpvp.scoreboard;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardHelper {
  private List<ScoreboardText> list = new ArrayList();
  private Scoreboard scoreBoard;
  private Objective objective;
  private String tag = "PlaceHolder";
  private int lastSentCount = -1;
  private static ScoreboardHelper scoreboard;
  
  public ScoreboardHelper(Scoreboard scoreBoard, String title)
  {
    Preconditions.checkState(title.length() <= 32, "title can not be more than 32");
    this.tag = ChatColor.translateAlternateColorCodes('&', title);
    this.scoreBoard = scoreBoard;
    (this.objective = getOrCreateObjective(this.tag)).setDisplaySlot(DisplaySlot.SIDEBAR);
  }
  
  public static ScoreboardHelper getInstance()
  {
    return scoreboard;
  }
  
  public Scoreboard getScoreBoard()
  {
    return this.scoreBoard;
  }
  
  public void add(String input)
  {
    input = ChatColor.translateAlternateColorCodes('&', input);
    ScoreboardText text = null;
    if (input.length() <= 16)
    {
      text = new ScoreboardText(input, "");
    }
    else
    {
      String first = input.substring(0, 16);
      String second = input.substring(16, input.length());
      if (first.endsWith(String.valueOf('&')))
      {
        first = first.substring(0, first.length() - 1);
        second = '&' + second;
      }
      String lastColors = ChatColor.getLastColors(first);
      second = lastColors + second;
      text = new ScoreboardText(first, StringUtils.left(second, 16));
    }
    this.list.add(text);
  }
  
  public void clear()
  {
    this.list.clear();
  }
  
  public void remove(int index)
  {
    String name = getNameForIndex(index);
    this.scoreBoard.resetScores(name);
    Team team = getOrCreateTeam(ChatColor.stripColor(StringUtils.left(this.tag, 14)) + index, index);
    team.unregister();
  }
  
  public void update(Player player)
  {
    player.setScoreboard(this.scoreBoard);
    for (int sentCount = 0; sentCount < this.list.size(); sentCount++)
    {
      Team i = getOrCreateTeam(ChatColor.stripColor(StringUtils.left(this.tag, 14)) + sentCount, sentCount);
      ScoreboardText str = (ScoreboardText)this.list.get(this.list.size() - sentCount - 1);
      i.setPrefix(str.getLeft());
      i.setSuffix(str.getRight());
      this.objective.getScore(getNameForIndex(sentCount)).setScore(sentCount + 1);
    }
    if (this.lastSentCount != -1)
    {
      int sentCount = this.list.size();
      for (int var4 = 0; var4 < this.lastSentCount - sentCount; var4++) {
        remove(sentCount + var4);
      }
    }
    this.lastSentCount = this.list.size();
  }
  
  public Team getOrCreateTeam(String team, int i)
  {
    Team value = this.scoreBoard.getTeam(team);
    if (value == null)
    {
      value = this.scoreBoard.registerNewTeam(team);
      value.addEntry(getNameForIndex(i));
    }
    return value;
  }
  
  public Objective getOrCreateObjective(String objective)
  {
    Objective value = this.scoreBoard.getObjective("dummyhubobj");
    if (value == null) {
      value = this.scoreBoard.registerNewObjective("dummyhubobj", "dummy");
    }
    value.setDisplayName(objective);
    return value;
  }
  
  public String getNameForIndex(int index)
  {
    return ChatColor.values()[index].toString() + ChatColor.RESET;
  }
  
  private static class ScoreboardText
  {
    private String left;
    private String right;
    
    public ScoreboardText(String left, String right)
    {
      this.left = left;
      this.right = right;
    }
    
    public String getLeft()
    {
      return this.left;
    }
    
    public String getRight()
    {
      return this.right;
    }
  }
}
