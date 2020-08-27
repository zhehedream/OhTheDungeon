package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class CoalOrePopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
    private static final int ROOM_ITERATIONS = 5;
	private static final float ROOM_ITERATIONS_CHANCE = .02f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getRoomChunkX();
        final int y = args.getChunkY();
        final int z = args.getRoomChunkZ();

        // Specify the coal ore block
        final Block coalOreBlock = chunk.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));

        // Change the block to coal if it's a cobblestone or mossy cobble stone block
        if(coalOreBlock.getType() == Material.COBBLESTONE || coalOreBlock.getType() == Material.MOSSY_COBBLESTONE)
            coalOreBlock.setType(Material.COAL_ORE);
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
	public int getMinimumLayer() {
		return LAYER_MIN;
	}

    @Override
    public int getMaximumLayer() {
        return LAYER_MAX;
    }
}