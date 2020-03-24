package me.soki.bunkers.Arena.Arguments;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;

import static me.soki.bunkers.Arena.ArenaMGR.arenaPos2;
import static me.soki.bunkers.Arena.ArenaMGR.sendHelp;
import static me.soki.bunkers.Main.Main.getPlugin;

public class Pos2Argument {
    public static void Pos2(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1){
            if (p.isOp()){
                arenaPos2 = p.getLocation();
                p.sendMessage(ChatColor.YELLOW + "Set the arena's second position");
                List<Integer> xyz = new ArrayList<>();
                xyz.add(p.getLocation().getBlockX());
                xyz.add(p.getLocation().getBlockY());
                xyz.add(p.getLocation().getBlockZ());
                getPlugin().getConfig().set("arena.pos2", xyz);
            }
        }else{
            sendHelp(p);
        }
    }
}
