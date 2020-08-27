/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.draylar.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 */
public class LootTable {
    
    private static class Node {
        Material item;
        int min, max;
        public Node(Material item, int min, int max) {
            this.item = item;
            this.max = max;
            this.min = min;
        }
        
        public ItemStack getItem(Random random) {
            int amount = min;
            if(min != max)  amount = min + random.nextInt(max - min);
            return new ItemStack(item, amount);
        }
    }
    
    private static class CoralNode extends Node {
        public CoralNode() {
            super(Material.AIR, 1, 1);
            coral_min = 1;
            coral_max = 10;
        }
        
        private final static List<Material> CORAL;
        static {
            CORAL = new ArrayList<>();
            for(Material type : Material.values()) {
                if(type.toString().toUpperCase().contains("CORAL")) {
                    CORAL.add(type);
                }
            }
        }
        
        private final int coral_max, coral_min;
        
        @Override
        public ItemStack getItem(Random random) {
            Material type = CORAL.get(random.nextInt(CORAL.size()));
            int amount = coral_min + random.nextInt(coral_max - coral_min);
            return new ItemStack(type, amount);
        }
    }
    
    private final static List<Node> BONUS;
    static {
        BONUS = new ArrayList<>();
        BONUS.add(new Node(Material.EMERALD, 1, 3));
        BONUS.add(new Node(Material.OBSIDIAN, 4, 7));
        BONUS.add(new Node(Material.NAUTILUS_SHELL, 1, 1));
        BONUS.add(new Node(Material.SCAFFOLDING, 28, 60));
        BONUS.add(new Node(Material.DIAMOND, 1, 3));
        BONUS.add(new Node(Material.PACKED_ICE, 10, 15));
        BONUS.add(new Node(Material.BLUE_ICE, 10, 15));
        BONUS.add(new Node(Material.PHANTOM_MEMBRANE, 2, 8));
        BONUS.add(new Node(Material.SEA_PICKLE, 1, 15));
        BONUS.add(new Node(Material.DRIED_KELP_BLOCK, 5, 12));
        BONUS.add(new Node(Material.WITHER_ROSE, 1, 3));
        BONUS.add(new Node(Material.SCUTE, 2, 8));
        BONUS.add(new CoralNode());
//        for(Material type : Material.values()) {
//            if(type.toString().toUpperCase().contains("CORAL")) {
//                BONUS.add(new Node(type, 1, 10));
//            }
//        }
    }
    
    private final static int MAX_LOOT_COUNT = 9;
    public static int getRandomLootCount(Random rand) {
        return rand.nextInt(MAX_LOOT_COUNT) + 1;
    }
    
    public static ItemStack getRandomLootItem(Random rand) {
        Node node = BONUS.get(rand.nextInt(BONUS.size()));
        return node.getItem(rand);
    }
}
