package forge_sandbox.jaredbgreat.dldungeons.pieces.entrances;


/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */	


import forge_sandbox.jaredbgreat.dldungeons.builder.DBlock;
import forge_sandbox.jaredbgreat.dldungeons.planner.Dungeon;
import static zhehe.util.Constant.*;
import org.bukkit.World;
//import net.minecraft.world.World;

public class SimpleEntrance extends AbstractEntrance {

	public SimpleEntrance(int x, int z) {
		super(x, z);
	}

	@Override
	public void build(Dungeon dungeon, World world) {
		int wx = x + (dungeon.map.chunkX * 16) - (dungeon.map.room.length / 2) + 8;
		int wz = z + (dungeon.map.chunkZ * 16) - (dungeon.map.room.length / 2) + 8;
		int bottom = dungeon.map.floorY[x][z];
		int top = world.getMaxHeight();
		while(!DBlock.isGroundBlock(world, wx, top, wz)) top--;
		top++;
		int side = dungeon.random.nextInt(4);
		switch (side) {
			case 0:
				for(int i = bottom; i <= top; i++) {
					DBlock.place(world, wx, i, wz, dungeon.wallBlock1);
					DBlock.placeBlock(world, wx + 1, i, wz, ladder5);
				}
				break;
			case 1:
				for(int i = bottom; i <= top; i++) {
					DBlock.place(world, wx, i, wz, dungeon.wallBlock1);
					DBlock.placeBlock(world, wx, i, wz + 1, ladder3);
				}
				break;
			case 2:
				for(int i = bottom; i <= top; i++) {
					DBlock.place(world, wx, i, wz, dungeon.wallBlock1);
					DBlock.placeBlock(world, wx - 1, i, wz, ladder4);
				}
				break;
			case 3:
				for(int i = bottom; i <= top; i++) {
					DBlock.place(world, wx, i, wz, dungeon.wallBlock1);
					DBlock.placeBlock(world, wx, i, wz - 1, ladder2);
				}
				break;
		}
		
	}

}
