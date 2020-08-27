package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class CobblestonePopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .2f;

    /** Populator constants. */
	private static final int CHANCE_CORNER = 75;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getRoomChunkX();
        final int z = args.getRoomChunkZ();
        final int webX = x + rand.nextInt(6) + 1;
        final int webY = args.getFloorY();
        final int webCeilingY = args.getCeilingY();
        final int webZ = z + rand.nextInt(6) + 1;

        if (rand.nextInt(100) < CHANCE_CORNER)
            if(chunk.getBlock(x + (rand.nextInt(2)*5), webCeilingY, z + (rand.nextInt(2)*5)).getType() == Material.AIR)
                chunk.getBlock(x + (rand.nextInt(2)*5), webCeilingY, z + (rand.nextInt(2)*5)).setType(Material.COBWEB);

        else
            if(!(chunk.getBlock(webX, webY - 1, webZ).getType() == Material.AIR))
                if(chunk.getBlock(webX, webY, webZ).getType() == Material.AIR)
                    chunk.getBlock(webX, webY, webZ).setType(Material.COBBLESTONE);
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