package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonLinker extends DungeonBase{

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory floor = theme.getPrimary().getFloor();
		MetaBlock bars = BlockType.get(BlockType.IRON_BAR);

		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, -1, -4));
		end.add(new Coord(4, 9, 4));
		RectHollow.fill(editor, rand, start, end, wall, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, 9, -4));
		end.add(new Coord(4, 9, 4));
		RectSolid.fill(editor, rand, start, end, wall, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, -1, -4));
		end.add(new Coord(4, -1, 4));
		RectSolid.fill(editor, rand, start, end, floor);
		
		for(Cardinal dir : Cardinal.directions){
			
			start = new Coord(origin);
			start.add(dir, 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 8);
			start.add(Cardinal.DOWN);
			start.add(Cardinal.left(dir), 4);
			end.add(Cardinal.right(dir), 4);
			RectSolid.fill(editor, rand, start, end, bars, true, false);
			
			start = new Coord(origin);
			end = new Coord(origin);
			start.add(dir, 3);
			start.add(Cardinal.left(dir), 3);
			end.add(dir, 4);
			end.add(Cardinal.left(dir), 4);
			end.add(Cardinal.UP, 8);
			RectSolid.fill(editor, rand, start, end, pillar);
		}
		

		
		
		return true;
	}

	@Override
	public int getSize() {
		return 6;
	}

}
