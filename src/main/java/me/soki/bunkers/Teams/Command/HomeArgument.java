package me.soki.bunkers.Teams.Command;

import me.soki.bunkers.Game.GameMGR;
import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.soki.bunkers.Game.GameMGR.*;
import static me.soki.bunkers.Main.Main.getPlugin;
import static me.soki.bunkers.Main.Main.playersPlaying;
import static me.soki.bunkers.Teams.TeamMGR.sendHelp;

public class HomeArgument {
    public static void Home(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1){
                Team playersTeam = Team.getTeamFromPlayer(p.getName());
                if (playersPlaying.contains(p.getName())) {
                    if (playersTeam != null) {
                        if (playersTeam.getTeamSpawn() != null) {
                            homeTimer.put(p.getName(), System.currentTimeMillis());
                            homeLocation.put(p.getName(), p.getLocation());

                            Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {
                                        @Override
                                        public void run() {
                                            if (homeLocation.containsKey(p.getName())) {
                                                if (homeTimer.containsKey(p.getName())) {
                                                    if (homeLocation.get(p.getName()).getBlockX() == p.getLocation().getBlockX()) {
                                                        if (homeLocation.get(p.getName()).getBlockY() == p.getLocation().getBlockY()) {
                                                            if (homeLocation.get(p.getName()).getBlockZ() == p.getLocation().getBlockZ()) {
                                                                if (Math.abs(homeTimer.get(p.getName()) - (System.currentTimeMillis() - 10 * 1000)) <= 20) {
                                                                    p.teleport(playersTeam.getTeamSpawn());
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    },

                                    10 * 20
                            );
                            p.sendMessage(ChatColor.BLUE + "Started your " + ChatColor.BOLD + ChatColor.BLUE + "home " +ChatColor.BLUE + "timer.");
                        } else {
                            p.sendMessage(ChatColor.RED + "There was an error with the arena. Contact an administrator.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "You aren't in a team");
                    }

                } else {
                    p.sendMessage(ChatColor.RED + "You aren't playing!");
                }
        }else{
            sendHelp(p);
        }
    }
}
