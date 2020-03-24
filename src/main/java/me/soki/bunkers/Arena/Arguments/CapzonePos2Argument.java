package me.soki.bunkers.Arena.Arguments;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.soki.bunkers.Arena.ArenaMGR.*;
import static me.soki.bunkers.Main.Main.getPlugin;

public class CapzonePos2Argument {
    public static void CapzonePos2(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1){
            if (p.isOp()){
                capzonePos2 = p.getLocation();

                List<Integer> xyz = new ArrayList<>();
                xyz.add(p.getLocation().getBlockX());
                xyz.add(p.getLocation().getBlockY());
                xyz.add(p.getLocation().getBlockZ());
                getPlugin().getConfig().set("arena.capzonepos2", xyz);
                p.sendMessage(ChatColor.YELLOW + "Set the capzone's second position");
            }
        }else{
            sendHelp(p);
        }
    }
}
