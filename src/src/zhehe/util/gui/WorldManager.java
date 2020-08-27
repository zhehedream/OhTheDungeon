/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.util.gui;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import shadow_manager.DungeonWorldManager;
import zhehe.util.I18n;
import zhehe.util.config.SimpleWorldConfig;
import zhehe.util.config.WorldConfig;

/**
 *
 * @author
 */
public class WorldManager extends Content {
    private final static int SLOT = 54;
    
    public static WorldManager instance = new WorldManager();
    
    public WorldManager() {
        super(I18n.instance.World_Manager, SLOT);
        worlds = new ArrayList<>();
        offset = 0;
    }
    
    public final static Material NORMAL = Material.GRASS_BLOCK;
    public final static Material NETHER = Material.NETHER_BRICKS;
    public final static Material ENDER = Material.END_PORTAL_FRAME;
    public final static Material MAP = Material.WRITTEN_BOOK;
    
    private List<World> worlds;
    private int offset;
    
    private final static String WORLD_KEYWORD = "World";
    
    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof WorldManager)) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            kcancel(e);
            return;
        }
        kcancel(e);
        
        int slot = e.getRawSlot();
        if(slot < 0 && slot >= 54) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        ItemStack is = clickedItem;
        ItemMeta im = is.getItemMeta();
        if(im == null) return;
        String name = im.getDisplayName();
        WorldManager holder = (WorldManager) e.getInventory().getHolder();
        
        if(name.equals(I18n.instance.Previous)) {
            holder.offset--;
            if(holder.offset < 0) holder.offset = 0;
            holder.init();
            return;
        }
        if(name.equals(I18n.instance.Next)) {
            holder.offset++;
            holder.init();
            return;
        }
        List<String> lores = im.getLore();
        if(lores == null) return;
        if(lores.isEmpty()) return;
        String keyword = lores.get(0);
        if(keyword.equals(WORLD_KEYWORD)) {
            String world_name = im.getDisplayName();
            World world = Bukkit.getServer().getWorld(world_name.trim());
            WorldEditor we = new WorldEditor(world);
            we.openInventory(p);
        }
    }
    
    @Override
    public void init() {
        inv.clear();
        worlds.clear();
        for(World w : Bukkit.getServer().getWorlds()) {
//            if(w.getName().equals(DungeonWorldManager.WORLD_NAME)) continue;
            worlds.add(w);
        }
        {
            ItemStack first = new ItemStack(Material.OAK_SIGN);
            ItemMeta im = first.getItemMeta();
            List<String> lores = new ArrayList<>();
            lores.add(I18n.instance.World_List);
            im.setLore(lores);
            first.setItemMeta(im);

            addItem(0, 0, first);
        }
        {
            ItemStack is = new ItemStack(Material.END_CRYSTAL);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Previous);
            is.setItemMeta(im);
            
            addItem(5, 0, is);
        }
        {
            ItemStack is = new ItemStack(Material.END_CRYSTAL);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Next);
            is.setItemMeta(im);
            
            addItem(5, 8, is);
        }
//        {
//            ItemStack is = new ItemStack(Material.BOOK);
//            ItemMeta im = is.getItemMeta();
//            im.setDisplayName(I18n.instance.Tip);
//            List<String> lores = new ArrayList<>();
//            for(String str : I18n.instance.World_Tip) lores.add(str);
//            im.setLore(lores);
//            is.setItemMeta(im);
//            
//            addItem(5, 4, is);
//        }
        if(offset * 4 * 9 > worlds.size()) {
            return;
        }
        
        int start = offset * 4 * 9;
        int count = 0;
        while(count < 4 * 9 && start + count < worlds.size()) {
            World w = worlds.get(start + count);
            Material mat;
            Environment env = w.getEnvironment();
            switch (env) {
                case NORMAL:
                    mat = NORMAL;
                    break;
                case NETHER:
                    mat = NETHER;
                    break;
                case THE_END:
                    mat = ENDER;
                    break;
                default:
                    mat = ENDER;
                    break;
            }
            if(w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
                mat = MAP;
            }
            
            ItemStack is = new ItemStack(mat);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(w.getName());
            
            if(WorldConfig.wc.dict.containsKey(w.getName())) {
                SimpleWorldConfig config = WorldConfig.wc.dict.get(w.getName());
                List<String> lores = new ArrayList<>();
                lores.add(WORLD_KEYWORD);
                if(w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
                    lores.add(I18n.instance.PPDI_WORLD);
                } else {
                    if(config.roguelike.doNaturalSpawn) {
                        lores.add(I18n.instance.Roguelike_Dungeon_Natural_Spawn + " : " + ChatColor.RED + I18n.instance.Enable);
                    } else {
                        lores.add(I18n.instance.Roguelike_Dungeon_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    }
                    if(config.doomlike.doNaturalSpawn) {
                        lores.add(I18n.instance.Doomlike_Dungeon_Natural_Spawn + " : " + ChatColor.RED + I18n.instance.Enable);
                    } else {
                        lores.add(I18n.instance.Doomlike_Dungeon_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    }
                    if(config.battletower.doNaturalSpawn) {
                        lores.add(I18n.instance.Battle_Tower_Natural_Spawn + " : " + ChatColor.RED + I18n.instance.Enable);
                    } else {
                        lores.add(I18n.instance.Battle_Tower_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    }
                    if(config.smoofydungeon.doNaturalSpawn) {
                        lores.add(I18n.instance.Smoofy_Dungeon_Natural_Spawn + " : " + ChatColor.RED + I18n.instance.Enable);
                    } else {
                        lores.add(I18n.instance.Smoofy_Dungeon_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    }
                    if(config.draylar_battletower.doNaturalSpawn) {
                        lores.add(I18n.instance.Draylar_Battle_Tower_Natural_Spawn + " : " + ChatColor.RED + I18n.instance.Enable);
                    } else {
                        lores.add(I18n.instance.Draylar_Battle_Tower_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    }
                }
                im.setLore(lores);
            } else {
                List<String> lores = new ArrayList<>();
                lores.add(WORLD_KEYWORD);
                if(w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
                    lores.add(I18n.instance.PPDI_WORLD);
                } else {
                    lores.add(I18n.instance.Roguelike_Dungeon_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    lores.add(I18n.instance.Doomlike_Dungeon_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    lores.add(I18n.instance.Battle_Tower_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    lores.add(I18n.instance.Smoofy_Dungeon_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                    lores.add(I18n.instance.Draylar_Battle_Tower_Natural_Spawn + " : " + ChatColor.GRAY + I18n.instance.Disable);
                }
                im.setLore(lores);
            }
            is.setItemMeta(im);
            
            addItem(9 + count, is);
            
            count++;
        }
    }
}
