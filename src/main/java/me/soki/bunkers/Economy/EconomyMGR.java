package me.soki.bunkers.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static me.soki.bunkers.Main.Main.getPlugin;
import static me.soki.bunkers.Main.Main.playersPlaying;

public class EconomyMGR implements Listener {
    public static HashMap<String, Integer> playerBalances = new HashMap<>();

    public static void sendBalMSG(Player sender) {
        Integer playersBal = playerBalances.get(sender.getName());
        String balMSG = ChatColor.YELLOW + "Your balance is: " + ChatColor.GREEN + "$" + playersBal.toString();
        sender.sendMessage(balMSG);
    }
    public static void giveMoney(Player toPlayer, Integer amount){
        Integer playerBal = playerBalances.get(toPlayer.getName());
        playerBalances.put(toPlayer.getName(), playerBal+amount);
    }
    public static void takeMoney(Player toPlayer, Integer amount){
        Integer playerBal = playerBalances.get(toPlayer.getName());
        playerBalances.put(toPlayer.getName(), playerBal-amount);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!playerBalances.containsKey(p.getName())){
            playerBalances.put(p.getName(), 0);
        }
    }

    public static void moneyFlow(){
        for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()){
            giveMoney(eachPlayer, 75);
        }
        new BukkitRunnable() {
            public void run() {
                if (getPlugin() == null){
                    cancel();
                }else {
                    for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
                        if (playersPlaying.contains(eachPlayer.getName())) {
                            giveMoney(eachPlayer, 2);
                        }
                    }
                }
            }
        }.runTaskTimer(getPlugin(), 0, 40);
    }
}
