package me.soki.bunkers.Game;

import fr.mrmicky.fastboard.FastBoard;
import me.soki.bunkers.Koth.KothMGR;
import me.soki.bunkers.Main.Main;
import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static me.soki.bunkers.Arena.ArenaMGR.arenaCenter;
import static me.soki.bunkers.Economy.EconomyMGR.playerBalances;
import static me.soki.bunkers.Game.GameMGR.*;
import static me.soki.bunkers.Main.Main.*;
import static me.soki.bunkers.Spectator.Spectator.enableSpec;

public class GameListeners implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (isStarted) {
            Team playersTeam = Team.getTeamFromPlayer(p.getName());
            if (playersTeam == null) {
                p.teleport(arenaCenter);
                enableSpec(p);
                //put in spec
            } else {
                if (playersTeam.getDTR() > 0) {
                    p.teleport(playersTeam.getTeamSpawn());
                } else {
                    p.teleport(arenaCenter);
                    enableSpec(p);
                    //put in spec
                }
            }
            if (respawnTimer.containsKey(p.getName())) {
                double secondsleft = ((respawnTimer.get(p.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                if (secondsleft > 0) {
                    p.setGameMode(GameMode.ADVENTURE);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        FastBoard board = boards.remove(p.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (isStarted) {
            if (respawnTimer.containsKey(p.getName())) {
                double secondsleft = ((respawnTimer.get(p.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                if (secondsleft < 0) {
                    respawnTimer.remove(p.getName());
                }
                Team playersTeam = Team.getTeamFromPlayer(p.getName());
                if (playersTeam != null) {
                    if (p.getLocation().getBlockX() != playersTeam.getTeamSpawn().getBlockX() ||
                            p.getLocation().getBlockZ() != playersTeam.getTeamSpawn().getBlockZ()) {
                        p.teleport(playersTeam.getTeamSpawn());
                    }
                } else {
                    respawnTimer.remove(p.getName());
                    p.teleport(arenaCenter);
                    //put in spec
                }
            }
            if (homeTimer.containsKey(p.getName())) {
                Team playersTeam = Team.getTeamFromPlayer(p.getName());
                if (p.getLocation().getBlockX() != homeLocation.get(p.getName()).getBlockX() ||
                        p.getLocation().getBlockZ() != homeLocation.get(p.getName()).getBlockZ() ||
                        p.getLocation().getBlockY() != homeLocation.get(p.getName()).getBlockY()
                ) {
                    homeTimer.remove(p.getName());
                    homeLocation.remove(p.getName());
                    //homeEntry.get(p.getName()).cancel();
                    //homeEntry.remove(p.getName());

                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (isStarted) {
            Team playersTeam = Team.getTeamFromPlayer(p.getName());
            if (playersTeam != null) {
                for (String playerStr : playersTeam.getPlayers()) {
                    Player teamMember = Bukkit.getPlayer(playerStr);
                    teamMember.sendMessage(ChatColor.RED + "Teammate Death: " + ChatColor.WHITE + p.getName());
                    teamMember.sendMessage(ChatColor.YELLOW + "DTR: " + playersTeam.getDTR() + ".0 -> " + (playersTeam.getDTR() - 1) + ".0");
                }
                if (playersTeam.getDTR() >= 1) {

                    respawnTimer.put(p.getName(), System.currentTimeMillis());

                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    respawnTimer.remove(p.getName());
                                    p.sendMessage(ChatColor.RED + "You have respawned!");
                                }
                            },

                            30000
                    );
                    playersTeam.removeOneDTR();
                } else {
                    p.sendMessage(ChatColor.RED + "You are now raidable and will not respawn!");
                    enableSpec(p);
                    List<String> teamsAlive = new ArrayList<>();
                    for (String eachPlayerName : playersPlaying) {
                        Player player = Bukkit.getPlayer(eachPlayerName);
                        Team eachPlayersTeam = Team.getTeamFromPlayer(player.getName());
                        if (!teamsAlive.contains(eachPlayersTeam.getTeamName())) {
                            teamsAlive.add(eachPlayersTeam.getTeamName());
                        }
                    }
                    playersPlaying.remove(p.getName());
                    for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {

                        if (teamsAlive.size() == 1) {
                            isStarted = false;
                            eachPlayer.sendMessage(ChatColor.RED + "Game Over! " + Team.getTeam(teamsAlive.get(0)).getColor() + Team.getTeam(teamsAlive.get(0)).getTeamName() + ChatColor.RED + " won!");
                            eachPlayer.sendMessage(ChatColor.RED + "Game Over! " + Team.getTeam(teamsAlive.get(0)).getColor() + Team.getTeam(teamsAlive.get(0)).getTeamName() + ChatColor.RED + " won!");
                            eachPlayer.sendMessage(ChatColor.RED + "Game Over! " + Team.getTeam(teamsAlive.get(0)).getColor() + Team.getTeam(teamsAlive.get(0)).getTeamName() + ChatColor.RED + " won!");
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
                                                eachPlayer.teleport(lobbyLoc);
                                            }
                                        }
                                    }, 400);
                        }
                    }
                }
            }
        }
        if (!isStarted) {
            e.getDrops().clear();
        }

        EntityDamageEvent ede = p.getLastDamageCause();
        EntityDamageEvent.DamageCause dc = ede.getCause();
        Team playersTeam = Team.getTeamFromPlayer(p.getName());

        String playerColor = "§f";
        String killerColor = "§f";
        if (playersTeam != null) {
            playerColor = playersTeam.getColor().toString();
        }
        if (p.getKiller() != null) {
            Team killerTeam = Team.getTeamFromPlayer(p.getKiller().getName());
            if (killerTeam != null) {
                killerColor = killerTeam.getColor().toString();
            }
        }
        int currentKillsKiller;
        if (!kills.containsKey(p.getKiller().getName())) {
            currentKillsKiller = 1;
            kills.put(p.getKiller().getName(), 0);
        }
        currentKillsKiller = kills.get(p.getKiller().getName());
        kills.remove(p.getKiller().getName());
        kills.put(p.getKiller().getName(), currentKillsKiller + 1);

        int currentKillsVictim;
        if (kills.get(p.getName()) == null) {
            currentKillsVictim = 1;
            kills.put(p.getName(), 0);
        }
        currentKillsVictim = kills.get(p.getName());
        kills.remove(p.getName());
        kills.put(p.getName(), currentKillsVictim + 1);
        ItemStack sword = p.getKiller().getItemInHand();
        String swordNameNullCheck = sword.getItemMeta().getDisplayName() == null ? sword.getType().name().toLowerCase().replace("_", " ") : sword.getItemMeta().getDisplayName();
        e.setDeathMessage("");
        if (dc.equals(EntityDamageEvent.DamageCause.FALL)) {
            if (p.getKiller() == null) {
                Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7] §efell from a high place");
            } else {
                Bukkit.broadcastMessage(killerColor + p.getKiller().getName() + "§7[§8" + currentKillsKiller + "§7]§e hit " + playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7]§e off of a cliff.");
            }
        } else if (dc.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            if (p.getKiller() != null) {
                Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7]§e was slain by " + killerColor
                        + p.getKiller().getName() + "§7[§8" + currentKillsKiller + "§7]§e using §c" + swordNameNullCheck);
            } else {
                Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7]§e died.");
            }
        } else if (dc.equals(EntityDamageEvent.DamageCause.FIRE)) {
            if (p.getKiller() == null) {
                Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7] §eburned to death.");
            } else {
                Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7]§e burned to death whilst fighting "
                        + killerColor + p.getKiller().getName() + "§7[§8" + currentKillsKiller + "§7]§e.");
            }
        } else if (dc.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            if (p.getKiller() != null) {
                Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7]§e was shot by " + killerColor
                        + p.getKiller().getName() + "§7[§8" + currentKillsKiller + "§7]§e using §c" + swordNameNullCheck);
            } else {
                Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7]§e died.");
            }
        } else {
            Bukkit.broadcastMessage(playerColor + p.getName() + "§7[§8" + currentKillsVictim + "§7]§e died.");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        FastBoard board = new FastBoard(p);
        board.updateTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "Bunkers");
        boards.put(p.getUniqueId(), board);

        //nametagColor(p);
        if (!isStarted) {
            if (lobbyLoc != null) {
                p.teleport(lobbyLoc);
            }
        } else {
            if (!kills.containsKey(p.getName())) {
                kills.put(p.getName(), 0);
            }
        }

    }

    @EventHandler
    public void onGameStart(GameStartEvent e) {
        new BukkitRunnable() {
            public void run() {
                gameTime++;
                for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()) {
                    Team playersTeam = Team.getTeamFromPlayer(eachPlayer.getName());
                    Collection<String> text = new ArrayList<>();
                    String kothTimeStrMin = String.valueOf(KothMGR.currentKothTime / 60.0);
                    String kothTimeStrSec = String.valueOf(KothMGR.currentKothTime % 60.0).substring(0, String.valueOf(KothMGR.currentKothTime % 60.0).indexOf("."));
                    String kothTimeStrSecF = (kothTimeStrSec.length() == 1) ? ("0" + kothTimeStrSec) : kothTimeStrSec;
                    String kothTimeStr = "0" + kothTimeStrMin.substring(0, kothTimeStrMin.indexOf(".")) + ":" + kothTimeStrSecF;
                    String gameTimeStrSec = (gameTime % 60 < 10) ? ("0" + gameTime % 60) : String.valueOf(gameTime % 60);
                    String gameTimeStr = ((gameTime / 60 >= 10) ? "" : "0") + gameTime / 60 + ":" + gameTimeStrSec;
                    String gameTimeStrF = (gameTime > 59) ? (gameTimeStr + "m") : (gameTime + "s");
                    String kothTimeStrF = (KothMGR.currentKothTime > 60.0) ? (kothTimeStr + "m") : (KothMGR.currentKothTime + "s");
                    text.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------");
                    text.add(ChatColor.AQUA + "Game Time" + ChatColor.RED + ": " + gameTimeStrF);
                    text.add(ChatColor.BLUE + "KOTH" + ChatColor.RED + ": " + kothTimeStrF);
                    if (playersTeam != null){
                        text.add(ChatColor.YELLOW + "Team" + ChatColor.RED + ": " + playersTeam.getColor() + playersTeam.getTeamName());
                        text.add(ChatColor.DARK_RED + "DTR" +ChatColor.RED + ": " + playersTeam.getDTR() + ".0");
                        text.add(ChatColor.GREEN + "Balance" +ChatColor.RED + ": $" + playerBalances.get(eachPlayer.getName()));
                    }
                    text.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------");
                    boards.get(eachPlayer.getUniqueId()).updateLines(text);
                }
            }
        }.runTaskTimer(getPlugin(), 1, 20);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!isStarted) {
            if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        } else {
            if (respawnTimer.containsKey(p.getName())) {
                double secondsleft = ((respawnTimer.get(p.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                if (secondsleft > 0) {
                    e.setCancelled(true);
                }
            }
            if (e.getBlock().getType().equals(Material.COAL_ORE) || e.getBlock().getType().equals(Material.IRON_ORE) ||
                    e.getBlock().getType().equals(Material.GOLD_ORE) || e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
                ItemStack coal = new ItemStack(Material.COAL);
                ItemStack iron = new ItemStack(Material.IRON_INGOT);
                ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                ItemStack diamond = new ItemStack(Material.DIAMOND);
                if (e.getBlock().getType().equals(Material.COAL_ORE)) {
                    p.getInventory().addItem(coal);
                }
                if (e.getBlock().getType().equals(Material.IRON_ORE)) {
                    p.getInventory().addItem(iron);
                }
                if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
                    p.getInventory().addItem(diamond);
                }
                if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
                    p.getInventory().addItem(gold);
                }
                blockMined(e.getBlock());
                e.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (e.getItem().getItemMeta() != null) {
            if (e.getItem().getItemMeta().getDisplayName() != null) {
                if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Antidote Potion")) {
                    for (PotionEffect effect : p.getActivePotionEffects()) {

                        if (effect.getType().equals(PotionEffectType.POISON)) {
                            p.removePotionEffect(effect.getType());
                        } else if (effect.getType().equals(PotionEffectType.SLOW)) {
                            p.removePotionEffect(effect.getType());
                        }

                    }
                    e.setCancelled(true);
                    p.setItemInHand(new ItemStack(Material.AIR));
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player attacker = (Player) e.getDamager();
            Player victim = (Player) e.getEntity();
            Team attackerTeam = Team.getTeamFromPlayer(attacker.getName());
            Team victimTeam = Team.getTeamFromPlayer(victim.getName());
            if (attackerTeam != null && victimTeam != null) {
                if (attackerTeam.getTeamName().equalsIgnoreCase(victimTeam.getTeamName())) {
                    attacker.sendMessage(ChatColor.RED + "You cant hurt " + victim.getName() + ".");
                    e.setCancelled(true);
                }
            }
            if (respawnTimer.containsKey(attacker.getName())) {
                double secondsleft = ((respawnTimer.get(attacker.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                if (secondsleft > 0) {
                    e.setCancelled(true);
                }
            }
            if (respawnTimer.containsKey(victim.getName())) {
                double secondsleft = ((respawnTimer.get(victim.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
                if (secondsleft > 0) {
                    e.setCancelled(true);
                }
            }
            if (!isStarted) {
                e.setCancelled(true);
            }

            for (PotionEffect effect : victim.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {
                    victim.removePotionEffect(effect.getType());
                }
            }

            if (homeTimer.containsKey(victim.getName())) {
                homeTimer.remove(victim.getName());
                homeLocation.remove(victim.getName());
            }
            if (homeTimer.containsKey(attacker.getName())) {
                homeTimer.remove(attacker.getName());
                homeLocation.remove(attacker.getName());
            }
        }
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (respawnTimer.containsKey(p.getName())) {
            double secondsleft = ((respawnTimer.get(p.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
            if (secondsleft > 0) {
                e.setCancelled(true);
            }
        }
        if (!isStarted) {
            if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (respawnTimer.containsKey(p.getName())) {
            double secondsleft = ((respawnTimer.get(p.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
            if (secondsleft > 0) {
                e.setCancelled(true);
            }
        }
        if (!isStarted) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (respawnTimer.containsKey(p.getName())) {
            double secondsleft = ((respawnTimer.get(p.getName()) / 1000.0) + 30) - (System.currentTimeMillis() / 1000.0);
            if (secondsleft > 0) {
                e.setCancelled(true);
            }
        }
        if (!isStarted) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (chatMuted) {
            if (!p.isOp()) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Chat is currently muted.");
            }
        }
    }
}
