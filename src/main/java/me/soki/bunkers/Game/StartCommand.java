package me.soki.bunkers.Game;

import me.soki.bunkers.Main.Main;
import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Arena.ArenaMGR.*;
import static me.soki.bunkers.Economy.EconomyMGR.giveMoney;
import static me.soki.bunkers.Game.GameMGR.*;


public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (p.isOp()){
        //if (p.getUniqueId().equals(UUID.fromString("912a9407-8592-4a58-ad66-dc2d10e81033")) || p.getUniqueId().equals(UUID.fromString("23d83f86-12d0-4068-8fda-5dda8d912ce1"))) {
            if (isStarted){
                p.sendMessage(ChatColor.RED + "Game already started.");
            }else{
                boolean isReady = true;
                boolean playerCountReady = true;
                if (Team.getTeam("Blue").getPlayers().size() == 5 && Team.getTeam("Red").getPlayers().size() == 5 &&
                Team.getTeam("Green").getPlayers().size() == 5 && Team.getTeam("Yellow").getPlayers().size() == 5){
                    playerCountReady = true;
                }

                if (arenaCenter == null && arenaPos2 == null && arenaPos1 == null) {
                    isReady = false;
                }
                for (Team eachTeam : Team.teams.values()){
                    if (eachTeam.getTeamName().equalsIgnoreCase("KOTH")) {
                        if (eachTeam.getTeamClaimPos1() == null || eachTeam.getTeamClaimPos2() == null) {
                            isReady = false;
                        }
                    }else{
                            if (eachTeam.getTeamSpawn() == null || eachTeam.getTeamClaimPos1() == null || eachTeam.getTeamClaimPos2() == null){
                                isReady = false;
                            }
                        }

                }

                if (isReady && playerCountReady) {
                    for (Player allPlayers : Bukkit.getServer().getOnlinePlayers()) {
                        allPlayers.sendMessage(ChatColor.YELLOW + "The game is starting!");
                        allPlayers.sendMessage(ChatColor.RED + "Chat is now muted for 30 seconds.");
                    }
                    Main.chatMuted = true;
                    startCountdown();
                }else{
                    p.sendMessage(ChatColor.RED + "Game is not ready.");
                }
            }
        }
        return true;
    }
}
