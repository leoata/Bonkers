package me.soki.bunkers.Game;

import me.soki.bunkers.Koth.KothMGR;
import me.soki.bunkers.Main.Main;
import me.soki.bunkers.Teams.Team;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

import static me.soki.bunkers.Economy.EconomyMGR.moneyFlow;
import static me.soki.bunkers.Economy.EconomyMGR.playerBalances;
import static me.soki.bunkers.Koth.KothMGR.startKothReducer;
import static me.soki.bunkers.Main.Main.*;
import static me.soki.bunkers.Spectator.Spectator.enableSpec;

public class GameMGR {
    public static boolean isStarted = false;
    public static HashMap<String, Long> respawnTimer = new HashMap<>();
    public static HashMap<String, Integer> kills = new HashMap<>();
    public static HashMap<String, Long> homeTimer = new HashMap<>();
    public static HashMap<String, Location> homeLocation = new HashMap<String, Location>();


    public static void startCountdown() {
        for (int i = 30; i >= 0; i--) {
            String iString = Integer.toString(i);
            if (i == 30 || i == 20 || i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2 || i == 1) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(ChatColor.GRAY + "Game starting in " + ChatColor.AQUA + iString + ChatColor.GRAY + " seconds!");
                    }
                }, Math.abs(i - 30) * 20);
            }

        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(ChatColor.GRAY + "Game started!");
                        Main.chatMuted = false;
                        Bukkit.broadcastMessage(ChatColor.RED + "Chat is now unmuted.");
                        isStarted = true;
                        moneyFlow();
                        startKothReducer();

                        KothMGR.checkKoth = false;


                        for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
                            //startScoreboard(eachPlayer);
                            if (Team.getTeamFromPlayer(eachPlayer.getName()) != null) {
                                eachPlayer.setGameMode(GameMode.SURVIVAL);
                            } else {
                                enableSpec(eachPlayer);
                                eachPlayer.setGameMode(GameMode.CREATIVE);
                            }

                        }

                        GameStartEvent event = new GameStartEvent();
                        Bukkit.getPluginManager().callEvent(event);

                        for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
                            Team eachTeam = Team.getTeamFromPlayer(eachPlayer.getName());
                            if (eachTeam != null) {
                                playersPlaying.add(eachPlayer.getName());
                                eachPlayer.teleport(eachTeam.getTeamSpawn());
                            }
                            // Make the PlayerScoreboard information for the player

                            if (playersPlaying.contains(eachPlayer.getName())) {
                                eachPlayer.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
                                eachPlayer.getInventory().addItem(new ItemStack(Material.STONE_AXE));
                            }
                            //nametagColor(eachPlayer);
                        }


                    }
                },

                30 * 20
        );
    }

    public static void blockMined(Block block) {
        Material oreType = block.getType();
        block.setTypeId(Material.COBBLESTONE.getId());
        Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        block.setTypeId(oreType.getId(), true);
                    }
                },

                15 * 20
        );
    }


    public static String getDirectionString(Float yaw) {
        if (yaw >= 0) {
            if (yaw <= 22) {
                return "§cS";
            }
            if (yaw >= 338) {
                return "§cS";
            }
            if (yaw >= 22 && yaw <= 67) {
                return "§cS§eW";
            }
            if (yaw >= 290 && yaw <= 338) {
                return "§cS§aE";
            }
            if (yaw >= 67 && yaw <= 112) {
                return "§eW";
            }
            if (yaw >= 246 && yaw <= 290) {
                return "§aE";
            }
            if (yaw >= 158 && yaw <= 202) {
                return "§9N";
            }
            if (yaw >= 112 && yaw <= 157) {
                return "§9N§eW";
            }
            if (yaw >= 202 && yaw <= 246) {
                return "§9N§aE";
            }
        } else {
            if (yaw <= -338) {
                return "§cS";
            }
            if (yaw >= -22) {
                return "§cS";
            }
            if (yaw >= -338 && yaw <= -293) {
                return "§cS§eW";
            }
            if (yaw >= -70 && yaw <= -22) {
                return "§cS§aE";
            }
            if (yaw >= -293 && yaw <= -248) {
                return "§eW";
            }
            if (yaw >= -114 && yaw <= -70) {
                return "§aE";
            }
            if (yaw >= -202 && yaw <= -158) {
                return "§9N";
            }
            if (yaw >= -248 && yaw <= -203) {
                return "§9N§eW";
            }
            if (yaw >= -158 && yaw <= -114) {
                return "§9N§aE";
            }
        }
        return "";
    }
}
