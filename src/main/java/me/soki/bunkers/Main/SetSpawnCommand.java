package me.soki.bunkers.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.soki.bunkers.Main.Main.getPlugin;
import static me.soki.bunkers.Main.Main.lobbyLoc;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (p.isOp()) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.YELLOW + "Set the lobby point to your location!");
                lobbyLoc = p.getLocation();
                List<Integer> xyz = new ArrayList<>();
                xyz.add(p.getLocation().getBlockX());
                xyz.add(p.getLocation().getBlockY());
                xyz.add(p.getLocation().getBlockZ());
                getPlugin().getConfig().set("spawn", xyz);
            } else {
                p.sendMessage(ChatColor.RED + "Incorrect Usage: /setspawn");
            }
        }
        return true;
    }
}
