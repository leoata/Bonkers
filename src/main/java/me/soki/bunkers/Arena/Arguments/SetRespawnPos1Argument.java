package me.soki.bunkers.Arena.Arguments;

import me.soki.bunkers.Teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.soki.bunkers.Arena.ArenaMGR.sendHelp;
import static me.soki.bunkers.Main.Main.getPlugin;

public class SetRespawnPos1Argument {
    public static void SetRespawnPos1Argument(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2){
            Team targetTeam = Team.getTeam(args[1]);
            if (targetTeam != null){
                targetTeam.setTeamRespawnPos1(p.getLocation());
                p.sendMessage(ChatColor.YELLOW + "Set " + targetTeam.getColor() + targetTeam.getTeamName() + ChatColor.YELLOW + "'s first respawn position.");

                List<Integer> xyz = new ArrayList<>();
                xyz.add(p.getLocation().getBlockX());
                xyz.add(p.getLocation().getBlockY());
                xyz.add(p.getLocation().getBlockZ());
                getPlugin().getConfig().set(targetTeam.getTeamName() +  ".respawnpos1", xyz);
            }else{
                p.sendMessage(ChatColor.RED + "Invalid Team.");
            }

        }else{
            sendHelp(p);
        }
    }
}
