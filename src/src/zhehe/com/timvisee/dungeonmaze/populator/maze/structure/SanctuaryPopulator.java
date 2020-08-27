package zhehe.com.timvisee.dungeonmaze.populator.maze.structure;


import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

public class SanctuaryPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 1;
	private static final float ROOM_CHANCE = .003f;
        
        public boolean const_room = true;
        @Override
        public boolean getConstRoom() {
            return const_room;
        }

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final int x = args.getRoomChunkX();
		final int yFloor = args.getFloorY();
		final int z = args.getRoomChunkZ();
			
//        // Register the current room as constant room
//        //DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, 30, z);

        for (int x2=x; x2 < x+8; x2+=1)
            for (int z2=z; z2 < z+8; z2+=1)
                chunk.getBlock(x2, yFloor, z2).setType(Material.OBSIDIAN);

        // Outline altar right
        chunk.getBlock(x + 2, yFloor + 1, z + 2).setType(Material.GOLD_BLOCK);
        chunk.getBlock(x + 3, yFloor + 1, z + 2).setType(Material.NETHERRACK);
        chunk.getBlock(x + 4, yFloor + 1, z + 2).setType(Material.NETHERRACK);
        chunk.getBlock(x + 5, yFloor + 1, z + 2).setType(Material.GOLD_BLOCK);

        // Center altar
        chunk.getBlock(x + 2, yFloor + 1, z + 3).setType(Material.NETHERRACK);
        chunk.getBlock(x + 3, yFloor + 1, z + 3).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 4, yFloor + 1, z + 3).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 5, yFloor + 1, z + 3).setType(Material.NETHERRACK);

        // Outline altar left
        chunk.getBlock(x + 2, yFloor + 1, z + 4).setType(Material.GOLD_BLOCK);
        chunk.getBlock(x + 3, yFloor + 1, z + 4).setType(Material.NETHERRACK);
        chunk.getBlock(x + 4, yFloor + 1, z + 4).setType(Material.NETHERRACK);
        chunk.getBlock(x + 5, yFloor + 1, z + 4).setType(Material.GOLD_BLOCK);

        // Torches
        chunk.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.TORCH);
        chunk.getBlock(x + 5, yFloor + 2, z + 2).setType(Material.TORCH);
        chunk.getBlock(x + 2, yFloor + 2, z + 4).setType(Material.TORCH);
        chunk.getBlock(x + 5, yFloor + 2, z + 4).setType(Material.TORCH);
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