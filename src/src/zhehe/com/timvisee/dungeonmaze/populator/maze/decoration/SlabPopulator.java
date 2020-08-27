package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

public class SlabPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 14;
	private static final float ROOM_ITERATIONS_CHANCE = .6f;
    private static final int ROOM_ITERATIONS_MAX = 12;
    
    private static final BlockData STEP3 = Bukkit.createBlockData("minecraft:cobblestone_slab[type=bottom]");
    private static final BlockData STEP11 = Bukkit.createBlockData("minecraft:cobblestone_slab[type=top]");
    
    /** Populator constants. */
    private static final float CEILING_CHANCE = .5f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getRoomChunkX();
        final int z = args.getRoomChunkZ();
        final int slabX = x + rand.nextInt(6) + 1;
        int slabY = args.getFloorY() + 1;
        final int slabZ = z + rand.nextInt(6) + 1;

        // Determine whether the slab should be on the ceiling
        final boolean ceiling = rand.nextFloat() >= CEILING_CHANCE;

        // Set the slab coordinate if it should be on the ceiling
        if(ceiling)
            slabY = args.getCeilingY() - 1;

        // Get the slab blocks
        Block slabBlock = chunk.getBlock(slabX, slabY, slabZ);
        Block baseBlock = chunk.getBlock(slabX, slabY - 1, slabZ);
        if(ceiling)
            baseBlock = chunk.getBlock(slabX, slabY + 1, slabZ);

        // Make sure the slab could be placed on the specified position
        if(baseBlock.getType() != Material.AIR && slabBlock.getType() == Material.AIR) {
            // Set the material type to a slab
            // Set the proper data value
            if(!ceiling)
                slabBlock.setBlockData(STEP3);
            else
                slabBlock.setBlockData(STEP11);
        }
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