package me.soki.bunkers.Teams.Command;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Teams.TeamMGR.invites;
import static me.soki.bunkers.Teams.TeamMGR.sendHelp;

public class JoinArgument {
    public static void JoinArgument(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Team playersTeam = Team.getTeamFromPlayer(p.getName());
        if (args.length == 2) {
            if (playersTeam == null) {
                Team targetTeam = Team.getTeam(args[1]);
                if (targetTeam != null) {
                    if (invites.containsKey(p.getName())) {
                        if (invites.get(p.getName()).equalsIgnoreCase(targetTeam.getTeamName())) {
                            for (String eachPlayerName : targetTeam.getPlayers()) {
                                Player eachPlayer = Bukkit.getPlayer(eachPlayerName);
                                eachPlayer.sendMessage(ChatColor.YELLOW + p.getName() + " joined your team!");
                            }
                            p.sendMessage(ChatColor.YELLOW + "You joined " + targetTeam.getColor() + targetTeam.getTeamName());
                            invites.remove(p.getName());
                            targetTeam.addPlayer(p.getName());
                        } else {
                            p.sendMessage(ChatColor.RED + "You don't have an invite to this team.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have an invite to this team.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid target team (Ex. /team join Blue).");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You are already in a team. Leave it first");
            }
        }else{
            sendHelp(p);
        }
    }
}
