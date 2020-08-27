package forge_sandbox.jaredbgreat.dldungeons.pieces.entrances;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */	


/**
 * The base class for all that build entrances.
 */
import forge_sandbox.jaredbgreat.dldungeons.planner.Dungeon;
import org.bukkit.Material;
import org.bukkit.World;
//import net.minecraft.block.Block;
//import net.minecraft.world.World;

public abstract class AbstractEntrance {
	
	protected static final Material ladder 
			= Material.LADDER;
	protected static final Material stairSlab 
			= Material.STONE_SLAB;	
	
	int x, z;
	
	/**
	 * Set an entrance to be built at the given coordinates.
	 * 
	 * @param x
	 * @param z
	 */
	public AbstractEntrance(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	
	/**
	 * Build the entrance to the given dungeon in the given world.
	 * 
	 * @param dungeon
	 * @param world
	 */
	public abstract void build(Dungeon dungeon, World world);
}
