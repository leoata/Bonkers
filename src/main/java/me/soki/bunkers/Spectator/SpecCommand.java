package me.soki.bunkers.Spectator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Spectator.Spectator.inSpec;

public class SpecCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (inSpec.contains(p.getName())){
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null){
                p.sendMessage(ChatColor.RED + "Invalid Player.");
            }else{
                p.teleport(target);
                p.sendMessage(ChatColor.YELLOW + "Now spectating " + target.getName());
            }
        }else{
            p.sendMessage(ChatColor.RED + "You aren't spectating!");
        }
    return true;
    }
}
