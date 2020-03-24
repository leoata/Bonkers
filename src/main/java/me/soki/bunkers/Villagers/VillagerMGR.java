package me.soki.bunkers.Villagers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VillagerMGR {

    public static HashMap<String, String> settingVillagers = new HashMap<>();
    //name, what type of villager (combat, sell, build)
    public static List<String> inShop = new ArrayList<>();
    //name
    public static List<String> inRepair = new ArrayList<>();

    public static void openCombatShop(Player p) {

        Inventory gui = Bukkit.getServer().createInventory(p, 54, "§c§lCombat Shop");

        List<String> diaHelmLore = new ArrayList<>();
        diaHelmLore.add("§7§m--------------");
        diaHelmLore.add("§71x Diamond Helmet");
        diaHelmLore.add("§7§m--------------");
        diaHelmLore.add("§ePrice: §a$75");
        ItemStack diaHelm = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta diaHelmIM = diaHelm.getItemMeta();
        diaHelmIM.setDisplayName(ChatColor.GREEN + "Diamond Helmet");
        diaHelmIM.setLore(diaHelmLore);
        diaHelm.setItemMeta(diaHelmIM);
        gui.setItem(10, diaHelm);

        List<String> diaChestLore = new ArrayList<>();
        diaChestLore.add("§7§m------------------");
        diaChestLore.add("§71x Diamond Chestplate");
        diaChestLore.add("§7§m------------------");
        diaChestLore.add("§ePrice: §a$200");
        ItemStack diaChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta diaChestIM = diaChest.getItemMeta();
        diaChestIM.setDisplayName(ChatColor.GREEN + "Diamond Chestplate");
        diaChestIM.setLore(diaChestLore);
        diaChest.setItemMeta(diaChestIM);
        gui.setItem(19, diaChest);

        List<String> diaLegsLore = new ArrayList<>();
        diaLegsLore.add("§7§m-----------------");
        diaLegsLore.add("§71x Diamond Leggings");
        diaLegsLore.add("§7§m-----------------");
        diaLegsLore.add("§ePrice: §a$150");
        ItemStack diaLegs = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta diaLegsIM = diaLegs.getItemMeta();
        diaLegsIM.setDisplayName(ChatColor.GREEN + "Diamond Leggings");
        diaLegsIM.setLore(diaLegsLore);
        diaLegs.setItemMeta(diaLegsIM);
        gui.setItem(28, diaLegs);

        List<String> diaBootsLore = new ArrayList<>();
        diaBootsLore.add("§7§m--------------");
        diaBootsLore.add("§71x Diamond Boots");
        diaBootsLore.add("§7§m--------------");
        diaBootsLore.add("§ePrice: §a$75");
        ItemStack diaBoots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta diaBootsIM = diaBoots.getItemMeta();
        diaBootsIM.setDisplayName(ChatColor.GREEN + "Diamond Boots");
        diaBootsIM.setLore(diaBootsLore);
        diaBoots.setItemMeta(diaBootsIM);
        gui.setItem(37, diaBoots);

        List<String> diaLoadoutLore = new ArrayList<>();
        diaLoadoutLore.add("§7§m-------------------");
        diaLoadoutLore.add("§71x Diamond Sword");
        diaLoadoutLore.add("§71x Diamond Helmet");
        diaLoadoutLore.add("§71x Diamond Chestplate");
        diaLoadoutLore.add("§71x Diamond Leggings");
        diaLoadoutLore.add("§71x Diamond Boots");
        diaLoadoutLore.add("§7§m-------------------");
        diaLoadoutLore.add("§ePrice: §a$600");
        ItemStack diaLoadout = new ItemStack(Material.DIAMOND);
        ItemMeta diaLoadoutIM = diaLoadout.getItemMeta();
        diaLoadoutIM.setDisplayName(ChatColor.GREEN + "Diamond Set");
        diaLoadoutIM.setLore(diaLoadoutLore);
        diaLoadout.setItemMeta(diaLoadoutIM);
        gui.setItem(20, diaLoadout);

        List<String> diaSwordLore = new ArrayList<>();
        diaSwordLore.add("§7§m---------------");
        diaSwordLore.add("§71x Diamond Sword");
        diaSwordLore.add("§7§m---------------");
        diaSwordLore.add("§ePrice: §a$100");
        ItemStack diaSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diaSwordIM = diaSword.getItemMeta();
        diaSwordIM.setDisplayName(ChatColor.GREEN + "Diamond Sword");
        diaSwordIM.setLore(diaSwordLore);
        diaSword.setItemMeta(diaSwordIM);
        gui.setItem(18, diaSword);

        List<String> pearlsLore = new ArrayList<>();
        pearlsLore.add("§7§m-----------------------------");
        pearlsLore.add("§71x Enderpearl");
        pearlsLore.add("§aRight click to purchase 16 for $400");
        pearlsLore.add("§7§m-----------------------------");
        pearlsLore.add("§ePrice: §a$25");
        ItemStack pearls = new ItemStack(Material.ENDER_PEARL);
        ItemMeta pearlsIM = pearls.getItemMeta();
        pearlsIM.setDisplayName(ChatColor.GREEN + "Ender Pearl");
        pearlsIM.setLore(pearlsLore);
        pearls.setItemMeta(pearlsIM);
        gui.setItem(21, pearls);

        List<String> steakLore = new ArrayList<>();
        steakLore.add("§7§m-----------------------------");
        steakLore.add("§716x Steak");
        steakLore.add("§aRight click to purchase 64 for $200");
        steakLore.add("§7§m-----------------------------");
        steakLore.add("§ePrice: §a$50");
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 16);
        ItemMeta steakIM = steak.getItemMeta();
        steakIM.setDisplayName(ChatColor.GREEN + "Steak");
        steakIM.setLore(steakLore);
        steak.setItemMeta(steakIM);
        gui.setItem(41, steak);


        List<String> healthPotLore = new ArrayList<>();
        healthPotLore.add("§7§m-----------------------------");
        healthPotLore.add("§71x Splash Health II Potion");
        healthPotLore.add("§aRight click to fill inventory");
        healthPotLore.add("§7§m-----------------------------");
        healthPotLore.add("§ePrice: §a$5");

        ItemStack healthPot = new ItemStack(Material.POTION);
        Potion pot = new Potion(1);
        pot.setType(PotionType.INSTANT_HEAL);
        pot.setSplash(true);
        pot.setLevel(2);
        pot.apply(healthPot);
        ItemMeta healthPotIM = healthPot.getItemMeta();
        healthPotIM.setDisplayName(ChatColor.GREEN + "Health Potion");
        healthPotIM.setLore(healthPotLore);
        healthPot.setItemMeta(healthPotIM);
        gui.setItem(24, healthPot);

        List<String> speedPotLore = new ArrayList<>();
        speedPotLore.add("§7§m-----------------------------");
        speedPotLore.add("§71x Speed Potion (1:30m)");
        speedPotLore.add("§7§m-----------------------------");
        speedPotLore.add("§ePrice: §a$15");
        ItemStack speedPot = new ItemStack(Material.POTION);
        Potion speedPotion = new Potion(1);
        speedPotion.setType(PotionType.SPEED);
        speedPotion.setLevel(2);
        speedPotion.apply(speedPot);
        ItemMeta speedPotIM = speedPot.getItemMeta();
        speedPotIM.setDisplayName(ChatColor.GREEN + "Speed Potion");
        speedPotIM.setLore(speedPotLore);
        speedPot.setItemMeta(speedPotIM);
        gui.setItem(14, speedPot);

        List<String> antidoteLore = new ArrayList<>();
        antidoteLore.add("§7§m-------------------");
        antidoteLore.add("§71x Antidote Potion");
        antidoteLore.add("§7§m-------------------");
        antidoteLore.add("§ePrice: §a$200");
        ItemStack antidote = new ItemStack(Material.POTION);
        Potion antidotePot = new Potion(1);
        antidotePot.setType(PotionType.POISON);
        antidotePot.apply(antidote);
        ItemMeta antidoteIM = antidote.getItemMeta();
        antidoteIM.setDisplayName(ChatColor.GREEN + "Antidote Potion");
        antidoteIM.setLore(antidoteLore);
        antidote.setItemMeta(antidoteIM);
        gui.setItem(39, antidote);

        List<String> posionLore = new ArrayList<>();
        posionLore.add("§7§m-----------------------------");
        posionLore.add("§71x Poison Potion (0:33)");
        posionLore.add("§7§m-----------------------------");
        posionLore.add("§ePrice: §a$100");
        ItemStack poison = new ItemStack(Material.POTION);
        Potion poisonPot = new Potion(1);
        poisonPot.setType(PotionType.POISON);
        poisonPot.setSplash(true);
        poisonPot.apply(poison);
        ItemMeta poisonIM = poison.getItemMeta();
        poisonIM.setDisplayName(ChatColor.GREEN + "Poison Potion");
        poisonIM.setLore(posionLore);
        poison.setItemMeta(poisonIM);
        gui.setItem(25, poison);

        List<String> fireResLore = new ArrayList<>();
        fireResLore.add("§7§m-----------------------------");
        fireResLore.add("§71x Fire Resistance Potion (8:00)");
        fireResLore.add("§7§m-----------------------------");
        fireResLore.add("§ePrice: §a$25");
        ItemStack fireRes = new ItemStack(Material.POTION);
        Potion fireResPot = new Potion(1);
        fireResPot.setType(PotionType.FIRE_RESISTANCE);
        fireResPot.setHasExtendedDuration(true);
        fireResPot.apply(fireRes);
        ItemMeta fireResIM = fireRes.getItemMeta();
        fireResIM.setDisplayName(ChatColor.GREEN + "Fire Resistance Potion");
        fireResIM.setLore(fireResLore);
        fireRes.setItemMeta(fireResIM);
        gui.setItem(15, fireRes);

        List<String> slownessLore = new ArrayList<>();
        slownessLore.add("§7§m-----------------------------");
        slownessLore.add("§71x Splash Slowness Potion (1:07)");
        slownessLore.add("§7§m-----------------------------");
        slownessLore.add("§ePrice: §a$100");
        ItemStack slowness = new ItemStack(Material.POTION);
        Potion slownessPot = new Potion(1);
        slownessPot.setType(PotionType.SLOWNESS);
        slownessPot.setSplash(true);
        slownessPot.apply(slowness);
        ItemMeta slownessIM = slowness.getItemMeta();
        slownessIM.setDisplayName(ChatColor.GREEN + "Splash Slowness Potion");
        slownessIM.setLore(slownessLore);
        slowness.setItemMeta(slownessIM);
        gui.setItem(16, slowness);

        List<String> invisLore = new ArrayList<>();
        invisLore.add("§7§m---------------------------------");
        invisLore.add("§71x Splash Lesser Invisibility Potion (2:15)");
        invisLore.add("§aPotion will be removed once someone hits you");
        invisLore.add("§7§m---------------------------------");
        invisLore.add("§ePrice: §a$400");
        ItemStack invis = new ItemStack(Material.POTION);
        Potion invisPot = new Potion(1);
        invisPot.setType(PotionType.INVISIBILITY);
        invisPot.setSplash(true);
        invisPot.apply(invis);
        ItemMeta invisIM = invis.getItemMeta();
        invisIM.setDisplayName(ChatColor.GREEN + "Lesser Invisibility Potion");
        invisIM.setLore(invisLore);
        invis.setItemMeta(invisIM);
        gui.setItem(34, invis);

        List<String> invisDrinkLore = new ArrayList<>();
        invisDrinkLore.add("§7§m---------------------------------");
        invisDrinkLore.add("§71x Lesser Invisibility Potion (8:00)");
        invisDrinkLore.add("§aPotion will be removed once someone hits you");
        invisDrinkLore.add("§7§m---------------------------------");
        invisDrinkLore.add("§ePrice: §a$800");
        ItemStack invisDrink = new ItemStack(Material.POTION);
        Potion invisDrinkPot = new Potion(1);
        invisDrinkPot.setType(PotionType.INVISIBILITY);
        invisDrinkPot.setHasExtendedDuration(true);
        invisDrinkPot.apply(invisDrink);
        ItemMeta invisDrinkIM = invisDrink.getItemMeta();
        invisDrinkIM.setDisplayName(ChatColor.GREEN + "Lesser Invisibility Potion (Drinkable)");
        invisDrinkIM.setLore(invisDrinkLore);
        invisDrink.setItemMeta(invisDrinkIM);
        gui.setItem(23, invisDrink);


        p.openInventory(gui);
        p.sendMessage(ChatColor.GREEN + "Opening combat shop");


    }

    public static void openBuildShop(Player p) {
        Inventory gui = Bukkit.getServer().createInventory(p, 54, "§c§lBuild Shop");

        List<String> cobbleLore = new ArrayList<>();
        cobbleLore.add("§7§m--------------");
        cobbleLore.add("§716x Cobblestone");
        cobbleLore.add("§7§m--------------");
        cobbleLore.add("§ePrice: §a$50");
        ItemStack cobble = new ItemStack(Material.COBBLESTONE, 16);
        ItemMeta cobbleIM = cobble.getItemMeta();
        cobbleIM.setDisplayName(ChatColor.GREEN + "Cobblestone");
        cobbleIM.setLore(cobbleLore);
        cobble.setItemMeta(cobbleIM);
        gui.setItem(0, cobble);

        List<String> chestLore = new ArrayList<>();
        chestLore.add("§7§m--------------");
        chestLore.add("§716x Chests");
        chestLore.add("§7§m--------------");
        chestLore.add("§ePrice: §a$50");
        ItemStack chest = new ItemStack(Material.CHEST, 16);
        ItemMeta chestIM = chest.getItemMeta();
        chestIM.setDisplayName(ChatColor.GREEN + "Chest");
        chestIM.setLore(chestLore);
        chest.setItemMeta(chestIM);
        gui.setItem(1, chest);

        List<String> fencegateLore = new ArrayList<>();
        fencegateLore.add("§7§m--------------");
        fencegateLore.add("§716x Fence Gates");
        fencegateLore.add("§7§m--------------");
        fencegateLore.add("§ePrice: §a$50");
        ItemStack fencegate = new ItemStack(Material.FENCE_GATE, 16);
        ItemMeta fencegateIM = fencegate.getItemMeta();
        fencegateIM.setDisplayName(ChatColor.GREEN + "Cobblestone");
        fencegateIM.setLore(fencegateLore);
        fencegate.setItemMeta(fencegateIM);
        gui.setItem(2, fencegate);

        List<String> pickaxeLore = new ArrayList<>();
        pickaxeLore.add("§7§m--------------");
        pickaxeLore.add("§71x Diamond Pickaxe");
        pickaxeLore.add("§7§m--------------");
        pickaxeLore.add("§ePrice: §a$50");
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta pickaxeIM = pickaxe.getItemMeta();
        pickaxeIM.setDisplayName(ChatColor.GREEN + "Fence Gate");
        pickaxeIM.setLore(pickaxeLore);
        pickaxe.setItemMeta(pickaxeIM);
        gui.setItem(48, pickaxe);

        List<String> axeLore = new ArrayList<>();
        axeLore.add("§7§m--------------");
        axeLore.add("§71x Diamond Axe");
        axeLore.add("§7§m--------------");
        axeLore.add("§ePrice: §a$50");
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta axeIM = axe.getItemMeta();
        axeIM.setDisplayName(ChatColor.GREEN + "Diamond Axe");
        axeIM.setLore(axeLore);
        axe.setItemMeta(axeIM);
        gui.setItem(49, axe);

        List<String> shovelLore = new ArrayList<>();
        shovelLore.add("§7§m--------------");
        shovelLore.add("§71x Diamond Shovel");
        shovelLore.add("§7§m--------------");
        shovelLore.add("§ePrice: §a$50");
        ItemStack shovel = new ItemStack(Material.DIAMOND_SPADE, 1);
        ItemMeta shovelIM = shovel.getItemMeta();
        shovelIM.setDisplayName(ChatColor.GREEN + "Diamond Shovel");
        shovelIM.setLore(shovelLore);
        shovel.setItemMeta(shovelIM);
        gui.setItem(50, shovel);

        p.openInventory(gui);
    }

    public static void openSellItems(Player p) {
        Inventory gui = Bukkit.getServer().createInventory(p, 9, "§c§lSell Items");

        int count = 0;
        for (ItemStack eachItem : p.getInventory().getContents()) {
            if (eachItem != null) {
                if (eachItem.getType().equals(Material.DIAMOND)) {
                    count += eachItem.getAmount();
                }
            }
        }
        List<String> diamondLore = new ArrayList<>();
        diamondLore.add("§7§m-------------------------");
        diamondLore.add("§7Right click to sell " + count + "x Diamonds for " + ChatColor.YELLOW + "$" + 35 * count);
        diamondLore.add("§7§m-------------------------");
        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        ItemMeta diamondIM = diamond.getItemMeta();
        diamondIM.setDisplayName(ChatColor.AQUA + "Sell Diamonds");
        diamondIM.setLore(diamondLore);
        diamond.setItemMeta(diamondIM);
        gui.setItem(1, diamond);

        int count1 = 0;
        for (ItemStack eachItem : p.getInventory().getContents()) {
            if (eachItem != null) {
                if (eachItem.getType().equals(Material.GOLD_INGOT)) {
                    count1 += eachItem.getAmount();
                }
            }
        }
        List<String> goldLore = new ArrayList<>();
        goldLore.add("§7§m-------------------------");
        goldLore.add("§7Right click to sell " + count1 + "x Gold for " + ChatColor.YELLOW + "$" + 25 * count1);
        goldLore.add("§7§m-------------------------");
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta goldIM = gold.getItemMeta();
        goldIM.setDisplayName(ChatColor.GOLD + "Sell Gold");
        goldIM.setLore(goldLore);
        gold.setItemMeta(goldIM);
        gui.setItem(3, gold);

        int count2 = 0;
        for (ItemStack eachItem : p.getInventory().getContents()) {
            if (eachItem != null) {
                if (eachItem.getType().equals(Material.IRON_INGOT)) {
                    count2 += eachItem.getAmount();
                }
            }
        }
        List<String> ironLore = new ArrayList<>();
        ironLore.add("§7§m-------------------------");
        ironLore.add("§7Right click to sell " + count2 + "x Iron for " + ChatColor.YELLOW + "$" + 15 * count2);
        ironLore.add("§7§m-------------------------");
        ItemStack iron = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta ironIM = iron.getItemMeta();
        ironIM.setDisplayName(ChatColor.GRAY + "Sell Iron");
        ironIM.setLore(ironLore);
        iron.setItemMeta(ironIM);
        gui.setItem(5, iron);

        int count3 = 0;
        for (ItemStack eachItem : p.getInventory().getContents()) {
            if (eachItem != null) {
                if (eachItem.getType().equals(Material.COAL)) {
                    count3 += eachItem.getAmount();
                }
            }
        }
        List<String> coalLore = new ArrayList<>();
        coalLore.add("§7§m-------------------------");
        coalLore.add("§7Right click to sell " + count3 + "x Coal for " + ChatColor.YELLOW + "$" + 10 * count3);
        coalLore.add("§7§m-------------------------");
        ItemStack coal = new ItemStack(Material.COAL, 1);
        ItemMeta coalIM = coal.getItemMeta();
        coalIM.setDisplayName(ChatColor.DARK_GRAY + "Sell Coal");
        coalIM.setLore(coalLore);
        coal.setItemMeta(coalIM);
        gui.setItem(7, coal);

        p.openInventory(gui);
    }

    public static void openEnchanter(Player p) {
        Inventory gui = Bukkit.getServer().createInventory(p, 9, "§c§lTim The Enchanter");


        List<String> protLore = new ArrayList<>();
        protLore.add("§7§m------------");
        protLore.add("§aPrice: $1000");
        protLore.add("§7§m------------");
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta bookIM = book.getItemMeta();
        bookIM.setDisplayName(ChatColor.GREEN + "Full Protection I");
        bookIM.setLore(protLore);
        book.setItemMeta(bookIM);
        gui.setItem(0, book);

        List<String> sharpLore = new ArrayList<>();
        sharpLore.add("§7§m------------");
        sharpLore.add("§aPrice: $200");
        sharpLore.add("§7§m------------");
        ItemStack sharp = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta sharpIM = sharp.getItemMeta();
        sharpIM.setDisplayName(ChatColor.GREEN + "Sharpness I");
        sharpIM.setLore(sharpLore);
        sharp.setItemMeta(sharpIM);
        gui.setItem(1, sharp);

        List<String> featherLore = new ArrayList<>();
        featherLore.add("§7§m------------");
        featherLore.add("§aPrice: $250");
        featherLore.add("§7§m------------");
        ItemStack feather = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta featherIM = feather.getItemMeta();
        featherIM.setDisplayName(ChatColor.GREEN + "Feather Falling IV");
        featherIM.setLore(featherLore);
        feather.setItemMeta(featherIM);
        gui.setItem(2, feather);

        p.openInventory(gui);
    }

    public static void openRepair(Player p) {
        Inventory gui = Bukkit.getServer().createInventory(p, 9, "§c§lRepair Menu");
        if (p.getInventory().getHelmet() != null) {
            String priceHelmStr = String.valueOf(((double) p.getInventory().getHelmet().getDurability() / 363.0) * 75.0);
            double priceHelm = Double.valueOf(priceHelmStr.substring(0, priceHelmStr.indexOf('.')));
            List<String> diaHelmLore = new ArrayList<>();
            diaHelmLore.add("§7§m--------------");
            diaHelmLore.add("§71x Diamond Helmet");
            diaHelmLore.add("§7§m--------------");
            diaHelmLore.add("§ePrice: §a$" + priceHelm);
            ItemStack diaHelm = new ItemStack(Material.DIAMOND_HELMET);
            ItemMeta diaHelmIM = diaHelm.getItemMeta();
            diaHelmIM.setDisplayName(ChatColor.GREEN + "Diamond Helmet");
            diaHelmIM.setLore(diaHelmLore);
            diaHelm.setItemMeta(diaHelmIM);
            if (p.getInventory().getHelmet() != null) {
                diaHelm.setDurability(p.getInventory().getHelmet().getDurability());
            }
            gui.setItem(0, diaHelm);
        }
        if (p.getInventory().getChestplate() != null) {
            String priceChestStr = String.valueOf(((double) p.getInventory().getChestplate().getDurability() / 528.0) * 200.0);
            double priceChest = Double.valueOf(priceChestStr.substring(0, priceChestStr.indexOf('.')));
            List<String> diaChestLore = new ArrayList<>();
            diaChestLore.add("§7§m------------------");
            diaChestLore.add("§71x Diamond Chestplate");
            diaChestLore.add("§7§m------------------");
            diaChestLore.add("§ePrice: §a$" + priceChest);
            ItemStack diaChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta diaChestIM = diaChest.getItemMeta();
            diaChestIM.setDisplayName(ChatColor.GREEN + "Diamond Chestplate");
            diaChestIM.setLore(diaChestLore);
            diaChest.setItemMeta(diaChestIM);
            if (p.getInventory().getChestplate() != null) {
                diaChest.setDurability(p.getInventory().getChestplate().getDurability());
            }
            gui.setItem(1, diaChest);
        }
        if (p.getInventory().getLeggings() != null) {
            String priceLegsStr = String.valueOf(((double) p.getInventory().getLeggings().getDurability() / 495.0) * 150.0);
            double priceLegs = Double.valueOf(priceLegsStr.substring(0, priceLegsStr.indexOf('.')));
            List<String> diaLegsLore = new ArrayList<>();
            diaLegsLore.add("§7§m-----------------");
            diaLegsLore.add("§71x Diamond Leggings");
            diaLegsLore.add("§7§m-----------------");
            diaLegsLore.add("§ePrice: §a$" + priceLegs);
            ItemStack diaLegs = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemMeta diaLegsIM = diaLegs.getItemMeta();
            diaLegsIM.setDisplayName(ChatColor.GREEN + "Diamond Leggings");
            diaLegsIM.setLore(diaLegsLore);
            diaLegs.setItemMeta(diaLegsIM);
            if (p.getInventory().getLeggings() != null) {
                diaLegs.setDurability(p.getInventory().getLeggings().getDurability());
            }
            gui.setItem(2, diaLegs);

        }
        if (p.getInventory().getBoots() != null) {
            String priceBootsStr = String.valueOf(((double) p.getInventory().getBoots().getDurability() / 420.0) * 75.0);
            double priceBoots = Double.valueOf(priceBootsStr.substring(0, priceBootsStr.indexOf('.')));
            List<String> diaBootsLore = new ArrayList<>();
            diaBootsLore.add("§7§m--------------");
            diaBootsLore.add("§71x Diamond Boots");
            diaBootsLore.add("§7§m--------------");
            diaBootsLore.add("§ePrice: §a$" + priceBoots);
            ItemStack diaBoots = new ItemStack(Material.DIAMOND_BOOTS);
            ItemMeta diaBootsIM = diaBoots.getItemMeta();
            diaBootsIM.setDisplayName(ChatColor.GREEN + "Diamond Boots");
            diaBootsIM.setLore(diaBootsLore);
            diaBoots.setItemMeta(diaBootsIM);
            if (p.getInventory().getBoots() != null) {
                diaBoots.setDurability(p.getInventory().getBoots().getDurability());
            }
            gui.setItem(3, diaBoots);

        }
        if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {

            String priceHelmStr = String.valueOf(((double) p.getInventory().getHelmet().getDurability() / 363.0) * 75.0);
            double priceHelm = Double.valueOf(priceHelmStr.substring(0, priceHelmStr.indexOf('.')));

            String priceChestStr = String.valueOf(((double) p.getInventory().getChestplate().getDurability() / 528.0) * 200.0);
            double priceChest = Double.valueOf(priceChestStr.substring(0, priceChestStr.indexOf('.')));

            String priceLegsStr = String.valueOf(((double) p.getInventory().getLeggings().getDurability() / 495.0) * 150.0);
            double priceLegs = Double.valueOf(priceLegsStr.substring(0, priceLegsStr.indexOf('.')));

            String priceBootsStr = String.valueOf(((double) p.getInventory().getBoots().getDurability() / 420.0) * 75.0);
            double priceBoots = Double.valueOf(priceBootsStr.substring(0, priceBootsStr.indexOf('.')));

            List<String> diaLoadoutLore = new ArrayList<>();
            diaLoadoutLore.add("§7§m-------------------");
            diaLoadoutLore.add("§71x Diamond Helmet");
            diaLoadoutLore.add("§71x Diamond Chestplate");
            diaLoadoutLore.add("§71x Diamond Leggings");
            diaLoadoutLore.add("§71x Diamond Boots");
            diaLoadoutLore.add("§7§m-------------------");
            diaLoadoutLore.add("§ePrice: §a$" + (priceHelm + priceChest + priceLegs + priceBoots));
            ItemStack diaLoadout = new ItemStack(Material.DIAMOND);
            ItemMeta diaLoadoutIM = diaLoadout.getItemMeta();
            diaLoadoutIM.setDisplayName(ChatColor.GREEN + "Diamond Set");
            diaLoadoutIM.setLore(diaLoadoutLore);
            diaLoadout.setItemMeta(diaLoadoutIM);
            gui.setItem(8, diaLoadout);
        }

        if (p.getInventory().getHelmet() == null && p.getInventory().getChestplate() == null && p.getInventory().getLeggings() == null && p.getInventory().getBoots() == null){
            ItemStack noItems = new ItemStack(Material.COAL);
            ItemMeta noItemsIM = noItems.getItemMeta();
            noItemsIM.setDisplayName(ChatColor.RED + "No Items Need Repairing");
            noItems.setItemMeta(noItemsIM);
            gui.setItem(4, noItems);
        }
        p.openInventory(gui);
    }
}
