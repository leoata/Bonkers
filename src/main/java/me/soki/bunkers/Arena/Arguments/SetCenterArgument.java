package me.soki.bunkers.Arena.Arguments;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.soki.bunkers.Arena.ArenaMGR.*;
import static me.soki.bunkers.Main.Main.getPlugin;

public class SetCenterArgument {
    public static void SetCenter(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1){
            if (p.isOp()){
                arenaCenter = p.getLocation();
                p.sendMessage(ChatColor.YELLOW + "Set the arena center");
                List<Integer> xyz = new ArrayList<>();
                xyz.add(p.getLocation().getBlockX());
                xyz.add(p.getLocation().getBlockY());
                xyz.add(p.getLocation().getBlockZ());
                getPlugin().getConfig().set("arena.center", xyz);
            }
        }else{
            sendHelp(p);
        }
    }
}
