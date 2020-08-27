package zhehe.com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class PumpkinPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
    private static final int LAYER_MIN = 1;
    private static final int LAYER_MAX = 7;
    private static final float ROOM_CHANCE = .025f;
    private static final int ROOM_ITERATIONS = 7;
    private static final float ROOM_ITERATIONS_CHANCE = .5f;
    private static final int ROOM_ITERATIONS_MAX = 5;

    /** Populator constants. */
    private static final float JACK_O_LANTERN_CHANCE = .33f;

    @Override
    public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getRoomChunkX();
        final int z = args.getRoomChunkZ();
        final int xPumpkin = x + rand.nextInt(6) + 1;
        final int yPumpkin = args.getFloorY() + 1;
        final int zPumpkin = z + rand.nextInt(6) + 1;

        // Decide whether to place a pumpkin or jack o lantern
        final boolean illuminated = rand.nextFloat() < JACK_O_LANTERN_CHANCE;

        // Place the pumpkin if there's any place
        if(chunk.getBlock(xPumpkin, yPumpkin - 1, zPumpkin).getType() != Material.AIR) {
            Block slabBlock = chunk.getBlock(xPumpkin, yPumpkin, zPumpkin);
            if(slabBlock.getType() == Material.AIR) {
                if(!illuminated)
                    slabBlock.setType(Material.PUMPKIN);
                else
                    slabBlock.setType(Material.JACK_O_LANTERN);

                // Randomly rotate the pumpkin
//                slabBlock.setData((byte) rand.nextInt(4));//TODO
            }
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
    public float getRoomChance() {
        return ROOM_CHANCE;
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