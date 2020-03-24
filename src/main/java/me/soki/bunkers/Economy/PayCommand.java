package me.soki.bunkers.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Economy.EconomyMGR.*;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || target == p) {
                p.sendMessage(ChatColor.RED + "Invalid Player.");
            } else {
                try {
                    Integer amount = Integer.valueOf(args[1]);
                    if (playerBalances.get(p.getName()) >= amount) {
                        giveMoney(target, amount);
                        takeMoney(p, amount);
                        p.sendMessage(ChatColor.YELLOW + "You sent " + ChatColor.GREEN + "$" + args[1] + ChatColor.YELLOW + " to " + target.getName() + ".");
                        target.sendMessage(ChatColor.YELLOW + "You received " + ChatColor.GREEN + "$" +args[1] +  ChatColor.YELLOW + " from " + p.getName() + ".");
                    } else {
                        p.sendMessage(ChatColor.RED + "Insufficient funds.");
                    }
                } catch (Exception e) {
                    p.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.RED + " is not an integer.");
                }
            }
        } else {
            p.sendMessage(ChatColor.RED + "Incorrect Usage: /pay <player> <amount>");
        }
        return true;
    }
}
