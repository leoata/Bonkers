package me.soki.bunkers.Claim;

import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import static me.soki.bunkers.Arena.ArenaMGR.capzonePos1;
import static me.soki.bunkers.Arena.ArenaMGR.capzonePos2;

public class ClaimMGR {

    public static Team whichClaimIsThisIn(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        for (Team eachTeam : Team.teams.values()) {
            Location l1 = eachTeam.getTeamClaimPos1();
            Location l2 = eachTeam.getTeamClaimPos2();
            if (l1 == null || l2 == null){
                return null;
            }
            int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
            int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
            int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
            int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
            int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
            int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2){
                return eachTeam;
            }
        }
        return null;
    }

    public static boolean isInRespawnArea(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        for (Team eachTeam : Team.teams.values()) {
            Location l1 = eachTeam.getTeamRespawnPos1();
            Location l2 = eachTeam.getTeamRespawnPos2();
            if (l1 == null || l2 == null){
                return false;
            }
            int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
            int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
            int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
            int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
            int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
            int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2){
                return true;
            }
        }
        return false;
    }

    public static boolean isOnCap(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        Location l1 = capzonePos1;
        Location l2 = capzonePos2;
        int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2){
            return true;
        }else{
            return false;
        }
    }
    public static String whichClaimIsThisInStr(Location loc){
        if (ClaimMGR.whichClaimIsThisIn(loc) == null){
            return "§cWarzone";
        }else{
            return ClaimMGR.whichClaimIsThisIn(loc).getColor() + ClaimMGR.whichClaimIsThisIn(loc).getTeamName();
        }
    }
}
