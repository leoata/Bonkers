package me.soki.bunkers.Teams.Command;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Teams.TeamMGR.*;

public class InviteArgument {
    public static void InviteArgument(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2) {
            Team playersTeam = Team.getTeamFromPlayer(p.getName());
            if (playersTeam == null) {
                p.sendMessage(ChatColor.RED + "You aren't in a team.");
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (Team.isInTeam(target.getName())) {
                        p.sendMessage(ChatColor.RED + "Target is in a team.");
                    } else {
                        if (playersTeam.getLeader().equalsIgnoreCase(p.getName())) {
                            p.sendMessage(ChatColor.YELLOW + "You invited " + target.getName() + " to your team.");
                            invites.put(target.getName(), playersTeam.getTeamName());
                            for (String eachPlayerName : playersTeam.getPlayers()) {
                                Player eachPlayer = Bukkit.getPlayer(eachPlayerName);
                                eachPlayer.sendMessage(ChatColor.YELLOW + target.getName() + " was invited to your team!");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You must be the leader to do this");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid Player.");
                }
            }
        }else{
            sendHelp(p);
        }
    }
}
