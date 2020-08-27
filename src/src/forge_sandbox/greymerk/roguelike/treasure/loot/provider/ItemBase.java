package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import forge_sandbox.greymerk.roguelike.util.IWeighted;

import java.util.Random;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.item.ItemStack;

public abstract class ItemBase implements IWeighted<ItemStack> {

	private int weight;
	int level;
	
	public ItemBase(int weight){
		this.weight = weight;
		this.level = 0;
	}
	
	public ItemBase(int weight, int level){
		this.weight = weight;
		this.level = level;
	}
	
	public abstract ItemStack getLootItem(Random rand, int level);

	@Override
	public int getWeight() {
		return weight;
	}
	
	@Override
	public ItemStack get(Random rand) {
		return getLootItem(rand, level);
	}
}
