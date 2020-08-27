package forge_sandbox.greymerk.roguelike.treasure.loot;


import java.util.Random;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.util.IWeighted;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import otd.Main;
import otd.MultiVersion;
import zhehe.util.FormatItem;
import zhehe.util.nbt.JsonToNBT;
//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.JsonToNBT;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.ResourceLocation;

public class WeightedRandomLoot implements Comparable<WeightedRandomLoot>, IWeighted<ItemStack>{
	
	
	private String name;
	private ItemStack item;
	private Material block;
	private int damage;
	private int min;
	private int max;
	private int enchLevel;
	private int weight;
	
	private Object nbt;
        
        
	
	public WeightedRandomLoot(Material block, int damage, int minStackSize, int maxStackSize, int weight){
		this.name = (new FormatItem(block)).getUnlocalizedName();
		this.block = block;
		this.damage = damage;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
		this.enchLevel = 0;
	}
	
	public WeightedRandomLoot(Material item, int damage, int minStackSize, int maxStackSize, int weight, int ench){
		
		this.name = (new FormatItem(item)).getUnlocalizedName();
		this.item = new ItemStack(item);
		this.damage = damage;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
		this.enchLevel = ench;
	}

	public WeightedRandomLoot(Material item, int damage, int weight){
		this(item, damage, 1, 1, weight, 0);
	}
	
	public WeightedRandomLoot(Material item, int weight){
		this(item, 0, 1, 1, weight, 0);
	}
	
	public WeightedRandomLoot(JsonObject json, int weight) throws Exception{
	
		this.name = json.get("name").getAsString();
                String array[] = name.split(":");
                Material material;
                try {
                    material = Material.valueOf(array[1].toUpperCase());
                } catch (Exception ex) {
                    material = Material.AIR;
                    Bukkit.getLogger().log(Level.SEVERE, "Invalid item: " + name);
                }
                this.item = new ItemStack(material);
//		ResourceLocation location = new ResourceLocation(name);
//		this.item = (Item) Item.REGISTRY.getObject(location);
//		try{
//			this.item.getUnlocalizedName();
//		} catch (NullPointerException e){
//			throw new Exception("Invalid item: " + this.name);
//		}
		this.damage = json.has("meta") ? json.get("meta").getAsInt() : 0;
		this.weight = weight;
		this.enchLevel = json.has("ench") ? json.get("ench").getAsInt() : 0;

		if(json.has("min") && json.has("max")){
			min = json.get("min").getAsInt();
			max = json.get("max").getAsInt();	
		} else {
			min = 1;
			max = 1;
		}
		
		if(json.has("nbt")) this.nbt = (Object) JsonToNBT.getTagFromJson(json.get("nbt").getAsString());
		
	}

	private int getStackSize(Random rand){
		if (max == 1) return 1;
		
		return rand.nextInt(max - min) + min;
	}

	
	@Override
	public int getWeight(){
		return this.weight;
	}
        
        private static class Get114 {
            public ItemStack get(ItemStack item, Object nbt) {
                net.minecraft.server.v1_14_R1.ItemStack tmp = 
                        org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asNMSCopy(item);
                tmp.setTag((net.minecraft.server.v1_14_R1.NBTTagCompound) nbt);
                item = org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack.asBukkitCopy(tmp);
                return item;
            }
        }
        
        private static class Get115 {
            public ItemStack get(ItemStack item, Object nbt) {
                net.minecraft.server.v1_15_R1.ItemStack tmp = 
                        org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asNMSCopy(item);
                tmp.setTag((net.minecraft.server.v1_15_R1.NBTTagCompound) nbt);
                item = org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack.asBukkitCopy(tmp);
                return item;
            }
        }
        
        private static class Get116 {
            public ItemStack get(ItemStack item, Object nbt) {
                net.minecraft.server.v1_16_R1.ItemStack tmp = 
                        org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack.asNMSCopy(item);
                tmp.setTag((net.minecraft.server.v1_16_R1.NBTTagCompound) nbt);
                item = org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack.asBukkitCopy(tmp);
                return item;
            }
        }
        
        private static class Get116R2 {
            public ItemStack get(ItemStack item, Object nbt) {
                net.minecraft.server.v1_16_R2.ItemStack tmp = 
                        org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack.asNMSCopy(item);
                tmp.setTag((net.minecraft.server.v1_16_R2.NBTTagCompound) nbt);
                item = org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack.asBukkitCopy(tmp);
                return item;
            }
        }

	@Override
	public ItemStack get(Random rand) {
		ItemStack item = null;
		if(this.item != null) item = this.item;
		if(this.block != null) item = new ItemStack(this.block, this.getStackSize(rand));
		try{
			if(this.enchLevel > 0 && this.enchLevel <= 30) Enchant.enchantItem(rand, item, this.enchLevel);
		} catch (NullPointerException e){
			// ignore
		}
		if(this.nbt != null) {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        item = (new Get114()).get(item, this.nbt);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        item = (new Get115()).get(item, this.nbt);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        item = (new Get116()).get(item, this.nbt);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        item = (new Get116R2()).get(item, this.nbt);
                    }
                }
		return item;
	}

	@Override
	public int compareTo(WeightedRandomLoot other) {
		if (this.weight > other.weight) return -1;
		if (this.weight < other.weight) return 1;
		
		return 0;
	}
}