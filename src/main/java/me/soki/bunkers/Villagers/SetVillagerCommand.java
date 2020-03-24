package me.soki.bunkers.Villagers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Villagers.VillagerMGR.settingVillagers;

public class SetVillagerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (p.isOp()){
            if (args.length == 0){
                p.sendMessage(ChatColor.RED + "Incorrect Usage: /setvillager <type>");
            }else{
                if (args[0].equalsIgnoreCase("combat") || args[0].equalsIgnoreCase("sell") || args[0].equalsIgnoreCase("build") || args[0].equalsIgnoreCase("enchanter")){
                    settingVillagers.put(p.getName(), args[0]);
                    p.sendMessage(ChatColor.YELLOW + "Setting a " + args[0] + " villager. Right click it to set it");
                }else{
                    p.sendMessage(ChatColor.RED + "Must be one of the following types of villagers: combat, sell, build, enchanter");
                }
            }
        }else{
            p.sendMessage(ChatColor.RED + "No permission.");
        }
        return true;
    }
}
