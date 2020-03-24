package me.soki.bunkers.Teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static me.soki.bunkers.Main.Main.*;
import static org.bukkit.Bukkit.getLogger;

public class TeamMGR {
    public static HashMap<String, String> invites = new HashMap<>();
    //player, teamname
    //player, entry

    public static void setupTeams(){
        new Team(ChatColor.BLUE, blueTeamPlayers, "Blue", 6, null);
        new Team(ChatColor.GREEN, greenTeamPlayers, "Green", 6, null);
        new Team(ChatColor.YELLOW, yellowTeamPlayers, "Yellow", 6, null);
        new Team(ChatColor.RED, redTeamPlayers, "Red", 6, null);
        new Team(ChatColor.RED, new ArrayList<>(), "KOTH", null, null);

        getLogger().log(Level.INFO,
                "All teams successfully created");
    }

    public static void sendHelp(Player p){
        p.sendMessage("&c&m-------------".replace("&", "§"));
        p.sendMessage("&c  » /team invite".replace("&", "§"));
        p.sendMessage("&c  » /team join".replace("&", "§"));
        p.sendMessage("&c  » /team kick".replace("&", "§"));
        p.sendMessage("&c  » /team leave".replace("&", "§"));
        p.sendMessage("&c&m-------------".replace("&", "§"));
    }
}
