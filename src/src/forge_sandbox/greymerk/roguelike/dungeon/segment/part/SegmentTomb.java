package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.treasure.ChestPlacementException;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;

public class SegmentTomb extends SegmentBase {
	
	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimary().getStair();
		
		Coord cursor = new Coord(origin);
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.orthogonal(dir);
		
		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, air);
		
		start.add(dir, 1);
		end.add(dir, 1);
		RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);

		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setOrientation(Cardinal.reverse(d), true);
			stair.set(editor, rand, c);
		}
		
		tomb(editor, rand, level.getSettings(), theme, dir, new Coord(origin));
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 3);
		BlockType.get(BlockType.QUARTZ).set(editor, cursor);
		
	}
	
	private static void tomb(IWorldEditor editor, Random rand, LevelSettings level, ITheme theme, Cardinal dir, Coord pos){
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.orthogonal(dir);
		start = new Coord(pos);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 3);
		end.add(dir, 3);
		List<Coord> box = new RectHollow(start, end).get();
		
		// make sure the box is solid wall
		for(Coord c : box){
			if(!editor.getMaterial(c).isSolid()) return;
		}
		
		RectHollow.fill(editor, rand, start, end, theme.getPrimary().getWall());
		if(!(rand.nextInt(3) == 0)) return;
		cursor = new Coord(pos);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 4);
		Spawner.generate(editor, rand, level, cursor, rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE);
		cursor.add(dir);
		try {
			Treasure.generate(editor, rand, cursor, rand.nextBoolean() ? Treasure.ARMOUR : Treasure.WEAPONS, Dungeon.getLevel(cursor.getY()));
		} catch (ChestPlacementException cpe) {
			// do nothing
		}
		
	}
}
