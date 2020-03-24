package me.soki.bunkers.Teams.Command;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LeaveArgument {
    public static void LeaveArgument(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Team playersTeam = Team.getTeamFromPlayer(p.getName());
        if (playersTeam != null){
            if (!playersTeam.getLeader().equalsIgnoreCase(p.getName())) {
                for (String eachPlayerName : playersTeam.getPlayers()) {
                    Player eachPlayer = Bukkit.getPlayer(eachPlayerName);
                    eachPlayer.sendMessage(ChatColor.YELLOW + p.getName() + " left your team!");
                }
            }else{
                p.sendMessage(ChatColor.RED + "You can't leave as a leader.");
            }
        }else{
            p.sendMessage(ChatColor.RED + "You aren't in a team.");
        }
    }
}
