package me.soki.bunkers.Teams.Command;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.soki.bunkers.Teams.TeamMGR.sendHelp;

public class LeaderArgument {
    public static void LeaderArgument(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 3) {
            if (p.getUniqueId().equals(UUID.fromString("912a9407-8592-4a58-ad66-dc2d10e81033")) || p.getUniqueId().equals(UUID.fromString("23d83f86-12d0-4068-8fda-5dda8d912ce1"))) {
                Player targetPlayer = Bukkit.getPlayer(args[1]);
                Team targetTeam = Team.getTeam(args[2]);
                if (targetPlayer != null) {
                    if (targetTeam != null) {
                        if (Team.getTeamFromPlayer(targetPlayer.getName()) == (targetTeam)) {
                            if (!targetTeam.getLeader().equalsIgnoreCase(targetPlayer.getName())){
                            targetTeam.setLeader(targetPlayer.getName());
                            targetPlayer.sendMessage(ChatColor.YELLOW + "You were added as leader of " + targetTeam.getColor() + targetTeam.getTeamName());
                        }else{
                                targetPlayer.sendMessage(ChatColor.RED + "You were removed from leader!");
                                targetTeam.setLeader("");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + targetPlayer.getName() + " is not on the " + targetTeam.getTeamName() + " team.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid team.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid Player.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "No permission.");
            }
        }else{
            sendHelp(p);
        }
    }
}
