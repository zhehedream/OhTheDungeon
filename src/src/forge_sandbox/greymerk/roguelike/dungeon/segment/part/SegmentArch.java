package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;

import java.util.Random;

public class SegmentArch extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
			
		IStair stair = theme.getSecondary().getStair(); 
		stair.setOrientation(Cardinal.reverse(dir), true);
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		Coord cursor = new Coord(origin);
		cursor.add(dir, 2);
		air.set(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		air.set(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		stair.set(editor, cursor);
		
		for(Cardinal orth : Cardinal.orthogonal(dir)){
			cursor = new Coord(origin);
			cursor.add(orth, 1);
			cursor.add(dir, 2);
			theme.getSecondary().getPillar().set(editor, rand, cursor);
			cursor.add(Cardinal.UP, 1);
			theme.getSecondary().getPillar().set(editor, rand, cursor);
			cursor.add(Cardinal.UP, 1);
			theme.getPrimary().getWall().set(editor, rand, cursor);
			cursor.add(Cardinal.reverse(dir), 1);
			stair.set(editor, cursor);			
		}
	}
}
