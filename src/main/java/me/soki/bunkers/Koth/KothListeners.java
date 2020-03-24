package me.soki.bunkers.Koth;

import me.soki.bunkers.Claim.ClaimMGR;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.soki.bunkers.Koth.KothMGR.*;
import static me.soki.bunkers.Main.Main.playersPlaying;

public class KothListeners implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if (playersPlaying.contains(p.getName())) {
            if (kothCapper.equalsIgnoreCase(p.getName())) {
                if (!ClaimMGR.isOnCap(p.getLocation())) {
                    kothCapper = "";
                    p.sendMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.YELLOW + "Control of " + ChatColor.BLUE + "KOTH" + ChatColor.YELLOW + " lost.");
                    Bukkit.broadcastMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.YELLOW + "Someone lost control of " + ChatColor.BLUE + "KOTH" + ChatColor.YELLOW + ".");
                    checkKoth = false;
                    currentKothTime = kothMaxTime;
                }
            }else
            if (kothCapper.equalsIgnoreCase("")) {
                if (ClaimMGR.isOnCap(p.getLocation())) {
                    kothCapper = p.getName();
                    p.sendMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.YELLOW + "Attempting to control " + ChatColor.BLUE + "KOTH" + ChatColor.YELLOW + ".");
                    Bukkit.broadcastMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.YELLOW + "Someone is attempting to control " + ChatColor.BLUE + "KOTH" + ChatColor.YELLOW + ".");
                    checkKoth = true;
                }
            }
        }
    }
}
