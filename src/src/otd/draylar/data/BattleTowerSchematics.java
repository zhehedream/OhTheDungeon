/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.draylar.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import zhehe.util.BiomeDictionary;
import zhehe.util.BiomeDictionary.Type;

/**
 *
 * @author
 */
public class BattleTowerSchematics {
    public static BattleTowerSchematic frosted_default;
    public static BattleTowerSchematic frosted_default_spike_1;
    public static BattleTowerSchematic frosted_entrance;
    public static BattleTowerSchematic frosted_hole;
    public static BattleTowerSchematic frosted_top;
    
    public static BattleTowerSchematic jungle_branch;
    public static BattleTowerSchematic jungle_default;
    public static BattleTowerSchematic jungle_entrance;
    public static BattleTowerSchematic jungle_lookout;
    public static BattleTowerSchematic jungle_stairs_wrap;
    public static BattleTowerSchematic jungle_top;
    
    public static BattleTowerSchematic mushroom_default;
    public static BattleTowerSchematic mushroom_entrance;
    public static BattleTowerSchematic mushroom_top;
    
    public static BattleTowerSchematic sandy_default;
    public static BattleTowerSchematic sandy_entrance;
    public static BattleTowerSchematic sandy_top;
    public static BattleTowerSchematic sandy_sandcastle_top;

    public static BattleTowerSchematic stone_blacksmith;
    public static BattleTowerSchematic stone_bottom;
    public static BattleTowerSchematic stone_default;
    public static BattleTowerSchematic stone_entrance;
    public static BattleTowerSchematic stone_lake;
    public static BattleTowerSchematic stone_library;
    public static BattleTowerSchematic stone_lookout;
    public static BattleTowerSchematic stone_mineshaft;
    public static BattleTowerSchematic stone_original;
    public static BattleTowerSchematic stone_plains;
    public static BattleTowerSchematic stone_top;
    
    public static void init(JavaPlugin plugin) {
        frosted_default = new BattleTowerSchematic(plugin, "draylars-battle-towers/frosted/default.json");
        frosted_default_spike_1 = new BattleTowerSchematic(plugin, "draylars-battle-towers/frosted/default_spike_1.json");
        frosted_entrance = new BattleTowerSchematic(plugin, "draylars-battle-towers/frosted/entrance.json", false, true);
        frosted_hole = new BattleTowerSchematic(plugin, "draylars-battle-towers/frosted/hole.json");
        frosted_top = new BattleTowerSchematic(plugin, "draylars-battle-towers/frosted/top.json", true, false);
        
        
        jungle_branch = new BattleTowerSchematic(plugin, "draylars-battle-towers/jungle/branch.json");
        jungle_default = new BattleTowerSchematic(plugin, "draylars-battle-towers/jungle/default.json");
        jungle_entrance = new BattleTowerSchematic(plugin, "draylars-battle-towers/jungle/entrance.json", false, true);
        jungle_lookout = new BattleTowerSchematic(plugin, "draylars-battle-towers/jungle/lookout.json");
        jungle_stairs_wrap = new BattleTowerSchematic(plugin, "draylars-battle-towers/jungle/stairs_wrap.json");
        jungle_top = new BattleTowerSchematic(plugin, "draylars-battle-towers/jungle/top.json", true, false);


        mushroom_default = new BattleTowerSchematic(plugin, "draylars-battle-towers/mushroom/default.json");
        mushroom_entrance = new BattleTowerSchematic(plugin, "draylars-battle-towers/mushroom/entrance.json");
        mushroom_top = new BattleTowerSchematic(plugin, "draylars-battle-towers/mushroom/top.json", true, false);


        sandy_default = new BattleTowerSchematic(plugin, "draylars-battle-towers/sandy/default.json");
        sandy_entrance = new BattleTowerSchematic(plugin, "draylars-battle-towers/sandy/entrance.json", false, true);
        sandy_top = new BattleTowerSchematic(plugin, "draylars-battle-towers/sandy/top.json");
        sandy_sandcastle_top = new BattleTowerSchematic(plugin, "draylars-battle-towers/sandy/sandcastle_top.json", true, false);


        stone_blacksmith = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/blacksmith.json");
//        stone_bottom = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/bottom.json");
        stone_default = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/default.json");
        stone_entrance = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/entrance.json", false, true);
        stone_lake = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/lake.json");
        stone_library = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/library.json");
        stone_lookout = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/lookout.json");
        stone_mineshaft = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/mineshaft.json");
        stone_original = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/original.json");
        stone_plains = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/plains.json");
        stone_top = new BattleTowerSchematic(plugin, "draylars-battle-towers/stone/top.json", true, false);
        
        {
            FROSTED = new Theme();
            FROSTED.type = Type.COLD;
            FROSTED.top = frosted_top;
            FROSTED.bottom = stone_bottom;
            FROSTED.entrance = frosted_entrance;
            FROSTED.layers = new BattleTowerSchematic[] {
                frosted_default, frosted_default_spike_1, frosted_hole,
            };
        }
        {
            JUNGLE = new Theme();
            JUNGLE.type = Type.JUNGLE;
            JUNGLE.top = jungle_top;
            JUNGLE.bottom = stone_bottom;
            JUNGLE.entrance = jungle_entrance;
            JUNGLE.layers = new BattleTowerSchematic[] {
                jungle_branch, jungle_default, jungle_lookout, jungle_stairs_wrap,
            };
        }
        {
            MUSHROOM = new Theme();
            MUSHROOM.type = Type.MUSHROOM;
            MUSHROOM.top = mushroom_top;
            MUSHROOM.bottom = stone_bottom;
            MUSHROOM.entrance = mushroom_entrance;
            MUSHROOM.layers = new BattleTowerSchematic[] {
                mushroom_default,
            };
        }
        {
            SANDY1 = new Theme();
            SANDY1.type = Type.SANDY;
            SANDY1.top = sandy_top;
            SANDY1.bottom = stone_bottom;
            SANDY1.entrance = sandy_entrance;
            SANDY1.layers = new BattleTowerSchematic[] {
                sandy_default,
            };
        }
        {
            SANDY2 = new Theme();
            SANDY2.type = Type.SANDY;
            SANDY2.top = sandy_sandcastle_top;
            SANDY2.bottom = stone_bottom;
            SANDY2.entrance = sandy_entrance;
            SANDY2.layers = new BattleTowerSchematic[] {
                sandy_default,
            };
        }
        {
            STONE = new Theme();
            STONE.type = null;
            STONE.top = stone_top;
            STONE.bottom = stone_bottom;
            STONE.entrance = stone_entrance;
            STONE.layers = new BattleTowerSchematic[] {
                stone_blacksmith,
                stone_default,
                stone_lake,
                stone_library,
                stone_lookout,
                stone_mineshaft,
                stone_original,
                stone_plains,
            };
        }
        bottom = stone_entrance.getLayer(0);
    }
    
