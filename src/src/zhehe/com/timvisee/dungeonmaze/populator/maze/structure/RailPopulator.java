package zhehe.com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class RailPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
    private static final int ROOM_ITERATIONS = 3;
	private static final float ROOM_ITERATIONS_CHANCE = .08f;
    private static final int ROOM_ITERATIONS_MAX = 2;
    
    /** Populator constants. */
    private static final float MINECART_CHANCE = .01f;
	private static final float BROKEN_RAIL_CHANCE = .2f;

	private static final BlockFace[] RAIL_DIRECTIONS = new BlockFace[] {
			BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

    // TODO: Implement this feature!
    private static final double RAIL_CHANCE_ADDITION_EACH_LEVEL = -0.333; /* to 6 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World world = args.getWorld();
		Chunk chunk = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getRoomChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getRoomChunkZ();
		
		// Count the amount of placed rails
		int rails = 0;
				
		// Iterate
		for(int i = 0; i < ROOM_ITERATIONS; i++) {
			
			if(rails <= ROOM_ITERATIONS_MAX) {
				
				if (rand.nextInt(100) < ROOM_ITERATIONS_CHANCE +(RAIL_CHANCE_ADDITION_EACH_LEVEL *(y-30)/6)) {
					int startX = x + rand.nextInt(6) + 1;
					int startZ = z + rand.nextInt(6) + 1;

					BlockFace dir1 = RAIL_DIRECTIONS[rand.nextInt(RAIL_DIRECTIONS.length)];
					BlockFace dir2 = RAIL_DIRECTIONS[rand.nextInt(RAIL_DIRECTIONS.length)];

					int x2 = startX;
					int z2 = startZ;
					while(0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
						if(rand.nextFloat() > BROKEN_RAIL_CHANCE) {
							chunk.getBlock(x2, yFloor + 1, z2).setType(Material.RAIL);
							if(rand.nextFloat() < MINECART_CHANCE)
								spawnMinecart(world, (chunk.getX() * 16) + x + x2, yFloor + 1, (chunk.getZ() * 16) + z + z2);
						}

						x2 += dir1.getModX();
						z2 += dir1.getModZ();
					}

					if(dir1 != dir2) {
						x2 = startX;
						z2 = startZ;
						while (0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
							if(rand.nextFloat() > BROKEN_RAIL_CHANCE) {
								chunk.getBlock(x2, yFloor + 1, z2).setType(Material.RAIL);
								if(rand.nextFloat() < MINECART_CHANCE)
									spawnMinecart(world, (chunk.getX() * 16) + x + x2, yFloor + 1, (chunk.getZ() * 16) + z + z2);
							}

							x2 += dir2.getModX();
							z2 += dir2.getModZ();
						}
					}

					rails++;
				}
			}
		}
	}
	
	public Minecart spawnMinecart(World world, int x, int y, int z) {
		return spawnMinecart(new Location(world, x, y, z));
	}
	
	public Minecart spawnMinecart(Location location) {
    	return spawnMinecart(location.getWorld(), location);
    }
	
    public Minecart spawnMinecart(World world, Location location) {
    	return world.spawn(location, Minecart.class);
    }
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
    }

    @Override
    public int getRoomIterationsMax() {
        return ROOM_ITERATIONS_MAX;
    }
}
