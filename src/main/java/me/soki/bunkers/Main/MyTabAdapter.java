package me.soki.bunkers.Main;

import me.soki.bunkers.azazel.tab.TabAdapter;
import me.soki.bunkers.azazel.tab.TabTemplate;
import me.soki.bunkers.Claim.ClaimMGR;
import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.soki.bunkers.Game.GameMGR.*;
import static me.soki.bunkers.Main.Main.playersPlaying;

public class MyTabAdapter implements TabAdapter {

    public TabTemplate getTemplate(Player player) {
        TabTemplate template = new TabTemplate();
        int amountOnline = 0;
        for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
            amountOnline += 1;
        }
        String direction = getDirectionString(player.getLocation().getYaw());


        if (isStarted) {

            Team playersTeam = Team.getTeamFromPlayer(player.getName());
            template.left("§3§lserver.ip");
            template.left("");


            if (playersTeam != null) {
                template.left("§b§lTeam Info");
                template.left("DTR: " + playersTeam.getDTRWithColor());

                template.left("Online: " + playersTeam.getOnlinePlayers(playersTeam).size() + "/" + playersTeam.getPlayers().size());
                template.left("");
            }
            template.left("§b§lLocation");
            template.left(ClaimMGR.whichClaimIsThisInStr(player.getLocation()));
            template.left("(" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockZ() + ")" + "§7[" + direction + "§7]");
            template.left("");
            template.left("§3§lGame Info");
            template.left("§bMap: §fCongeal");
            template.left("§bPlayers: §f" + amountOnline);
            template.left("");
            template.left("§b§lDTR");
            template.left("§cRed§f: " + Team.getTeam("Red").getDTRWithColor());
            template.left("§9Blue§f: " + Team.getTeam("Blue").getDTRWithColor());
            template.left("§aGreen§f: " + Team.getTeam("Green").getDTRWithColor());
            template.left("§eYellow§f: " + Team.getTeam("Yellow").getDTRWithColor());


            template.middle("§3§lBunkers");
            template.middle("");
            template.middle("§e§lYellow Team");
            for (String eachPlayer : Team.getTeam("Yellow").getPlayers()) {
                if (!playersPlaying.contains(player.getName())){
                    template.middle("§e§m" + eachPlayer);
                }else {
                    if (respawnTimer.containsKey(eachPlayer)) {
                        double secondsleft = ((respawnTimer.get(eachPlayer) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                        String secondsleftStr = Double.toString(secondsleft).substring(0, Double.toString(secondsleft).indexOf("."));
                        template.middle("§7" + secondsleftStr + " §e§m" + eachPlayer);
                    } else {
                        template.middle("§e" + eachPlayer);
                    }
                }
            }
            template.middle("");
            template.middle("§9§lBlue Team");
            for (String eachPlayer : Team.getTeam("Blue").getPlayers()) {
                if (!playersPlaying.contains(player.getName())){
                    template.middle("§9§m" + eachPlayer);
                }else {
                    if (respawnTimer.containsKey(eachPlayer)) {
                        double secondsleft = ((respawnTimer.get(eachPlayer) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                        String secondsleftStr = Double.toString(secondsleft).substring(0, Double.toString(secondsleft).indexOf("."));
                        template.middle("§7" + secondsleftStr + " §9§m" + eachPlayer);
                    } else {
                        template.middle("§9" + eachPlayer);
                    }
                }
            }


            template.right("§3§lserver.ip");
            template.right("");
            template.right("§c§lRed Team");
            for (String eachPlayer : Team.getTeam("Red").getPlayers()) {
                if (!playersPlaying.contains(player.getName())){
                    template.right("§c§m" + eachPlayer);
                }else {
                    if (respawnTimer.containsKey(eachPlayer)) {
                        double secondsleft = ((respawnTimer.get(eachPlayer) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                        String secondsleftStr = Double.toString(secondsleft).substring(0, Double.toString(secondsleft).indexOf("."));
                        template.right("§7" + secondsleftStr + " §c§m" + eachPlayer);
                    } else {
                        template.right("§c" + eachPlayer);
                    }
                }
            }
            template.right("");
            template.right("§a§lGreen Team");
            for (String eachPlayer : Team.getTeam("Green").getPlayers()) {
                if (!playersPlaying.contains(player.getName())){
                    template.right("§a§m" + eachPlayer);
                }else {
                    if (respawnTimer.containsKey(eachPlayer)) {
                        double secondsleft = ((respawnTimer.get(eachPlayer) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                        String secondsleftStr = Double.toString(secondsleft).substring(0, Double.toString(secondsleft).indexOf("."));
                        template.right("§7" + secondsleftStr + " §a§m" + eachPlayer);
                    } else {
                        template.right("§a" + eachPlayer);
                    }
                }
            }
        } else {
            template.left("§3§lserver.ip");
            template.left("");
            template.left("§3§lLocation");
            template.left("§7Lobby");
            template.left("(" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockZ() + ")" + "§7[" + direction + "§7]");
            template.left("");
            template.left("§3§lGame Info");
            template.left("§bMap: §fCongeal");
            template.left("§bPlayers: §f" + amountOnline);

            template.middle("§3§lBunkers");
            template.middle("");
            template.middle("§e§lYellow Team");
            for (String eachPlayer : Team.getTeam("Yellow").getPlayers()) {
                template.middle("§e" + eachPlayer);
            }
            template.middle("");
            template.middle("§9§lBlue Team");
            for (String eachPlayer : Team.getTeam("Blue").getPlayers()) {
                template.middle("§9" + eachPlayer);
            }


            template.right("§3§lserver.ip");
            template.right("");
            template.right("§c§lRed Team");
            for (String eachPlayer : Team.getTeam("Red").getPlayers()) {
                template.right("§c" + eachPlayer);
            }
            template.right("");
            template.right("§a§lGreen Team");
            for (String eachPlayer : Team.getTeam("Green").getPlayers()) {
                template.right("§a" + eachPlayer);
            }

        }
        return template;

    }
}
