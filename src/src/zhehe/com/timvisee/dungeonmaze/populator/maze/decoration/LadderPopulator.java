package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

public class LadderPopulator extends MazeRoomBlockPopulator {
    
    private static final BlockData LADDER0 = Bukkit.createBlockData(Material.LADDER);
    private static final BlockData LADDER2 = Bukkit.createBlockData("minecraft:ladder[facing=north]");
    private static final BlockData LADDER3 = Bukkit.createBlockData("minecraft:ladder[facing=south]");
    private static final BlockData LADDER4 = Bukkit.createBlockData("minecraft:ladder[facing=west]");
    private static final BlockData LADDER5 = Bukkit.createBlockData("minecraft:ladder[facing=east]");

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .05f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
        final int startX;
        final int startY = args.getFloorY() + 1;
        final int startZ;
			
        BlockData ladderData = LADDER0;
        switch (rand.nextInt(2)) {
        case 0:
            int r = rand.nextInt(2);
            startX = x + 1 + (r * 5);
            startZ = z + rand.nextInt(2) * 7;
            if(r == 0)
                ladderData = LADDER5;
            else
                ladderData = LADDER4;
            break;

        case 1:
            int r2 = rand.nextInt(2);
            startX = x + rand.nextInt(2) * 7;
            startZ = z + 1 + (r2*5);
            if(r2 == 0)
                ladderData = LADDER3;
            else
                ladderData = LADDER2;
            break;

        default:
            startX = x + 1 + (rand.nextInt(2) * 5);
            startZ = z + rand.nextInt(2) * 7;
        }

        // Make sure there's no wall or anything else
        if(chunk.getBlock(startX, startY, startZ).getType() == Material.AIR) {
            for (int ladderY=startY; ladderY <= startY + 8; ladderY++) {
                chunk.getBlock(startX, ladderY, startZ).setBlockData(ladderData);
                if(ladderData == LADDER2) {
                    Block b = chunk.getBlock(startX, ladderY, startZ).getRelative(BlockFace.SOUTH);
                    if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
                } else if(ladderData == LADDER3) {
                    Block b = chunk.getBlock(startX, ladderY, startZ).getRelative(BlockFace.NORTH);
                    if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
                } else if(ladderData == LADDER4) {
                    Block b = chunk.getBlock(startX, ladderY, startZ).getRelative(BlockFace.EAST);
                    if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
                } else if(ladderData == LADDER5) {
                    Block b = chunk.getBlock(startX, ladderY, startZ).getRelative(BlockFace.WEST);
                    if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
                }
            }
        }
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