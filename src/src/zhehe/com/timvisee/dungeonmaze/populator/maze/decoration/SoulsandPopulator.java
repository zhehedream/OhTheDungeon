package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class SoulsandPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 15;
	private static final float ROOM_ITERATIONS_CHANCE = .2f;

    // TODO: Implement this!
	public static final double CHANCE_ADDITION_EACH_LEVEL = -1.667; /* to 10 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int z = args.getRoomChunkZ();
        final Block b = chunk.getBlock(x + rand.nextInt(8), rand.nextInt(2) + y, z + rand.nextInt(8));

        if (b.getType() == Material.COBBLESTONE)
            b.setType(Material.SOUL_SAND);
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
