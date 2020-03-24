package me.soki.bunkers.Arena;

import me.soki.bunkers.Claim.ClaimMGR;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static me.soki.bunkers.Arena.ArenaMGR.*;
import static me.soki.bunkers.Game.GameMGR.isStarted;

public class ArenaListeners implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        if (isStarted) {
            if (loc.getBlockY() > arenaPos1.getBlockY() && loc.getBlockY() > arenaPos2.getBlockY()) {
                p.teleport(arenaCenter);
                p.sendMessage(ChatColor.RED + "You must stay within the arena");
            }
            if (loc.getBlockY() < arenaPos1.getBlockY() && loc.getBlockY() < arenaPos2.getBlockY()){
                p.teleport(arenaCenter);
                p.sendMessage(ChatColor.RED + "You must stay within the arena");
            }
            if (loc.getBlockX() < arenaPos1.getBlockX() && loc.getBlockX() < arenaPos2.getBlockX()){
                p.teleport(arenaCenter);
                p.sendMessage(ChatColor.RED + "You must stay within the arena");
            }
            if (loc.getBlockX() > arenaPos1.getBlockX() && loc.getBlockX() > arenaPos2.getBlockX()){
                p.teleport(arenaCenter);
                p.sendMessage(ChatColor.RED + "You must stay within the arena");
            }
            if (loc.getBlockZ() < arenaPos1.getBlockZ() && loc.getBlockZ() < arenaPos2.getBlockZ()){
                p.teleport(arenaCenter);
                p.sendMessage(ChatColor.RED + "You must stay within the arena");
            }
            if (loc.getBlockZ() > arenaPos1.getBlockZ() && loc.getBlockZ() > arenaPos2.getBlockZ()){
                p.teleport(arenaCenter);
                p.sendMessage(ChatColor.RED + "You must stay within the arena");
            }
        }

        String currentTeamName;
        if (ClaimMGR.whichClaimIsThisIn(loc) == null || loc == null){
            currentTeamName = "§cWarzone";
        }else{
             currentTeamName = ClaimMGR.whichClaimIsThisInStr(loc);
        }
        if (!currentTeamName.equalsIgnoreCase(lastClaim.get(p.getName()))) {
            p.sendMessage(ChatColor.YELLOW + "Now leaving: " + lastClaim.get(p.getName()));
            p.sendMessage(ChatColor.YELLOW + "Now entering: " + currentTeamName);
            lastClaim.remove(p.getName());
            lastClaim.put(p.getName(), currentTeamName);
        }

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        lastClaim.put(p.getName(), "§2Wilderness");
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (e.getBlock().getLocation().getY() < mineYLevel){
            if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                if (!(e.getBlock().getType().equals(Material.COAL_ORE) || e.getBlock().getType().equals(Material.IRON_ORE) ||
                        e.getBlock().getType().equals(Material.GOLD_ORE) || e.getBlock().getType().equals(Material.DIAMOND_ORE))) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void enderPearlThrown(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Location to = event.getTo();
            if (ClaimMGR.isInRespawnArea(to)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot throw an enderpearl to here");
            }
        }
    }

}
