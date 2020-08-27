/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.util.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zhehe.util.I18n;
import zhehe.util.config.LootNode;
import zhehe.util.config.RoguelikeLootNode;
import zhehe.util.config.WorldConfig;

/**
 *
 * @author
 */
public class RoguelikeLootManager extends Content {
    public final List<RoguelikeLootNode> loots;
    public final Content parent;
    private final static int SLOT = 54;
    public int offset;
    
    private final static Material PAGE = Material.END_CRYSTAL;
    private final static Material ISO = Material.BLUE_STAINED_GLASS_PANE;
    
    private RoguelikeLootManager() {
        super("", SLOT);
        loots = null;
        offset = 0;
        parent = null;
    }
    public static RoguelikeLootManager instance = new RoguelikeLootManager();
    
    public RoguelikeLootManager(List<RoguelikeLootNode> loots, Content parent) {
        super(I18n.instance.Loot_Manager, SLOT);
        this.loots = loots;
        offset = 0;
        this.parent = parent;
    }
    
    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof RoguelikeLootManager)) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            kcancel(e);
            return;
        }

        kcancel(e);

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        
        int slot = e.getRawSlot();
        RoguelikeLootManager holder = (RoguelikeLootManager) e.getInventory().getHolder();
        if(holder == null) return;
        if(slot == 0) {
            RoguelikeLootItem li = new RoguelikeLootItem(holder.loots, holder.loots.size(), holder);
            li.openInventory(p);
        }
        if(slot == 6) {
            holder.offset--;
            if(holder.offset < 0) holder.offset = 0;
            holder.init();
        }
        if(slot == 7) {
            holder.offset++;
            holder.init();
        }
        if(slot == 8) {
            WorldConfig.save();
            holder.parent.openInventory(p);
        }
        
        if(slot >= 18 && slot <= 53) {
            int index = holder.offset * 36 + slot - 18;
            if(index >= holder.loots.size()) return;
            RoguelikeLootItem li = new RoguelikeLootItem(holder.loots, index, holder);
            li.openInventory(p);
        }
    }
    
    @Override
    public void init() {
        WorldConfig.save();
        inv.clear();
        {
            ItemStack is = new ItemStack(Material.CHEST);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Add_New_Loot);
            is.setItemMeta(im);
            
            addItem(0, 0, is);
        }
        {
            ItemStack is = new ItemStack(PAGE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Previous);
            List<String> lores = new ArrayList<>();
            lores.add(I18n.instance.Current_Page + " : " + Integer.toString(offset + 1));
            im.setLore(lores);
            is.setItemMeta(im);
            
            addItem(0, 6, is);
        }
        {
            ItemStack is = new ItemStack(PAGE);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Next);
            List<String> lores = new ArrayList<>();
            lores.add(I18n.instance.Current_Page + " : " + Integer.toString(offset + 1));
            im.setLore(lores);
            is.setItemMeta(im);
            
            addItem(0, 7, is);
        }
        {
            ItemStack is = new ItemStack(Material.STONE_BUTTON);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Back);
            is.setItemMeta(im);
            
            addItem(0, 8, is);
        }
        {
            for(int i = 0; i < 9; i++) {
                ItemStack is = new ItemStack(ISO);
                addItem(1, i, is);
            }
        }
        {
            int index = 18;
            int i = offset * 36;
            while(index < SLOT && i < loots.size()) {
                RoguelikeLootNode it = loots.get(i);
                ItemStack is = it.getItem().clone();
                ItemMeta im = is.getItemMeta();
                if(im != null) {
                    List<String> lores;
                    if(im.hasLore()) lores = im.getLore();
                    else lores = new ArrayList<>();
                    lores.add(0, I18n.instance.Click_To_Edit);
                    lores.add(0, I18n.instance.Min_Item + " : " + Integer.toString(it.min));
                    lores.add(0, I18n.instance.Max_Item + " : " + Integer.toString(it.max));
                    lores.add(0, I18n.instance.Loot_Weight + " : " + Double.toString(it.weight));
                    im.setLore(lores);
                    is.setItemMeta(im);
                    addItem(index, is);
                }
                index++;
                i++;
            }
        }
    }
}
