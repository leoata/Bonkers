package me.soki.bunkers.Claim;

import me.soki.bunkers.Teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClaimListeners implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Team playersTeam = Team.getTeamFromPlayer(p.getName());
        if (!p.getGameMode().equals(GameMode.CREATIVE)) {
            if (playersTeam == null) {
                e.setCancelled(true);
            } else {
                if (ClaimMGR.whichClaimIsThisIn(e.getBlock().getLocation()) != null) {
                    Team claimsTeam = ClaimMGR.whichClaimIsThisIn(e.getBlock().getLocation());
                    if (!claimsTeam.getTeamName().equalsIgnoreCase(Team.getTeamFromPlayer(p.getName()).getTeamName())) {
                        if (claimsTeam.getDTR() != null) {
                            if (claimsTeam.getDTR() > 0) {
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.YELLOW + "You cannot build in " + claimsTeam.getColor() + claimsTeam.getTeamName() + "'s" + ChatColor.YELLOW + " territory!");
                            }
                        }else{
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.YELLOW + "You cannot build in " + claimsTeam.getColor() + claimsTeam.getTeamName() + "'s" + ChatColor.YELLOW + " territory!");
                        }
                    }
                }else{
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Team playersTeam = Team.getTeamFromPlayer(p.getName());
        if (!p.getGameMode().equals(GameMode.CREATIVE)) {
            if (playersTeam == null) {
                e.setCancelled(true);
            } else {
                if (ClaimMGR.whichClaimIsThisIn(e.getBlock().getLocation()) != null) {
                    Team claimsTeam = ClaimMGR.whichClaimIsThisIn(e.getBlock().getLocation());
                    if (!claimsTeam.getTeamName().equalsIgnoreCase(Team.getTeamFromPlayer(p.getName()).getTeamName())) {
                        if (claimsTeam.getDTR() != null) {
                            if (claimsTeam.getDTR() > 0) {
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.YELLOW + "You cannot build in " + claimsTeam.getColor() + claimsTeam.getTeamName() + "'s" + ChatColor.YELLOW + " territory!");
                            }
                            if (claimsTeam.getTeamName().equalsIgnoreCase("KOTH")) {
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.YELLOW + "You cannot build in " + claimsTeam.getColor() + claimsTeam.getTeamName() + "'s" + ChatColor.YELLOW + " territory!");
                            }
                        }else{
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.YELLOW + "You cannot build in " + claimsTeam.getColor() + claimsTeam.getTeamName() + "'s" + ChatColor.YELLOW + " territory!");
                        }
                    }
                }else{
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.YELLOW + "You cannot build in "  + ClaimMGR.whichClaimIsThisInStr(e.getBlock().getLocation()) + "'s" + ChatColor.YELLOW + " territory!");
                }

            }
        }
    }

    @EventHandler
    public void onFenceGateInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.getGameMode().equals(GameMode.CREATIVE)) {

            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock().getType().equals(Material.FENCE_GATE)) {
                        Team team = ClaimMGR.whichClaimIsThisIn(e.getClickedBlock().getLocation());
                        if (team != null) {
                            if (Team.getTeamFromPlayer(p.getName()) == null) {
                                e.setCancelled(true);
                            } else {
                                if (!team.getTeamName().equalsIgnoreCase(Team.getTeamFromPlayer(p.getName()).getTeamName())) {
                                    if (p.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
                                        if (p.isSneaking()) {
                                            e.setCancelled(false);
                                        }else{
                                            e.setCancelled(true);
                                        }
                                    } else {
                                        e.setCancelled(true);
                                        p.sendMessage(ChatColor.YELLOW + "You cannot interact in " + team.getColor() + team.getTeamName() + "'s" + ChatColor.YELLOW + " territory!");
                                    }
                                }
                            }
                        } else {
                            e.setCancelled(false);
                        }

                    }
                }
            }

        }
}
