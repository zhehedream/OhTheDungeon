package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;

public class ThemeSnow extends ThemeBase{

	public ThemeSnow(){
	
		MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);
		MetaBlock mossy = BlockType.get(BlockType.STONE_BRICK_MOSSY);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 10);
		walls.addBlock(cracked, 1);
		walls.addBlock(mossy, 1);
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock pillar = Log.getLog(Wood.SPRUCE);
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = BlockType.get(BlockType.SNOW);
		MetaStair SegmentStair = new MetaStair(StairType.BRICK);
		MetaBlock pillar2 = BlockType.get(BlockType.BRICK);
		
		this.secondary =  new BlockSet(SegmentWall, SegmentStair, pillar2);
	}
}
