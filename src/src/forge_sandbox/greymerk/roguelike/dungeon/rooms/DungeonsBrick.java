package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;

public class DungeonsBrick extends DungeonBase {

	
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory blocks = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// fill air inside
		RectSolid.fill(editor, rand, new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3), air);
		RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1), air);
		
		// shell
		RectHollow.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4), blocks, false, true);

		RectSolid.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4), theme.getPrimary().getFloor(), false, true);
		
		Coord start;
		Coord end;
		Coord cursor;

		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 5);
		air.set(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		blocks.set(editor, rand, cursor);
		
		// Chests
		List<Coord> space = new ArrayList<Coord>();
		
		for(Cardinal dir : Cardinal.directions){
			
			// top
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(Cardinal.UP, 5);
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, rand, cursor, false, true);
			cursor.add(Cardinal.left(dir), 1);
			blocks.set(editor, rand, cursor, false, true);

			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(Cardinal.UP, 4);
			air.set(editor, cursor);
			cursor.add(Cardinal.UP, 1);
			blocks.set(editor, rand, cursor, false, true);
			
			// pillar
			cursor = new Coord(x, y, z);
			cursor.add(dir, 3);
			cursor.add(Cardinal.left(dir), 3);
			start = new Coord(cursor);
			cursor.add(Cardinal.UP, 2);
			end = new Coord(cursor);
			RectSolid.fill(editor, rand, start, end, pillar, true, true);
			cursor.add(Cardinal.UP, 1);
			blocks.set(editor, rand, cursor);
			
			// pillar stairs
			for(Cardinal orth : Cardinal.orthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				cursor.add(Cardinal.UP, 3);
				stair.setOrientation(Cardinal.reverse(orth), true);
				stair.set(editor, rand, cursor);
			}

			// layer above pillars
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(Cardinal.left(dir), 2);
			cursor.add(Cardinal.UP, 4);
			blocks.set(editor, rand, cursor, false, true);
			
			for(Cardinal orth : Cardinal.orthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 4);
				cursor.add(dir, 2);
				cursor.add(orth, 1);
				stair.setOrientation(Cardinal.reverse(orth), true);
				stair.set(editor, rand, cursor, false, true);
			}
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(Cardinal.left(dir), 1);
			cursor.add(Cardinal.UP, 5);
			blocks.set(editor, rand, cursor, false, true);
			
			for(Cardinal orth : Cardinal.orthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				space.add(cursor);
			}
		}

		List<Treasure> types = new ArrayList<Treasure>(Arrays.asList(Treasure.ARMOUR, Treasure.WEAPONS, Treasure.TOOLS));
		Treasure.createChests(editor, rand, 1, space, types, Dungeon.getLevel(origin.getY()));
		Spawner.generate(editor, rand, settings, new Coord(x, y, z));
		return true;
	}
	
	public int getSize(){
		return 6;
	}
}
