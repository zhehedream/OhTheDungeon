package zhehe.com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import zhehe.com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;

public class FlowerPopulator extends SurfaceBlockPopulator {
    
    private static final Material FLOWERS[] = {
        Material.POPPY,
        Material.BLUE_ORCHID,
        Material.ALLIUM,
        Material.AZURE_BLUET,
        Material.RED_TULIP,
        Material.ORANGE_TULIP,
        Material.WHITE_TULIP,
        Material.PINK_TULIP,
        Material.OXEYE_DAISY,
        Material.LILY_OF_THE_VALLEY,
        Material.CORNFLOWER,
        Material.SWEET_BERRY_BUSH,
    };

    /** General populator constants. */
    private static final int CHUNK_ITERATIONS = 10;
    private static final float CHUNK_ITERATIONS_CHANCE = .15f;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
        final int xFlower = rand.nextInt(16);
        final int zFlower = rand.nextInt(16);

        // Get the surface level at the location of the flower
        final int ySurface = args.getSurfaceLevel(xFlower, zFlower);

        // Make sure the surface block is grass
        if(chunk.getBlock(xFlower, ySurface, zFlower).getType() == Material.GRASS_BLOCK) {
            final int flowerY = ySurface + 1;

            // Spawn the flower
            if (rand.nextInt(2) == 0)
                chunk.getBlock(xFlower, flowerY, zFlower).setType(Material.DANDELION);

            else {
                chunk.getBlock(xFlower, flowerY, zFlower).setType(FLOWERS[rand.nextInt(FLOWERS.length)]);
            }
        }
	}
	
	/**
	 * Get a random flower type
	 * @param rand Random instance
	 * @return Random flower type ID
	 */
	public byte getRandomFlowerType(Random rand) {
		return (byte) (rand.nextInt(9));
	}

    @Override
    public int getChunkIterations() {
        return CHUNK_ITERATIONS;
    }

    @Override
    public float getChunkIterationsChance() {
        return CHUNK_ITERATIONS_CHANCE;
    }
}