package zhehe.com.timvisee.dungeonmaze.populator.surface;

import zhehe.com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Random;

public abstract class SurfaceBlockPopulator extends ChunkBlockPopulator {
	
	@Override
    public void populateChunk(ChunkBlockPopulatorArgs args) {
        World w = args.getWorld();
        Random rand = args.getRandom();
        Chunk chunk = args.getSourceChunk();
			
		// Construct the DMMazePopulatorArgs to use the the populateMaze method
		SurfaceBlockPopulatorArgs surfaceArgs = new SurfaceBlockPopulatorArgs(w, rand, chunk);
		
		// Populate the maze
		populateSurface(surfaceArgs);
	}
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	public abstract void populateSurface(SurfaceBlockPopulatorArgs args);
}
