package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class IronBarPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
    private static final int ROOM_ITERATIONS = 4;
	private static final float ROOM_ITERATIONS_CHANCE = .20f;

    /** Populator constants. */
	private static final float CHANCE_DOUBLE_HEIGHT = .66f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int z = args.getRoomChunkZ();
		final int floorOffset = args.getFloorOffset();

        // Define the position variables
        int blockX, blockY, blockZ;

        // Determine the y position of the gap
        blockY = y + rand.nextInt(4 - floorOffset) + 1 + floorOffset;

        // Define the x and z position of the broken wall
        if(rand.nextBoolean()) {
            blockX = x + (rand.nextBoolean() ? 0 : 7);
            blockZ = z + rand.nextInt(6) + 1;

        } else {
            blockX = z + rand.nextInt(6) + 1;
            blockZ = x + (rand.nextBoolean() ? 0 : 7);
        }

        // Specify the bars base block
        Block barsBase = chunk.getBlock(blockX, blockY, blockZ);
        if(barsBase.getType() == Material.COBBLESTONE || barsBase.getType() == Material.MOSSY_COBBLESTONE || barsBase.getType() == Material.STONE_BRICKS) {
            // Set the block type to the iron bars
            barsBase.setType(Material.IRON_BARS);

            // Check whether bars of two blocks height should be spawned
            if(rand.nextFloat() < CHANCE_DOUBLE_HEIGHT) {
                Block block2 = chunk.getBlock(blockX, blockY + 1, blockZ);
                block2.setType(Material.IRON_BARS);
            }
        }
	}

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
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
}
