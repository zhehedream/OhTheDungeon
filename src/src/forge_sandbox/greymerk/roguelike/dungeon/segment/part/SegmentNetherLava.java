package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentNetherLava extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		IStair step = theme.getSecondary().getStair();
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock lava = BlockType.get(BlockType.LAVA_FLOWING);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		air.set(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		air.set(editor, cursor);
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		boolean isAir = editor.isAirBlock(cursor);
		boolean isLava = true;
		IBlockFactory wall = theme.getSecondary().getWall();
		
		for(Cardinal orth : Cardinal.orthogonal(dir)){
			start = new Coord(origin);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth, 1);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.DOWN, 1);
			if(isLava && !isAir){
				RectSolid.fill(editor, rand, start, end, air);
				lava.set(editor, start);
				start.add(Cardinal.reverse(orth), 1);
				lava.set(editor, start);
			}
			
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			
			step.setOrientation(Cardinal.reverse(orth), false);
			cursor.add(orth, 1);
			step.set(editor, cursor);
			
			step.setOrientation(Cardinal.reverse(orth), true);
			cursor.add(Cardinal.UP, 1);
			step.set(editor, cursor);
			
			cursor.add(Cardinal.UP, 1);
			wall.set(editor, rand, cursor);
			cursor.add(Cardinal.reverse(orth), 1);
			wall.set(editor, rand, cursor);
		}
		
	}

}
