package forge_sandbox.jaredbgreat.dldungeons;

import static forge_sandbox.jaredbgreat.dldungeons.builder.Builder.placeDungeon;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import zhehe.util.BiomeDictionary;
import zhehe.util.BiomeDictionary.Type;
import org.bukkit.World;
import zhehe.util.config.WorldConfig;

/**
 * The class responcible for determine where dungeons generate.  More 
 * specifically, it wil(IWorldGenerator) l determine if a chunk being generated should be 
 * the technical (not geometric) center of a dungeon.
 * 
 * @author Jared Blackburn
 *
 */
public class GenerationHandler {	
	private static int frequency;
	private static int factor = 6;
	private static int minXZ;
	private static Random mrand;
	private static HashSet<Integer> dimensions;
	
	
//	public GenerationHandler() {
//		GameRegistry.registerWorldGenerator(this, 100);
//	}
	
	
	/**
	 * Called when a new chunk generates to determine if a dungeon should be 
	 * built centered on that chunk.
	 * 
	 * First it will make sure dungeons are aloud, including all config setting 
	 * and world setting as well as the dimension and biome of the chunk.
	 * 
	 * If dungeons are allowed here then it will check if the chunk is the one 
	 * in its area to have a dungeon.  To do this the map is logically divided 
	 * into square areas the size of which depend on the frequency scale.  One 
	 * chunk in each area is the technical center around with a dungeon will 
	 * generate.  The chunk coordinates are divided by the area's width using 
	 * integer division to round so that all chunks in the same area are given
	 * the same two numbers.  These numbers are then used to create a random 
	 * seeds from which coordinates from 0 to the area width are generated. If
	 * the coords match the remainder of chunk coordinate divided by the width
	 * of the area this chunk will have a dungeon; since all chunks in the area
	 * will use the same random seed and thus have the same number one and only
	 * one of them will have a dungeon (assuming dungeons are allowed in the 
	 * worlds and in that chunk).
	 * 
	 * This allows dungeons to be placed randomly while ensuring a more consistent
	 * distribution than simply giving each a random probability to have one.
	 */
	public void generate(Random random, int chunkX, int chunkZ, World world) {
		// Prevent bad spawns
//		boolean blockedBiome = false;
//		Set<Type> types = BiomeDictionary.getTypes(world.getBiome(chunkX * 16, chunkZ * 16));
//		for(Type type : types) {			
////			blockedBiome = ConfigHandler.biomeExclusions.contains(type) || blockedBiome;
//                        if(WorldConfig.wc.dict.containsKey(world.getName())) {
//                            blockedBiome = WorldConfig.wc.dict.get(world.getName()).doomlike.biomeExclusions.contains(type) || blockedBiome;
//                        }
//		}
//		if(blockedBiome) return;
		if((Math.abs(chunkX - (world.getSpawnLocation().getX() / 16)) < minXZ) 
				|| (Math.abs(chunkZ - (world.getSpawnLocation().getZ() / 16)) < minXZ)) return;
		
		mrand = new Random(world.getSeed()
				+ (2027 * (long)(chunkX / factor)) 
				+ (1987 * (long)(chunkZ / factor)));
		int xrand = mrand.nextInt();
		int zrand = mrand.nextInt();
		int xuse = ((chunkX + xrand) % factor);
		int zuse = ((chunkZ + zrand) % factor);
		
		if((xuse == 0) && (zuse == 0)) {
			try {
				placeDungeon(random, chunkX, chunkZ, world);
			} catch (Throwable e) {
				System.err.println("[OTD] Danger!  Failed to finalize a dungeon after building!");
//				e.printStackTrace();
			}
		}
	}	
	
	
	/**
	 * Sets the frequency scale and converts into the 
	 * width (and height) of the areas for which dungeons
	 * are generated.
	 * 
	 * @param freqScale
	 */
	public static void setFrequency(int freqScale) {
		if((freqScale % 2) == 0) factor = 2;
		else factor = 3;
		System.out.println("freqScale = " + freqScale);
		factor = (1 << (freqScale / 2)) * factor;
		System.out.println("factor = " + factor);
	}
	
	
	/**
	 * Sets the minimum number of chunks from spawn a dungeon center must be.
	 * 
	 * @param value
	 */
	public static void setMinXZ(int value) {
		minXZ = value;		
	}
	
	
	/**
	 * Sets list of dimension id's to check for dungeons being allowed.
	 * 
	 * @param value
	 */
	public static void setDimensions(int[] value) {
		dimensions = new HashSet<Integer>();
		for(int i = 0; i < value.length; i++) {
			dimensions.add(Integer.valueOf(value[i]));
		}
	}
	
	
	/**
	 * Add a dimension id to the list of dimension to consider when 
	 * check if a dungeon is allowed.  (What this means can vary.)
	 * 
	 * @param value
	 */
	public static void addDimension(int value) {
		dimensions.add(Integer.valueOf(value));
	}
	
	
	/**
	 * Remove a dimension id from the list of those to consider when potentially 
	 * placing a dungeons.  (What this means can vary.)
	 * 
	 * @param value
	 */
	public static void subDimension(int value) {
		dimensions.remove(Integer.valueOf(value));
	}
}
