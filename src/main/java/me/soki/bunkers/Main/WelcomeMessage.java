package me.soki.bunkers.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WelcomeMessage implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.RED + "Fear not, your ip was not grabbed when you joined this server. This server uses a python script that encrypts your ip with SHA-256." +
        " If someone was to look at what your ips turned into, it would take 10 times the age of the universe to crack.");
        p.sendMessage(ChatColor.YELLOW + "Welcome to the private bunkers game hosted by" + ChatColor.LIGHT_PURPLE + " Flexicuted" + ChatColor.YELLOW +".");
        p.sendMessage(ChatColor.YELLOW + "This server was coded and hosted by " + ChatColor.LIGHT_PURPLE + "sokiii" + ChatColor.YELLOW + ".");
        p.sendMessage(ChatColor.YELLOW + "This map was made by " + ChatColor.LIGHT_PURPLE + "congeal" + ChatColor.YELLOW + ".");

        e.setJoinMessage(ChatColor.AQUA + p.getName() + ChatColor.GRAY + " joined the server!");

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        e.setQuitMessage(ChatColor.AQUA + p.getName() + ChatColor.GRAY + " left the server!");
    }
}
