package me.soki.bunkers.Teams.Command;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Teams.TeamMGR.sendHelp;

public class KickArgument {
    public static void KickArgument(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2) {
            Team playersTeam = Team.getTeamFromPlayer(p.getName());
            if (playersTeam != null) {
                if (playersTeam.getLeader().equalsIgnoreCase(p.getName())) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (target == p) {
                            p.sendMessage(ChatColor.RED + "You can't kick yourself.");
                        } else {
                            playersTeam.removePlayer(target.getName());
                            p.sendMessage(ChatColor.YELLOW + "You kicked " + target.getName() + "!");
                            for (String eachPlayerName : playersTeam.getPlayers()) {
                                Player eachPlayer = Bukkit.getPlayer(eachPlayerName);
                                eachPlayer.sendMessage(ChatColor.YELLOW + target.getName() + " was kicked from your team!");
                            }
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid Player.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You must be the leader to do this.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You aren't in a team.");
            }
        }else{
            sendHelp(p);
        }
    }
}
