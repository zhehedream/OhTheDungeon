package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentAnkh extends SegmentBase{

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord pos) {
		Coord start;
		Coord end;
		Coord cursor;
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getSecondary().getStair();
		DyeColor color = DyeColor.get(rand);
		MetaBlock glass = ColorBlock.get(ColorBlock.GLASS, color);
		MetaBlock back = ColorBlock.get(ColorBlock.CLAY, color);
		MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
		
		Cardinal[] orth = Cardinal.orthogonal(dir);
		
		start = new Coord(pos);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		
		RectSolid.fill(editor, rand, start, end, air);
		
		
		for(Cardinal o : orth){
			
			cursor = new Coord(pos);
			cursor.add(dir, 2);
			cursor.add(o);
			stair.setOrientation(Cardinal.reverse(o), false).set(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), false).set(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
		}
		
		start = new Coord(pos);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, glass);
		start.add(dir);
		end.add(dir);
		RectSolid.fill(editor, rand, start, end, back);
		
		cursor = new Coord(pos);
		cursor.add(dir, 3);
		cursor.add(Cardinal.DOWN);
		glowstone.set(editor, cursor);
		cursor.add(Cardinal.UP, 4);
		glowstone.set(editor, cursor);
	}

}
