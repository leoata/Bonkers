package me.soki.bunkers.Main;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatUtil implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        Team playersTeam = Team.getTeamFromPlayer(p.getName());
        if (playersTeam != null) {
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + playersTeam.getColor() + playersTeam.getTeamName() + ChatColor.GRAY + "]" + ChatColor.WHITE + p.getName() + ": " + e.getMessage());
        }else{
            Bukkit.broadcastMessage(p.getName() + ": " + e.getMessage());
        }
    }
}
