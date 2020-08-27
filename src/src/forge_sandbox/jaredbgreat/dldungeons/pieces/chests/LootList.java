package forge_sandbox.jaredbgreat.dldungeons.pieces.chests;


/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */		



//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
import static forge_sandbox.jaredbgreat.dldungeons.pieces.chests.LootItem.*;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Material;


/**
 * A list of loot items for a particular type and level.  This class also
 * contains static LootList members to statically hold all the loot used by
 * the mod.
 * 
 * @author Jared Blackburn
 *
 */
public class LootList extends ArrayList<LootItem>{
	ArrayList<LootItem> dummy = new ArrayList<LootItem>();
	
	
	/**
	 * Add the item, converting it to a LootItem.
	 * 
	 * @param item
	 * @param min
	 * @param max
	 * @param prob
	 */
	public void add(Material item, int min, int max, int prob) {
		add(new LootItem(item, min, max));
	}
	
	
	/**
	 * Returns a random item (as a LootItem) stored in the list.
	 * 
	 * @param random
	 * @return
	 */
	public LootItem getLoot(Random random) {
		LootItem out;
		if(isEmpty()) return null;
		// Done with removal now to somewhat increase randomness
		if(dummy.isEmpty() || random.nextInt(size()) > dummy.size()) {
			dummy.clear();
			dummy.addAll(this);
		}
		int which = random.nextInt(dummy.size());
		out = dummy.get(which);
		dummy.remove(which);
		return out;
	}
	
	

	
	
	
}
