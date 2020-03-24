package me.soki.bunkers.Teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team {

    private List<String> players = new ArrayList<>();
    private ChatColor color;
    private String teamName;
    private String leader;
    private Integer DTR;
    private Location teamSpawn;
    private Location teamClaimPos1;
    private Location teamClaimPos2;
    private Location teamRespawnPos1;
    private Location teamRespawnPos2;
    public static HashMap<String, Team> teams = new HashMap<>();


    public Team(ChatColor color, List<String> players, String teamName, Integer DTR, String leader){
        this.color = color;
        this.players = players;
        this.teamName = teamName;
        this.DTR = DTR;
        this.leader = leader;
        teams.put(teamName, this);


    }

    public ChatColor getColor() {
        return color;
    }

    public List<String> getPlayers() {
        return players;
    }
    public void addPlayer(String playerName){
        players.add(playerName);

    }
    public void removePlayer(String playerName){
        players.remove(playerName);
    }

    public Integer getDTR() {
        return DTR;
    }


    public String getDTRWithColor(){
        switch(getDTR()){
            case 6:
                return "§a6.0";
            case 5:
                return "§a5.0";
            case 4:
                return "§a4.0";
            case 3:
                return "§e3.0";

            case 2:
                return "§e2.0";

            case 1:
                return "§c1.0";

            case 0:
                return "§40.0";

            default:
                return "§a6.0";



        }
    }
    public void removeOneDTR(){
        DTR -= 1;
    }

    public String getTeamName() {
        return teamName;
    }
    public static boolean isInTeam(String playerName){
        for (Team eachTeam : teams.values()){
            if (eachTeam.players.contains(playerName)){
                return true;
            }
        }
        return false;
    }

    public List<String> getOnlinePlayers(Team team){
        List<String> onlinePlayers = new ArrayList<>();
        for (String eachTeammate : team.getPlayers()){
            Player eachPlayer = Bukkit.getPlayer(eachTeammate);
            if (eachPlayer != null){
                onlinePlayers.add(eachPlayer.getName());
            }
        }
        return onlinePlayers;
    }

    public void setTeamClaimPos1(Location teamClaimPos1) {
        this.teamClaimPos1 = teamClaimPos1;
    }

    public void setTeamSpawn(Location teamSpawn) {
        this.teamSpawn = teamSpawn;
    }

    public Location getTeamSpawn() {
        return teamSpawn;
    }

    public Location getTeamClaimPos1() {
        return teamClaimPos1;
    }

    public Location getTeamClaimPos2() {
        return teamClaimPos2;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeader() {
        return leader;
    }

    public void setTeamClaimPos2(Location teamClaimPos2) {
        this.teamClaimPos2 = teamClaimPos2;
    }

    public void setTeamRespawnPos1(Location teamRespawnPos1) {
        this.teamRespawnPos1 = teamRespawnPos1;
    }

    public void setTeamRespawnPos2(Location teamRespawnPos2) {
        this.teamRespawnPos2 = teamRespawnPos2;
    }

    public Location getTeamRespawnPos1() {
        return teamRespawnPos1;
    }

    public Location getTeamRespawnPos2() {
        return teamRespawnPos2;
    }

    public static Team getTeam(String teamName){
        return teams.get(teamName);
    }
    public static Team getTeamFromPlayer(String playerName){
        for (Team eachTeam : teams.values()){
            if (!eachTeam.getTeamName().equalsIgnoreCase("KOTH")) {
                if (!eachTeam.getPlayers().isEmpty()) {
                    for (String eachPlayer : eachTeam.getPlayers()) {
                        if (eachPlayer.equalsIgnoreCase(playerName)) {
                            return eachTeam;
                        }
                    }
                }
            }
        }
        return null;
    }

}
