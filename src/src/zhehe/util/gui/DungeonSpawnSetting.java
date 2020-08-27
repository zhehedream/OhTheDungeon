/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.util.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zhehe.util.I18n;
import zhehe.util.config.SimpleWorldConfig;
import zhehe.util.config.WorldConfig;

/**
 *
 * @author
 */
public class DungeonSpawnSetting extends Content {
    private final static int SLOT = 27;
    public static DungeonSpawnSetting instance = new DungeonSpawnSetting();
    public final World world;
    public final Content parent;
    
    private int roguelike;
    private int doomlike;
    private int battle;
    private int smoofy;
    private int distance;
    
    private final static Material ROGUELIKE = Material.DIAMOND_BLOCK;
    private final static Material DOOMLIKE = Material.GOLD_BLOCK;
    private final static Material BATTLE = Material.IRON_BLOCK;
    private final static Material SMOOFY = Material.COBBLESTONE;
    
    private DungeonSpawnSetting() {
        super("", SLOT);
        this.world = null;
        this.parent = null;
    }
    
    public DungeonSpawnSetting(World world, Content parent) {
        super(I18n.instance.Dungeon_Spawn_Setting, SLOT);
        this.world = world;
        this.parent = parent;
        
        SimpleWorldConfig swc;
        String world_name = this.world.getName();
        if(!WorldConfig.wc.dict.containsKey(world_name)) {
            swc = new SimpleWorldConfig();
            WorldConfig.wc.dict.put(world_name, swc);
            WorldConfig.save();
        } else {
            swc = WorldConfig.wc.dict.get(world_name);
        }
        
        this.roguelike = swc.roguelike_weight;
        this.doomlike = swc.doomlike_weight;
        this.battle = swc.battle_tower_weight;
        this.smoofy = swc.smoofy_weight;
        this.distance = swc.distance;
    }
    
    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof DungeonSpawnSetting)) {
            return;
        }

        kcancel(e);
        ClickType type = e.getClick();
        
        int slot = e.getRawSlot();
        if(slot < 0 && slot >= 27) {
            return;
        }
        DungeonSpawnSetting holder = (DungeonSpawnSetting) e.getInventory().getHolder();
        if(holder == null) return;
        if(slot == 9) {
            if(type == ClickType.LEFT) {
                holder.roguelike++;
                holder.init();
            }
            if(type == ClickType.RIGHT) {
                holder.roguelike--;
                holder.init();
            }
        }
        if(slot == 10) {
            if(type == ClickType.LEFT) {
                holder.doomlike++;
                holder.init();
            }
            if(type == ClickType.RIGHT) {
                holder.doomlike--;
                holder.init();
            }
        }
        if(slot == 11) {
            if(type == ClickType.LEFT) {
                holder.battle++;
                holder.init();
            }
            if(type == ClickType.RIGHT) {
                holder.battle--;
                holder.init();
            }
        }
        if(slot == 12) {
            if(type == ClickType.LEFT) {
                holder.smoofy++;
                holder.init();
            }
            if(type == ClickType.RIGHT) {
                holder.smoofy--;
                holder.init();
            }
        }
        if(slot == 18) {
            if(type == ClickType.LEFT) {
                holder.distance++;
                holder.init();
            }
            if(type == ClickType.RIGHT) {
                holder.distance--;
                if(holder.distance < 15) holder.distance = 15;
                holder.init();
            }
        }
        if(slot == 26) {
            if(type == ClickType.LEFT) {
                SimpleWorldConfig swc = WorldConfig.wc.dict.get(holder.world.getName());
                swc.roguelike_weight = holder.roguelike;
                swc.doomlike_weight = holder.doomlike;
                swc.battle_tower_weight = holder.battle;
                swc.smoofy_weight = holder.smoofy;
                swc.distance = holder.distance;
                WorldConfig.save();
                Player p = (Player) e.getWhoClicked();
                p.sendMessage(I18n.instance.World_Config_Save);
                holder.parent.openInventory(p);
            }
        }
    }
    
    @Override
    public void init() {
        if(this.battle < 0) this.battle = 0;
        if(this.doomlike < 0) this.doomlike = 0;
        if(this.roguelike < 0) this.doomlike = 0;
        if(this.smoofy < 0) this.smoofy = 0;
        inv.clear();
        {
            ItemStack is = new ItemStack(Material.OAK_SIGN);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Dungeon_Spawn_Tip);
            is.setItemMeta(im);
            addItem(0, 0, is);
        }
        int total = this.battle + this.doomlike + this.roguelike + this.smoofy;
        {
            ItemStack is = new ItemStack(ROGUELIKE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Roguelike_Dungeon);
            List<String> lores = new ArrayList<>();
            lores.add(Integer.toString(this.roguelike) + " / " + Integer.toString(total));
            lores.add(I18n.instance.Amount_Item_Tip1);
            lores.add(I18n.instance.Amount_Item_Tip2);
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 0, is);
        }
        {
            ItemStack is = new ItemStack(DOOMLIKE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Doomlike_Dungeon);
            List<String> lores = new ArrayList<>();
            lores.add(Integer.toString(this.doomlike) + " / " + Integer.toString(total));
            lores.add(I18n.instance.Amount_Item_Tip1);
            lores.add(I18n.instance.Amount_Item_Tip2);
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 1, is);
        }
        {
            ItemStack is = new ItemStack(BATTLE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Battle_Tower);
            List<String> lores = new ArrayList<>();
            lores.add(Integer.toString(this.battle) + " / " + Integer.toString(total));
            lores.add(I18n.instance.Amount_Item_Tip1);
            lores.add(I18n.instance.Amount_Item_Tip2);
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 2, is);
        }
        {
            ItemStack is = new ItemStack(SMOOFY);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Smoofy_Dungeon);
            List<String> lores = new ArrayList<>();
            lores.add(Integer.toString(this.smoofy) + " / " + Integer.toString(total));
            lores.add(I18n.instance.Amount_Item_Tip1);
            lores.add(I18n.instance.Amount_Item_Tip2);
            im.setLore(lores);
            is.setItemMeta(im);
            addItem(1, 3, is);
        }
        {
            ItemStack is = new ItemStack(Material.MAP);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Average_Dungeon_Chunk_Distance);
            
            List<String> lores = new ArrayList<>();
            lores.add(Integer.toString(distance));
            lores.add(I18n.instance.Amount_Item_Tip1);
            lores.add(I18n.instance.Amount_Item_Tip2);
            im.setLore(lores);
            is.setItemMeta(im);
            
            addItem(2, 0, is);
        }
        {
            ItemStack is = new ItemStack(Material.LEVER);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Back);
            is.setItemMeta(im);
            
            addItem(2, 8, is);
        }

    }
}
