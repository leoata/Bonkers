package me.soki.bunkers.Villagers;

import me.soki.bunkers.Claim.ClaimMGR;
import me.soki.bunkers.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.List;

import static me.soki.bunkers.Economy.EconomyMGR.*;
import static me.soki.bunkers.Main.Main.getPlugin;
import static me.soki.bunkers.Villagers.VillagerMGR.*;

public class VillagerListener implements Listener {
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Villager) {
            Player p = e.getPlayer();
            Villager villager = (Villager) e.getRightClicked();
            if (settingVillagers.containsKey(p.getName())) {
                e.setCancelled(true);
                //setting villagers already
                switch (settingVillagers.get(p.getName())) {
                    case "combat":
                        villager.setCustomName(ChatColor.YELLOW + "Combat Shop");
                        break;
                    case "sell":
                        villager.setCustomName(ChatColor.YELLOW + "Sell Items");
                        break;
                    case "build":
                        villager.setCustomName(ChatColor.YELLOW + "Build Shop");
                        break;
                    case "enchanter":
                        villager.setCustomName(ChatColor.YELLOW + "Tim The Enchanter");
                        break;

                }
                p.sendMessage(ChatColor.YELLOW + "Set the villager to the appropriate type.");
                villager.setCustomNameVisible(true);
                villager.setRemoveWhenFarAway(false);
                settingVillagers.remove(p.getName());
            } else {
                if (villager.getCustomName() != null) {
                    if (villager.getCustomName().equalsIgnoreCase(ChatColor.YELLOW.toString() + "Combat Shop")) {
                        e.setCancelled(true);
                        openCombatShop(p);
                        inShop.add(p.getName());
                    } else if (villager.getCustomName().equalsIgnoreCase(ChatColor.YELLOW.toString() + "Sell Items")) {
                        e.setCancelled(true);
                        openSellItems(p);
                        inShop.add(p.getName());
                    } else if (villager.getCustomName().equalsIgnoreCase(ChatColor.YELLOW.toString() + "Build Shop")) {
                        e.setCancelled(true);
                        openBuildShop(p);
                        inShop.add(p.getName());
                    } else if (villager.getCustomName().equalsIgnoreCase(ChatColor.YELLOW.toString() + "Tim The Enchanter")) {
                        e.setCancelled(true);
                        openEnchanter(p);
                        inShop.add(p.getName());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Villager && e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            Team playersTeam = Team.getTeamFromPlayer(p.getName());
            if (playersTeam != null) {
                if (ClaimMGR.whichClaimIsThisIn(e.getEntity().getLocation()) != null) {
                    if (ClaimMGR.whichClaimIsThisIn(e.getEntity().getLocation()).getTeamName().equalsIgnoreCase(playersTeam.getTeamName()))
                        e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        inShop.remove(p.getName());
        inRepair.remove(p.getName());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        inShop.remove(p.getName());
        inRepair.remove(p.getName());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        try {
            Player p = (Player) e.getWhoClicked();
            if (inShop.contains(p.getName())) {
                e.setCancelled(true);
                Inventory inventory = e.getClickedInventory();
                ItemStack clickedItem = e.getCurrentItem();
                if (clickedItem.getItemMeta().getLore() != null) {
                    List<String> lore = clickedItem.getItemMeta().getLore();
                    if (p.getInventory().firstEmpty() != -1) {
                        if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
                            //right click
                            if (clickedItem.getType().equals(Material.POTION)) {
                                if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Health Potion")) {
                                    for (ItemStack eachSlot : p.getInventory().getContents()) {
                                        if (playerBalances.get(p.getName()) >= 5 && p.getInventory().firstEmpty() != -1) {
                                            ItemStack healthPot = new ItemStack(Material.POTION);
                                            Potion pot = new Potion(1);
                                            pot.setType(PotionType.INSTANT_HEAL);
                                            pot.setSplash(true);
                                            pot.setLevel(2);
                                            pot.apply(healthPot);
                                            p.getInventory().addItem(healthPot);
                                            takeMoney(p, 5);
                                        } else if (playerBalances.get(p.getName()) < 5) {
                                            p.sendMessage(ChatColor.RED + "Insufficient funds");
                                            break;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            } else if (clickedItem.getType().equals(Material.COOKED_BEEF)) {
                                if (playerBalances.get(p.getName()) >= 200) {
                                    takeMoney(p, 200);
                                    p.sendMessage(ChatColor.GREEN + "You bought 64 " + ChatColor.YELLOW + "Steak" + ChatColor.GREEN + " for " + ChatColor.GREEN + "$200");
                                    p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
                                } else {
                                    p.sendMessage(ChatColor.RED + "Insufficient funds");
                                }
                            } else if (clickedItem.getType().equals(Material.ENDER_PEARL)) {
                                if (playerBalances.get(p.getName()) >= 400) {
                                    takeMoney(p, 400);
                                    p.sendMessage(ChatColor.GREEN + "You bought 16 " + ChatColor.YELLOW + "ender pearls" + ChatColor.GREEN + " for " + ChatColor.GREEN + "$400");
                                    p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
                                } else {
                                    p.sendMessage(ChatColor.RED + "Insufficient funds");
                                }
                            } else if (clickedItem.getType().equals(Material.DIAMOND)) {
                                int count = 0;
                                for (ItemStack eachItem : p.getInventory().getContents()) {
                                    if (eachItem != null) {
                                        if (eachItem.getType().equals(Material.DIAMOND)) {
                                            count += eachItem.getAmount();
                                        }
                                    }
                                }
                                giveMoney(p, count * 35);
                                p.getInventory().remove(Material.DIAMOND);
                                p.sendMessage(ChatColor.GREEN + "You sold " + count + ChatColor.AQUA + " diamonds " + ChatColor.GREEN + "for " + ChatColor.GREEN + "$" + count * 35);
                            } else if (clickedItem.getType().equals(Material.GOLD_INGOT)) {
                                int count = 0;
                                for (ItemStack eachItem : p.getInventory().getContents()) {
                                    if (eachItem != null) {
                                        if (eachItem.getType().equals(Material.GOLD_INGOT)) {
                                            count += eachItem.getAmount();
                                        }
                                    }
                                }
                                giveMoney(p, count * 25);
                                p.getInventory().remove(Material.GOLD_INGOT);
                                p.sendMessage(ChatColor.GREEN + "You sold " + count + ChatColor.GOLD + " gold " + ChatColor.GREEN + "for " + ChatColor.GREEN + "$" + count * 25);
                            } else if (clickedItem.getType().equals(Material.IRON_INGOT)) {
                                int count = 0;
                                for (ItemStack eachItem : p.getInventory().getContents()) {
                                    if (eachItem != null) {
                                        if (eachItem.getType().equals(Material.IRON_INGOT)) {
                                            count += eachItem.getAmount();
                                        }
                                    }
                                }
                                giveMoney(p, count * 15);
                                p.getInventory().remove(Material.IRON_INGOT);
                                p.sendMessage(ChatColor.GREEN + "You sold " + count + ChatColor.GRAY + " iron " + ChatColor.GREEN + "for " + ChatColor.GREEN + "$" + count * 15);
                            } else if (clickedItem.getType().equals(Material.COAL)) {
                                int count = 0;
                                for (ItemStack eachItem : p.getInventory().getContents()) {
                                    if (eachItem != null) {
                                        if (eachItem.getType().equals(Material.COAL)) {
                                            count += eachItem.getAmount();
                                        }
                                    }
                                }

                                giveMoney(p, count * 15);
                                p.getInventory().remove(Material.COAL);
                                p.sendMessage(ChatColor.GREEN + "You sold " + count + ChatColor.DARK_GRAY + " coal " + ChatColor.GREEN + "for " + ChatColor.GREEN + "$" + count * 15);
                            }
                        } else {
                            if (lore == null) {
                                e.setCancelled(false);
                                return;
                            }
                            String priceStr = "null";
                            for (String eachLine : lore) {
                                if (eachLine.contains("Price: ")) {
                                    priceStr = eachLine.substring(eachLine.indexOf("$") + 1);
                                }
                            }
                            int priceInt = Integer.valueOf(priceStr);
                            int playersBal = playerBalances.get(p.getName());
                            if (playersBal >= priceInt) {

                                p.sendMessage(ChatColor.YELLOW + "You bought a " + clickedItem.getItemMeta().getDisplayName() + ChatColor.YELLOW + " for $" + priceStr);
                                if (clickedItem.getType().equals(Material.POTION)) {
                                    if (clickedItem.getItemMeta() != null) {
                                        if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Health Potion")) {
                                            ItemStack healthPot = new ItemStack(Material.POTION);
                                            Potion pot = new Potion(1);
                                            pot.setType(PotionType.INSTANT_HEAL);
                                            pot.setSplash(true);
                                            pot.setLevel(2);
                                            pot.apply(healthPot);
                                            p.getInventory().addItem(healthPot);
                                            p.updateInventory();
                                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Speed Potion")) {
                                            ItemStack healthPot = new ItemStack(Material.POTION);
                                            Potion pot = new Potion(1);
                                            pot.setType(PotionType.SPEED);
                                            pot.setLevel(2);
                                            pot.apply(healthPot);
                                            p.getInventory().addItem(healthPot);
                                            p.updateInventory();
                                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Antidote Potion")) {
                                            ItemStack antidote = new ItemStack(Material.POTION);
                                            Potion antidotePot = new Potion(1);
                                            antidotePot.setType(PotionType.POISON);
                                            antidotePot.apply(antidote);
                                            ItemMeta antidoteIM = antidote.getItemMeta();
                                            antidoteIM.setDisplayName(ChatColor.GREEN.toString() + "Antidote Potion");
                                            antidote.setItemMeta(antidoteIM);
                                            p.getInventory().addItem(antidote);
                                            p.updateInventory();
                                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Fire Resistance Potion")) {
                                            ItemStack pot = new ItemStack(Material.POTION);
                                            Potion potPot = new Potion(1);
                                            potPot.setType(PotionType.FIRE_RESISTANCE);
                                            potPot.setHasExtendedDuration(true);
                                            potPot.apply(pot);
                                            p.getInventory().addItem(pot);
                                            p.updateInventory();
                                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Lesser Invisibility Potion (Drinkable)")) {
                                            ItemStack pot = new ItemStack(Material.POTION);
                                            Potion potPot = new Potion(1);
                                            potPot.setType(PotionType.INVISIBILITY);
                                            potPot.setHasExtendedDuration(true);
                                            potPot.apply(pot);
                                            ItemMeta potIM = pot.getItemMeta();
                                            potIM.setDisplayName(ChatColor.GREEN.toString() + "Lesser Invisibility Potion (Drinkable)");
                                            pot.setItemMeta(potIM);
                                            p.getInventory().addItem(pot);
                                            p.updateInventory();
                                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Splash Slowness Potion")) {
                                            ItemStack pot = new ItemStack(Material.POTION);
                                            Potion potPot = new Potion(1);
                                            potPot.setType(PotionType.SLOWNESS);
                                            potPot.setSplash(true);
                                            potPot.apply(pot);
                                            p.getInventory().addItem(pot);
                                            p.updateInventory();
                                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Poison Potion")) {
                                            ItemStack pot = new ItemStack(Material.POTION);
                                            Potion potPot = new Potion(1);
                                            potPot.setType(PotionType.POISON);
                                            potPot.setSplash(true);
                                            potPot.apply(pot);
                                            p.getInventory().addItem(pot);
                                            p.updateInventory();
                                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Lesser Invisibility Potion")) {
                                            ItemStack pot = new ItemStack(Material.POTION);
                                            Potion potPot = new Potion(1);
                                            potPot.setType(PotionType.INVISIBILITY);
                                            potPot.setSplash(true);
                                            potPot.apply(pot);
                                            ItemMeta potIM = pot.getItemMeta();
                                            potIM.setDisplayName(ChatColor.GREEN.toString() + "Lesser Invisibility Potion");
                                            pot.setItemMeta(potIM);
                                            p.getInventory().addItem(pot);
                                            p.updateInventory();
                                        }
                                    } else {
                                        p.getInventory().addItem(new ItemStack(clickedItem.getType(), clickedItem.getAmount()));
                                    }
                                    takeMoney(p, priceInt);
                                } else {
                                    if (clickedItem.getType().equals(Material.DIAMOND_HELMET)) {
                                        if (p.getInventory().getHelmet() == null) {
                                            p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_HELMET));
                                        }
                                    } else if (clickedItem.getType().equals(Material.DIAMOND_CHESTPLATE)) {
                                        if (p.getInventory().getChestplate() == null) {
                                            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                        }
                                    } else if (clickedItem.getType().equals(Material.DIAMOND_LEGGINGS)) {
                                        if (p.getInventory().getLeggings() == null) {
                                            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        }
                                    } else if (clickedItem.getType().equals(Material.DIAMOND_BOOTS)) {
                                        if (p.getInventory().getBoots() == null) {
                                            p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_BOOTS));
                                        }
                                    } else if (clickedItem.getType().equals(Material.DIAMOND)) {
                                        if (p.getInventory().getHelmet() == null) {
                                            p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_HELMET));
                                        }
                                        if (p.getInventory().getChestplate() == null) {
                                            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
                                        }
                                        if (p.getInventory().getLeggings() == null) {
                                            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        }
                                        if (p.getInventory().getBoots() == null) {
                                            p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                        } else {
                                            p.getInventory().addItem(new ItemStack(Material.DIAMOND_BOOTS));
                                        }
                                        p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
                                    } else if (!clickedItem.getType().equals(Material.ENCHANTED_BOOK)) {
                                        p.getInventory().addItem(new ItemStack(clickedItem.getType(), clickedItem.getAmount()));
                                        takeMoney(p, priceInt);
                                    }
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Insufficient funds.");
                            }
                        }
                    }

                    if (lore == null) {
                        e.setCancelled(false);
                        return;
                    }
                    String priceStr = "null";
                    for (String eachLine : lore) {
                        if (eachLine.contains("Price: ")) {
                            priceStr = eachLine.substring(eachLine.indexOf("$") + 1);
                        }
                    }
                    int priceInt = Integer.valueOf(priceStr);
                    int playersBal = playerBalances.get(p.getName());
                    if (playersBal >= priceInt) {
                        if (!clickedItem.getType().equals(Material.ENCHANTED_BOOK)) {
                            p.sendMessage(ChatColor.RED + "You already have a full inventory.");
                            return;
                        } else if (clickedItem.getType().equals(Material.ENCHANTED_BOOK)) {
                            if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Full Protection I")) {
                                p.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                p.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                p.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                p.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                            } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Sharpness I")) {
                                for (ItemStack eachItem : p.getInventory().getContents()) {
                                    if (eachItem != null) {
                                        if (eachItem.getType().equals(Material.DIAMOND_SWORD)) {
                                            eachItem.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                                            break;
                                        }
                                    }
                                }
                            } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Feather Falling IV")) {
                                p.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_FALL, 4);
                            }
                            takeMoney(p, priceInt);
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Insufficient funds.");
                    }
                }
            }
            if (inRepair.contains(p.getName())) {
                e.setCancelled(true);
                ItemStack clickedItem = e.getCurrentItem();
                List<String> lore = clickedItem.getItemMeta().getLore();
                if (lore == null) {
                    if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED.toString() + "No Items Need Repairing")) {
                        p.closeInventory();
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(false);
                    }
                    return;
                }
                String priceStr = "null";
                for (String eachLine : lore) {
                    if (eachLine.contains("Price: ")) {
                        priceStr = eachLine.substring(eachLine.indexOf("$") + 1);
                    }
                }
                int priceInt = Integer.valueOf(priceStr.substring(0, priceStr.indexOf('.')));
                int playersBal = playerBalances.get(p.getName());
                if (playersBal >= priceInt) {
                    if (clickedItem.getItemMeta() != null) {
                        if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Diamond Helmet")) {
                            if (p.getInventory().getHelmet() != null) {
                                takeMoney(p, priceInt);
                                p.sendMessage(ChatColor.YELLOW + "You fixed your helmet for " + ChatColor.GREEN + "$" + priceStr);
                                p.getInventory().getHelmet().setDurability((short) 0);
                                p.closeInventory();
                                openRepair(p);
                                inRepair.add(p.getName());
                            } else {
                                p.sendMessage(ChatColor.RED + "You don't have a helmet");
                            }
                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Diamond Chestplate")) {
                            if (p.getInventory().getChestplate() != null) {
                                takeMoney(p, priceInt);
                                p.sendMessage(ChatColor.YELLOW + "You fixed your chestplate for " + ChatColor.GREEN + "$" + priceStr);
                                p.getInventory().getChestplate().setDurability((short) 0);
                                p.closeInventory();
                                openRepair(p);
                                inRepair.add(p.getName());
                            } else {
                                p.sendMessage(ChatColor.RED + "You don't have a chestplate");
                            }
                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Diamond Leggings")) {
                            if (p.getInventory().getLeggings() != null) {
                                takeMoney(p, priceInt);
                                p.sendMessage(ChatColor.YELLOW + "You fixed your leggings for " + ChatColor.GREEN + "$" + priceStr);
                                p.getInventory().getLeggings().setDurability((short) 0);
                                p.closeInventory();
                                openRepair(p);
                                inRepair.add(p.getName());
                            } else {
                                p.sendMessage(ChatColor.RED + "You don't have a leggings");
                            }
                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Diamond Boots")) {
                            if (p.getInventory().getBoots() != null) {
                                takeMoney(p, priceInt);
                                p.sendMessage(ChatColor.YELLOW + "You fixed your boots for " + ChatColor.GREEN + "$" + priceStr);
                                ItemStack boots = p.getInventory().getBoots();
                                boots.setDurability((short) 0);
                                p.getInventory().setBoots(boots);
                                p.closeInventory();
                                openRepair(p);
                                inRepair.add(p.getName());
                            } else {
                                p.sendMessage(ChatColor.RED + "You don't have boots");
                            }
                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + "Diamond Set")) {
                            takeMoney(p, priceInt);
                            p.sendMessage(ChatColor.YELLOW + "You fixed your set for " + ChatColor.GREEN + "$" + priceStr);
                            p.getInventory().getBoots().setDurability((short) 0);
                            p.getInventory().getChestplate().setDurability((short) 0);
                            p.getInventory().getLeggings().setDurability((short) 0);
                            p.getInventory().getHelmet().setDurability((short) 0);
                            p.closeInventory();
                            openRepair(p);
                            inRepair.add(p.getName());
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Insufficient funds.");
                }
            }
        } catch (NullPointerException nullPointer) {

        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (inShop.contains(p.getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.ANVIL)) {
                e.setCancelled(true);
                openRepair(p);
                inRepair.add(p.getName());
            }
        }
    }

    @EventHandler
    public void onVillagerDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (e.getEntityType().equals(EntityType.VILLAGER)) {
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                String villagerType = creature.getCustomName();
                Team villagerClaim = ClaimMGR.whichClaimIsThisIn(entity.getLocation());
                if (villagerClaim != null) {
                    for (String eachPlayerStr : villagerClaim.getPlayers()) {
                        Player eachPlayer = Bukkit.getPlayer(eachPlayerStr);
                        eachPlayer.sendMessage(ChatColor.YELLOW + "Your " + ChatColor.RED + villagerType + " has died! They will respawn in 3 minutes!");
                    }
                }
                Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                Entity newEntity = e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.VILLAGER);
                                Villager newVillager = (Villager) newEntity;
                                newVillager.setCustomName(villagerType);
                                newVillager.setCustomNameVisible(true);
                                if (villagerClaim != null) {
                                    for (String eachPlayerStr : villagerClaim.getPlayers()) {
                                        Player eachPlayer = Bukkit.getPlayer(eachPlayerStr);
                                        eachPlayer.sendMessage(ChatColor.YELLOW + "Your " + villagerType + " has respawned!");
                                    }
                                }
                            }
                        },

                        180 * 20
                );
            }
        }
    }

}
