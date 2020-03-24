package me.soki.bunkers.Economy;

import me.soki.bunkers.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Economy.EconomyMGR.sendBalMSG;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            sendBalMSG(p);
        }else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatColor.RED + "Invalid Player");
            } else {
                p.sendMessage(ChatColor.RED + "You cannot view other player's balances!");
            }
        }else{

            p.sendMessage(ChatColor.RED + "Incorrect Usage: /bal [player]");
        }
        return true;
    }
}
