package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

public class VinePopulator extends MazeRoomBlockPopulator {

	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;

	private static final int CHANCE_VINE = 30;
	private static final double CHANCE_VINE_ADDITION_EACH_LEVEL = -2.5; /* to 15 */
	private static final int ITERATIONS = 5;
	private static final int CHANCE_CEILING_VINE = 5;
	private static final int ITERATIONS_CEILING_VINE = 5;
        
        private static final BlockData VINE0 = Bukkit.createBlockData("minecraft:vine[east=false,south=false,north=false,west=false,up=false]");
        private static final BlockData VINE1 = Bukkit.createBlockData("minecraft:vine[east=false,south=true,north=false,west=false,up=false]");
        private static final BlockData VINE2 = Bukkit.createBlockData("minecraft:vine[east=false,south=false,north=false,west=true,up=false]");
        private static final BlockData VINE4 = Bukkit.createBlockData("minecraft:vine[east=false,south=false,north=true,west=false,up=false]");
        private static final BlockData VINE8 = Bukkit.createBlockData("minecraft:vine[east=true,south=false,north=false,west=false,up=false]");

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getRoomChunkX();
        final int y = args.getChunkY();
        final int z = args.getRoomChunkZ();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			if (rand.nextInt(100) < CHANCE_VINE +(CHANCE_VINE_ADDITION_EACH_LEVEL *(y-30)/6)) {
				
				int vineX;
				int vineY;
				int vineZ;
				
				switch(rand.nextInt(4)) {
				case 0:
					vineX = 0;
					vineY = rand.nextInt(4) + 2;
					vineZ = rand.nextInt(6) + 1;
					
					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						chunk.getBlock(x + vineX + 1, y + vineY, z + vineZ).setBlockData(VINE2);
					}
					
					break;
				case 1:
					vineX = 7;
					vineY = rand.nextInt(3) + 3;
					vineZ = rand.nextInt(6) + 1;
					
					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						chunk.getBlock(x + vineX - 1, y + vineY, z + vineZ).setBlockData(VINE8);
					}
					
					break;
				case 2:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 0;
					
					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						chunk.getBlock(x + vineX, y + vineY, z + vineZ + 1).setBlockData(VINE4);
					}
					
					break;
				case 3:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 7;
					
					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						chunk.getBlock(x + vineX, y + vineY, z + vineZ - 1).setBlockData(VINE1);
					}
					
					break;
				default:
				}	
			}
		}
		
		// Iterate
		for(int i = 0; i < ITERATIONS_CEILING_VINE; i++) {
			if (rand.nextInt(100) < CHANCE_CEILING_VINE) {
				
				int vineX = rand.nextInt(6) + 1;
				int vineY = args.getCeilingY() - 1;
				int vineZ = rand.nextInt(6) + 1;
				
				chunk.getBlock(x + vineX, vineY, z + vineZ).setBlockData(VINE0);
			}
		}
	}

    @Override
    public float getRoomChance() {
        // TODO: Improve this!
        return 1.0f;
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