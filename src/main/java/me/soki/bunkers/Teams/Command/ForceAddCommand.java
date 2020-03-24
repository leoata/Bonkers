package me.soki.bunkers.Teams.Command;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Teams.TeamMGR.sendHelp;

public class ForceAddCommand {
    public static void ForceAddCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 3) {
            if (p.isOp()){
            //if (p.getUniqueId().equals(UUID.fromString("912a9407-8592-4a58-ad66-dc2d10e81033")) || p.getUniqueId().equals(UUID.fromString("23d83f86-12d0-4068-8fda-5dda8d912ce1"))) {
                Player targetPlayer = Bukkit.getPlayer(args[1]);
                Team targetTeam = Team.getTeam(args[2]);
                if (targetPlayer != null) {
                    if (targetTeam != null) {
                        if (Team.getTeamFromPlayer(targetPlayer.getName()) != null) {
                            Team.getTeamFromPlayer(targetPlayer.getName()).removePlayer(targetPlayer.getName());
                        }
                        targetTeam.addPlayer(targetPlayer.getName());
                        p.sendMessage(ChatColor.YELLOW + "You forcefully added " + targetPlayer.getName() + " to team " + targetTeam.getColor() + targetTeam.getTeamName()
                                + ChatColor.YELLOW + "!");
                        targetPlayer.sendMessage(ChatColor.YELLOW + "You were forcefully added to " + targetTeam.getColor() + targetTeam.getTeamName() + ChatColor.YELLOW +
                                " by " + p.getName());
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid Team (Ex. Blue, Red, Green, Yellow).");
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
