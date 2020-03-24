package me.soki.bunkers.Spectator;

import me.soki.bunkers.Main.Main;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.soki.bunkers.Game.GameMGR.isStarted;

public class Spectator implements Listener {

    private HashMap<String, String> inspections = new HashMap<>();
    public static List<String> inSpec = new ArrayList<>();

    private static void giveSpecItems(Player p) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassim = compass.getItemMeta();
        compassim.setDisplayName(ChatColor.AQUA + "Compass");
        compass.setItemMeta(compassim);
        p.getInventory().setItem(0, compass);

        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookim = book.getItemMeta();
        bookim.setDisplayName(ChatColor.AQUA + "Inventory Inspector");
        book.setItemMeta(bookim);
        p.getInventory().setItem(1, book);


        ItemStack carpet = new ItemStack(Material.CARPET);
        ItemMeta carpetim = carpet.getItemMeta();
        carpetim.setDisplayName(ChatColor.AQUA + "Better Visibility");
        carpet.setItemMeta(carpetim);
        p.getInventory().setItem(8, carpet);


    }

    public static void enableSpec(Player p) {
        inSpec.add(p.getName());
        giveSpecItems(p);
        p.setGameMode(GameMode.CREATIVE);
        p.sendMessage(ChatColor.YELLOW + "You are now in spectator");
        for (Player eachPlayer : Bukkit.getServer().getOnlinePlayers()){
            eachPlayer.hidePlayer(p);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (isStarted) {
            if (!Main.playersPlaying.contains(p.getName())) {
                enableSpec(p);
            }
        }
        for (String eachSpecStr : inSpec){
            Player spec = Bukkit.getPlayer(eachSpecStr);
            if (eachSpecStr != null){
                p.hidePlayer(spec);
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (inSpec.contains(p.getName())){
            p.getInventory().setContents(null);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (inSpec.contains(((Player) e.getDamager()).getName())) {
                e.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (inSpec.contains(p.getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (inSpec.contains(p.getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (inSpec.contains(p.getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventInteract(InventoryInteractEvent e){
        Player p = (Player) e.getWhoClicked();
        if (inSpec.contains(p.getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        if (inSpec.contains(p.getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if (inSpec.contains(p.getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        if (inSpec.contains(e.getPlayer().getName())){
            e.setCancelled(true);
        }
        if (e.getRightClicked() instanceof Player) {
            Player p = e.getPlayer();
            if (inSpec.contains(p.getName())) {
                if (p.getItemInHand() != null) {
                    if (p.getItemInHand().getItemMeta() != null) {
                        if (p.getItemInHand().getType().equals(Material.BOOK) && p.getItemInHand().getItemMeta().getDisplayName()
                                .equalsIgnoreCase(ChatColor.AQUA + "Inventory Inspector")) {
                            openInspectionMenu(p, ((Player) e.getRightClicked()));
                        }
                    }
                }
            }
        }
    }

    private void openInspectionMenu(final Player player, final Player target) {
        final Inventory inventory = Bukkit.getServer().createInventory((InventoryHolder) null, 45, "Inventory of " + target.getName());
        new BukkitRunnable() {
            public void run() {
                inspections.put(player.getName(), target.getName());
                PlayerInventory playerInventory = target.getInventory();
                Damageable dtarget = (Damageable) target;
                org.bukkit.inventory.ItemStack speckledMelon = new ItemStack(351, (int) dtarget.getHealth(), (short) 1);
                ItemMeta speckledMelonMeta = speckledMelon.getItemMeta();
                speckledMelonMeta.setDisplayName(ChatColor.AQUA + "&bPlayer Health");
                List<String> tempLore = new ArrayList<String>();
                tempLore.add("&fThis is the health of your user you are");
                tempLore.add("&fcurrently inspecting.");
                List<String> tempLore2 = new ArrayList<String>();
                for (String eachLore : tempLore) {
                    tempLore2.add(eachLore);
                }
                speckledMelonMeta.setLore(tempLore2);
                speckledMelon.setItemMeta(speckledMelonMeta);
                ItemStack brewingStand = new ItemStack(Material.BLAZE_POWDER, target.getPlayer().getActivePotionEffects().size());
                ItemMeta brewingStandMeta = brewingStand.getItemMeta();
                brewingStandMeta.setDisplayName(ChatColor.AQUA + "&bEffects");
                String brewingStandLoreString = "";
                ArrayList<String> brewingStandLore = new ArrayList<String>();
                for (PotionEffect potionEffect : target.getPlayer().getActivePotionEffects()) {
                    String effectName = potionEffect.getType().getName();
                    int effectLevel = potionEffect.getAmplifier();
                    ++effectLevel;
                    brewingStandLore.add(
                            ("&dPotion: &f" + WordUtils.capitalizeFully(effectName).replace("_", " ")
                                    + "&d, Level: &f" + effectLevel + "&d, Time: &f" + (potionEffect.getDuration() / 20))
                                    .replace("&", "§"));
                }
                brewingStandMeta.setLore((List) brewingStandLore);
                brewingStand.setItemMeta(brewingStandMeta);
                ItemStack empty = new ItemStack(Material.IRON_FENCE, 1);
                ItemMeta emptyMeta = empty.getItemMeta();
                emptyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cEmpty Slot"));
                empty.setItemMeta(emptyMeta);
                ItemStack clearInv = new ItemStack(Material.BOOK_AND_QUILL, 1);
                ItemMeta clearInvMeta = clearInv.getItemMeta();
                clearInvMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bPlayer Info"));

                clearInv.setItemMeta(clearInvMeta);
                ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
                inventory.setContents(playerInventory.getContents());
                if (playerInventory.getHelmet() == null) {
                    inventory.setItem(36, empty);
                } else {
                    inventory.setItem(36, playerInventory.getHelmet());
                }
                if (playerInventory.getChestplate() == null) {
                    inventory.setItem(37, empty);
                } else {
                    inventory.setItem(37, playerInventory.getChestplate());
                }
                if (playerInventory.getLeggings() == null) {
                    inventory.setItem(38, empty);
                } else {
                    inventory.setItem(38, playerInventory.getLeggings());
                }
                if (playerInventory.getBoots() == null) {
                    inventory.setItem(39, empty);
                } else {
                    inventory.setItem(39, playerInventory.getBoots());
                }
                inventory.setItem(42, speckledMelon);
                inventory.setItem(43, brewingStand);
                inventory.setItem(44, clearInv);
                if (!player.getOpenInventory().getTitle().equals("Inventory of " + target.getName())) {
                    this.cancel();
                    inspections.remove(player.getName());
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 5L);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (inspections.containsKey(p.getName())) {
            inspections.remove(p.getName());
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (inspections.containsKey(p.getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (inspections.containsKey(p.getName())) {
            e.setCancelled(true);
        }
    }
}
