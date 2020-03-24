package me.soki.bunkers.Game;

import me.soki.bunkers.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static me.soki.bunkers.Main.Main.getPlugin;

public class PearlCooldown implements Listener {
    public static HashMap<String, Long> enderpearlTimer = new HashMap<>();
    public static Integer enderpearlTime = 16;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (p.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
                if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                        if (!enderpearlTimer.containsKey(p.getName())) {
                                p.sendMessage(ChatColor.BLUE + "You are now on " + ChatColor.BLUE + ChatColor.BOLD + "enderpearl cooldown " + ChatColor.BLUE + "for " + enderpearlTime + " seconds");
                                enderpearlTimer.put(p.getName(), System.currentTimeMillis());

                        } else {
                            long secondsleft = ((enderpearlTimer.get(p.getName()) / 1000) + enderpearlTime) - (System.currentTimeMillis() / 1000);
                            if (secondsleft > 0) {
                                p.sendMessage(ChatColor.RED + "You are currently on enderpearl cooldown for another " + ChatColor.RED + ChatColor.BOLD + secondsleft + "s");
                                e.setCancelled(true);
                            } else {
                                enderpearlTimer.remove(p.getName());
                                enderpearlTimer.put(p.getName(), System.currentTimeMillis());

                            }
                        }
                }
            }
        }
    }
}
