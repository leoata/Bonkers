package me.soki.bunkers.Koth;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static me.soki.bunkers.Game.GameMGR.isStarted;
import static me.soki.bunkers.Main.Main.getPlugin;
import static me.soki.bunkers.Main.Main.lobbyLoc;

public class KothMGR {
    public static String kothCapper = "";
    public static Integer kothMaxTime = 300;
    public static double currentKothTime = 300;

    public static boolean checkKoth = true;

    public static BukkitTask runnable = new BukkitRunnable() {
        @Override
        public void run() {
            //methods
            if (checkKoth) {
                for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
                    if (kothCapper.equalsIgnoreCase(eachPlayer.getName())) {
                        if (currentKothTime == 0 && isStarted) {
                            isStarted = false;
                            Team capperTeam = Team.getTeamFromPlayer(kothCapper);
                            eachPlayer.sendMessage(ChatColor.RED + "Game Over! " + capperTeam.getColor() + capperTeam.getTeamName() + ChatColor.RED + " won!");
                            eachPlayer.sendMessage(ChatColor.RED + "Game Over! " + capperTeam.getColor() + capperTeam.getTeamName() + ChatColor.RED + " won!");
                            eachPlayer.sendMessage(ChatColor.RED + "Game Over! " + capperTeam.getColor() + capperTeam.getTeamName() + ChatColor.RED + " won!");
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
                                                eachPlayer.teleport(lobbyLoc);
                                            }
                                        }
                                    }, 400);

                        } else if (currentKothTime != 0 && isStarted){
                            currentKothTime -= 1;
                        }
                    }else{
                        currentKothTime = kothMaxTime;
                    }
                }
            } else {
                currentKothTime = kothMaxTime;
            }
        }
    }.runTaskTimer(getPlugin(), 0, 20);


    public static void startKothReducer() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            public void run() {
                kothMaxTime = 240;
                if (currentKothTime > 240) {
                    currentKothTime = 240;
                }
                Bukkit.broadcastMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.BLUE + "KOTH's" + ChatColor.YELLOW + " capture time has been reduced to 4:00 minutes");
            }
        }, 30000L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            public void run() {
                kothMaxTime = 180;
                if (currentKothTime > 180) {
                    currentKothTime = 180;
                }
                Bukkit.broadcastMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.BLUE + "KOTH's" + ChatColor.YELLOW + " capture time has been reduced to 3:00 minutes");
            }

        }, 42000L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            public void run() {
                kothMaxTime = 120;
                if (currentKothTime > 120) {
                    currentKothTime = 120;
                }
                Bukkit.broadcastMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.BLUE + "KOTH's" + ChatColor.YELLOW + " capture time has been reduced to 2:00 minutes");
            }
        }, 54000L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            public void run() {
                kothMaxTime = 90;
                if (currentKothTime > 90) {
                    currentKothTime = 90;
                }
                Bukkit.broadcastMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.BLUE + "KOTH's" + ChatColor.YELLOW + " capture time has been reduced to 1:30 minutes");
            }
        }, 60000L);
    }
}
