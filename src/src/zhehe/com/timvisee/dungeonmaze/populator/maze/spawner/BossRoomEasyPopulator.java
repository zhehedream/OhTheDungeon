package zhehe.com.timvisee.dungeonmaze.populator.maze.spawner;

//import zhehe.com.timvisee.dungeonmaze.Config;
import java.util.Random;

import zhehe.com.timvisee.dungeonmaze.util.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;


import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import zhehe.com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import otd.Main;
import shadow_lib.api.SpawnerDecryAPI;
import zhehe.com.timvisee.dungeonmaze.util.SpawnerUtils;

public class BossRoomEasyPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .004f;
        
        public boolean const_room = true;
        @Override
        public boolean getConstRoom() {
            return const_room;
        }


    /** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 10; // Chunks

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World world = args.getWorld();
		Chunk chunk = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getRoomChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getRoomChunkZ();
		
		// Make sure the distance between the spawn and this chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Register the current room as constant room
        //DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Create the floor
        for(int x2 = x; x2 < x + 7; x2 += 1)
            for(int z2 = z; z2 < z + 7; z2 += 1)
                chunk.getBlock(x2, yFloor, z2).setType(Material.MOSSY_COBBLESTONE);

        // Create the spawners
        if(SpawnerUtils.isZombieAllowed(world)) {
            Block spawnerBlock = chunk.getBlock(x + 1, yFloor + 1, z + 1);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.ZOMBIE);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }
        if(SpawnerUtils.isPigZombieAllowed(world)) {
            Block spawnerBlock = chunk.getBlock(x + 3, yFloor + 1, z + 3);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            if(otd.Main.version == otd.MultiVersion.Version.V1_16_R1
                    || otd.Main.version == otd.MultiVersion.Version.V1_16_R2) {
                spawner.setSpawnedType(EntityType.valueOf("ZOMBIFIED_PIGLIN"));
            } else {
                spawner.setSpawnedType(EntityType.valueOf("PIG_ZOMBIE"));
            }
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }
        if(SpawnerUtils.isSpiderAllowed(world)) {
            Block spawnerBlock = chunk.getBlock(x + 5, yFloor + 1, z + 5);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.SPIDER);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        // Coal ores
        chunk.getBlock(x + 1, yFloor + 1, z + 5).setType(Material.COAL_ORE);
        chunk.getBlock(x + 5, yFloor + 1, z + 1).setType(Material.COAL_ORE);
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