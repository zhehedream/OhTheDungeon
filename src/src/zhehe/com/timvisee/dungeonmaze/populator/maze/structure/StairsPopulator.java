package zhehe.com.timvisee.dungeonmaze.populator.maze.structure;

import org.bukkit.Chunk;
import org.bukkit.Material;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

public class StairsPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .02f;
        private static final BlockData COBBLESTONE_STAIRS2 = 
                Bukkit.createBlockData("minecraft:cobblestone_stairs[half=bottom,shape=straight,facing=south]");
        private static final BlockData COBBLESTONE_STAIRS1 = 
                Bukkit.createBlockData("minecraft:cobblestone_stairs[half=bottom,shape=straight,facing=west]");

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        // Get various
		final Chunk chunk = args.getSourceChunk();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int z = args.getRoomChunkZ();

        // Make sure there's some air at the spot we want to place the stair
        if(chunk.getBlock(x, y - 1, z).getType() == Material.AIR)
            return;

        // Build the stairs
        chunk.getBlock(x + 5, yFloor + 1, z + 2).setBlockData(COBBLESTONE_STAIRS2);
        chunk.getBlock(x + 6, yFloor + 1, z + 2).setBlockData(COBBLESTONE_STAIRS2);
        chunk.getBlock(x + 5, yFloor + 1 + 1, z + 3).setBlockData(COBBLESTONE_STAIRS2);
        chunk.getBlock(x + 6, yFloor + 1 + 1, z + 3).setBlockData(COBBLESTONE_STAIRS2);
        chunk.getBlock(x + 5, yFloor + 1 + 2, z + 4).setBlockData(COBBLESTONE_STAIRS2);
        chunk.getBlock(x + 6, yFloor + 1 + 2, z + 4).setBlockData(COBBLESTONE_STAIRS2);
        chunk.getBlock(x + 5, yFloor + 1 + 2, z + 5).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 6, yFloor + 1 + 2, z + 5).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 5, yFloor + 1 + 2, z + 6).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 6, yFloor + 1 + 2, z + 6).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 4, yFloor + 1 + 3, z + 5).setBlockData(COBBLESTONE_STAIRS1);
        chunk.getBlock(x + 4, yFloor + 1 + 3, z + 6).setBlockData(COBBLESTONE_STAIRS1);
        chunk.getBlock(x + 3, yFloor + 1 + 4, z + 5).setBlockData(COBBLESTONE_STAIRS1);
        chunk.getBlock(x + 3, yFloor + 1 + 4, z + 6).setBlockData(COBBLESTONE_STAIRS1);
        chunk.getBlock(x + 2, yFloor + 1 + 5, z + 5).setBlockData(COBBLESTONE_STAIRS1);
        chunk.getBlock(x + 2, yFloor + 1 + 5, z + 6).setBlockData(COBBLESTONE_STAIRS1);

        // Remove blocks blocking the stairway
        chunk.getBlock(x + 3, yFloor + 1 + 5, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 3, yFloor + 1 + 5, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 4, yFloor + 1 + 5, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 4, yFloor + 1 + 5, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 5, yFloor + 1 + 5, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 5, yFloor + 1 + 5, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 2, yFloor + 1 + 6, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 2, yFloor + 1 + 6, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 3, yFloor + 1 + 6, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 3, yFloor + 1 + 6, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 4, yFloor + 1 + 6, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 4, yFloor + 1 + 6, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 5, yFloor + 1 + 6, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 5, yFloor + 1 + 6, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 2, yFloor + 1 + 7, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 2, yFloor + 1 + 7, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 3, yFloor + 1 + 7, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 3, yFloor + 1 + 7, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 4, yFloor + 1 + 7, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 4, yFloor + 1 + 7, z + 6).setType(Material.AIR);
        chunk.getBlock(x + 5, yFloor + 1 + 7, z + 5).setType(Material.AIR);
        chunk.getBlock(x + 5, yFloor + 1 + 7, z + 6).setType(Material.AIR);

        // Properly set the data values of the stair blocks
        // TODO: Use the stair block instance instead (because of deprecation)

        // Put some supports under the staircase
        chunk.getBlock(x + 5, yFloor + 1 + 1, z + 4).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 6, yFloor + 1 + 1, z + 4).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 5, yFloor + 1 + 1, z + 5).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 6, yFloor + 1 + 1, z + 5).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 5, yFloor + 1 + 1, z + 6).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 6, yFloor + 1 + 1, z + 6).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 4, yFloor + 1 + 2, z + 5).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 4, yFloor + 1 + 2, z + 6).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 3, yFloor + 1 + 3, z + 5).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 3, yFloor + 1 + 3, z + 6).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 2, yFloor + 1 + 4, z + 5).setType(Material.COBBLESTONE);
        chunk.getBlock(x + 2, yFloor + 1 + 4, z + 6).setType(Material.COBBLESTONE);
	}

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
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