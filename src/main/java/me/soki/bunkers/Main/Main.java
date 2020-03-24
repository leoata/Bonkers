package me.soki.bunkers.Main;

import fr.mrmicky.fastboard.FastBoard;
import me.soki.bunkers.Arena.ArenaCommand;
import me.soki.bunkers.Arena.ArenaListeners;
import me.soki.bunkers.Claim.ClaimListeners;
import me.soki.bunkers.Economy.BalanceCommand;
import me.soki.bunkers.Economy.EconomyMGR;
import me.soki.bunkers.Economy.PayCommand;
import me.soki.bunkers.Game.GameListeners;
import me.soki.bunkers.Game.PearlCooldown;
import me.soki.bunkers.Game.StartCommand;
import me.soki.bunkers.Koth.KothListeners;
import me.soki.bunkers.Spectator.SpecCommand;
import me.soki.bunkers.Spectator.Spectator;
import me.soki.bunkers.Teams.Command.TeamCommand;
import me.soki.bunkers.Teams.Team;
import me.soki.bunkers.Verification.Verification;
import me.soki.bunkers.Villagers.SetVillagerCommand;
import me.soki.bunkers.Villagers.VillagerListener;
import me.soki.bunkers.azazel.Azazel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.util.*;

import static me.soki.bunkers.Arena.ArenaMGR.*;
import static me.soki.bunkers.Teams.TeamMGR.setupTeams;

public class Main extends JavaPlugin {

    public final static OS OPERATING_SYSTEM = isUnix(System.getProperty("os.name").toLowerCase())? OS.LINUX : OS.WINDOWS;
    public final static String DRIVE = OPERATING_SYSTEM == OS.WINDOWS ? System.getProperty("user.dir").substring(0,1) : null;

    public static List<String> blueTeamPlayers = new ArrayList<>();
    public static List<String> redTeamPlayers = new ArrayList<>();
    public static List<String> greenTeamPlayers = new ArrayList<>();
    public static List<String> yellowTeamPlayers = new ArrayList<>();
    public static Map<UUID, FastBoard> boards = new HashMap<>();
    public static List<String> playersPlaying = new ArrayList<>();
    public static Location lobbyLoc;
    public static int gameTime = 0;

    public static Scoreboard sb;
    public static org.bukkit.scoreboard.Team red;
    public static org.bukkit.scoreboard.Team yellow;
    public static org.bukkit.scoreboard.Team green;
    public static org.bukkit.scoreboard.Team blue;

    private static Plugin plugin;

    public static boolean chatMuted = false;
    public static void setConfigFile(File configFile) {
        configFile = configFile;
    }

    public static void setConfigCfg(FileConfiguration configCfg) {
        configCfg = configCfg;
    }

    /*TODO

    actually check if game is ready in start command

    death messages

    scoreboard flicker

    chat mute doesnt work
    */


    @Override
    public void onEnable() {

        new Verification();


        setupTeams();

        chatMuted = false;

        plugin = this;
        plugin.saveDefaultConfig();




        getCommand("team").setExecutor(new TeamCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("spec").setExecutor(new SpecCommand());
        getCommand("setvillager").setExecutor(new SetVillagerCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("balance").setExecutor(new BalanceCommand());

        registerEvents(this, new WelcomeMessage(), new ChatUtil(), new ArenaListeners(), new GameListeners(),
                new Spectator(), new ClaimListeners(), new VillagerListener(), new EconomyMGR(), new KothListeners(), new PearlCooldown());

        /*
        glaedr = new Glaedr(this, ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Bunkers");
        glaedr.getBottomWrappers().add("&7&m--------------------");
        glaedr.getTopWrappers().add("&7&m--------------------");
        glaedr.registerPlayers();


         */

        //setupBukkitTeams(sb);


        new Azazel(this, new MyTabAdapter());
        try {
            arenaPos1 = convertLocation("arena.pos1");
            arenaPos2 = convertLocation("arena.pos2");
            arenaCenter = convertLocation("arena.center");
            lobbyLoc = convertLocation("spawn");
            Team.getTeam("KOTH").setTeamClaimPos1(convertLocation("arena.kothpos1"));
            Team.getTeam("KOTH").setTeamClaimPos2(convertLocation("arena.kothpos2"));
            capzonePos1 = convertLocation("arena.capzonepos1");
            capzonePos2 = convertLocation("arena.capzonepos2");
            mineYLevel = Integer.valueOf(plugin.getConfig().getString("arena.mineylevel"));
        } catch (Exception e) {
            System.out.println("Arena locations not saved from previous session. Not loading them");
        }
        for (Team eachTeam : Team.teams.values()) {
            try {
                if (!eachTeam.getTeamName().equalsIgnoreCase(Team.getTeam("KOTH").getTeamName())) {
                    eachTeam.setTeamClaimPos1(convertLocation(eachTeam.getTeamName().toLowerCase() + ".pos1"));
                    eachTeam.setTeamClaimPos2(convertLocation(eachTeam.getTeamName().toLowerCase() + ".pos2"));
                    eachTeam.setTeamRespawnPos1(convertLocation(eachTeam.getTeamName().toLowerCase() + ".respawnpos2"));
                    eachTeam.setTeamRespawnPos2(convertLocation(eachTeam.getTeamName().toLowerCase() + ".respawnpos2"));
                    eachTeam.setTeamSpawn(convertLocation(eachTeam.getTeamName().toLowerCase() + ".spawn"));
                }
            } catch (Exception e) {
                System.out.println(eachTeam.getTeamName() + "'s team locations not saved from previous session. Not loading them");
            }
        }

        for (World w : Bukkit.getWorlds()) {
            w.setStorm(false);
            w.setThundering(false);
            w.setWeatherDuration(999999999);
        }






    }

    @Override
    public void onDisable() {
        plugin = null;
        for (Player allPlayers : Bukkit.getServer().getOnlinePlayers()) {
            allPlayers.kickPlayer(ChatColor.RED + "Server Reloading");
        }

        saveConfig();

    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    public static Location convertLocation(final String path) {
        List<Integer> xyz = new ArrayList<Integer>();
        for (String eachCoord : plugin.getConfig().getStringList(path)) {
            xyz.add(Integer.valueOf(eachCoord));
        }
        return new Location(Bukkit.getWorld("world"), (double)xyz.get(0), (double)xyz.get(1), (double)xyz.get(2));
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static boolean isUnix(String os_name) {
        return (os_name.contains("nix") || os_name.contains("nux") || os_name.contains("aix"));
    }


}