    private static List<int[]> bottom;
    
    
    public final static int MAX_LEVEL = 10;
    
    public static class Theme {
        public Type type;
        public BattleTowerSchematic top, bottom, entrance;
        public BattleTowerSchematic[] layers;
    }
    
    public static Theme FROSTED;
    public static Theme JUNGLE;
    public static Theme MUSHROOM;
    public static Theme SANDY1, SANDY2;
    public static Theme STONE;
    
    public static boolean place(World world, Random rand, int x, int z) {
        int y = world.getHighestBlockYAt(x, z);
        
        if(y < 30 || y > 200) return false;
        
        for(int[] pos : bottom) {
            int xx = x + pos[0] - 8;
            int zz = z + pos[2] - 8;
            
            int yy = world.getHighestBlockYAt(xx, zz);
            if(yy < y) {
                for(int i = y; i > 0; i--) {
                    Block block = world.getBlockAt(xx, i, zz);
                    if(!block.getType().isSolid()) block.setType(Material.STONE, false);
                }
            }
        }
        
        int level = 0;
        Biome biome = world.getBiome(x, z);
        Set<Type> types = BiomeDictionary.getTypes(biome);
        
        List<Theme> themes = new ArrayList<>();
        themes.add(STONE);
        
        if(types.contains(FROSTED.type)) themes.add(FROSTED);
        if(types.contains(JUNGLE.type)) themes.add(JUNGLE);
        if(types.contains(MUSHROOM.type)) themes.add(MUSHROOM);
        if(types.contains(SANDY1.type)) themes.add(SANDY1);
        if(types.contains(SANDY2.type)) themes.add(SANDY2);
        
        
        //todo entrance
        Theme theme = themes.get(rand.nextInt(themes.size()));
        
        theme.entrance.place(world, x, y, z, rand);
        level++;
        y += theme.entrance.getYIncrement();
        
        
        while(level < 10) {
            BattleTowerSchematic layer = theme.layers[rand.nextInt(theme.layers.length)];
            layer.place(world, x, y, z, rand);
            level++;
            y += layer.getYIncrement();
        }
        
        theme.top.place(world, x, y, z, rand);
        
        return true;
    }
}
