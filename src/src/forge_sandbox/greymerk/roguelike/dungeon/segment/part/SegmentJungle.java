package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Leaves;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentJungle extends SegmentBase {


	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, Coord origin) {
		
		IStair stair = theme.getSecondary().getStair();
		
		MetaBlock leaves = Leaves.get(Wood.JUNGLE, false);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.orthogonal(wallDirection);
		start = new Coord(origin);
		start.add(wallDirection, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 1);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		start.add(Cardinal.DOWN, 1);
		end.add(Cardinal.DOWN, 2);
		
		if(rand.nextInt(5) == 0){
			RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.WATER_FLOWING));
		} else {
			RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.GRASS));
			start.add(Cardinal.UP, 1);
			end.add(Cardinal.UP, 1);
			if(rand.nextBoolean()) RectSolid.fill(editor, rand, start, end, leaves);
		}
		
		for(Cardinal d : orth){
			cursor = new Coord(origin);
			cursor.add(wallDirection, 2);
			cursor.add(d, 1);
			cursor.add(Cardinal.UP, 1);
			stair.setOrientation(Cardinal.reverse(d), true);
			stair.set(editor, cursor);
		}

	}
}
