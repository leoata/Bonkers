package me.soki.bunkers.Arena.Arguments;

import me.soki.bunkers.Arena.ArenaMGR;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.soki.bunkers.Arena.ArenaMGR.mineYLevel;
import static me.soki.bunkers.Main.Main.getPlugin;

public class MineYLevelArgument {
    public static void MineYLevelArgument(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2) {
            try {
                mineYLevel = Integer.valueOf(args[1]);
                getPlugin().getConfig().set("arena.mineylevel", p.getLocation().getBlockY());
                p.sendMessage(ChatColor.YELLOW + "Set the mine's Y level");

            } catch (Exception e) {
                p.sendMessage(ChatColor.RED + args[1] + " isn't an integer.");
            }
        }else{
            p.sendMessage(ChatColor.RED + "Incorrect Usage: /arena mineylevel <ylevel>");
        }
    }
}
