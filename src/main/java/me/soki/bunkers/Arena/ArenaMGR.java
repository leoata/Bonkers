package me.soki.bunkers.Arena;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ArenaMGR {

    public static Location arenaPos1;
    public static Location arenaPos2;
    public static Location arenaCenter;
    public static int mineYLevel;
    public static Location capzonePos1;
    public static Location capzonePos2;

    public static HashMap<String, String> lastClaim = new HashMap<>();
    //name, last claim name
    public static void sendHelp(Player p){
        p.sendMessage("&c&m-------------------------".replace("&", "§"));
        p.sendMessage("&c  » /arena setspawn <teamName>".replace("&", "§"));
        p.sendMessage("&c  » /arena pos1".replace("&", "§"));
        p.sendMessage("&c  » /arena pos2".replace("&", "§"));
        p.sendMessage("&c  » /arena setcenter".replace("&", "§"));
        p.sendMessage("&c  » /arena setclaimpos1 <teamName>".replace("&", "§"));
        p.sendMessage("&c  » /arena setclaimpos2 <teamName>".replace("&", "§"));
        p.sendMessage("&c  » /arena mineylevel <ylevel>".replace("&", "§"));
        p.sendMessage("&c  » /arena kothpos1".replace("&", "§"));
        p.sendMessage("&c  » /arena kothpos2".replace("&", "§"));
        p.sendMessage("&c  » /arena capzonepos1".replace("&", "§"));
        p.sendMessage("&c  » /arena capzonepos2".replace("&", "§"));
        p.sendMessage("&c&m-------------------------".replace("&", "§"));
    }
}
