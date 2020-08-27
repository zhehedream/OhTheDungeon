package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.Random;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.treasure.ChestPlacementException;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BrewingStand;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Crops;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.FlowerPot;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Slab;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.redstone.Torch;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import zhehe.util.config.WorldConfig;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public class DungeonLab extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		IBlockFactory blocks = theme.getPrimary().getWall();
		
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		// Air
		air.fill(editor, rand, new RectSolid(new Coord(x - 7, y, z - 7), new Coord(x + 7, y + 3, z + 7)));

		IBlockFactory roof = theme.getSecondary().getWall();
		// Wood upper Roof
		RectSolid.fill(editor, rand, new Coord(x - 6, y + 5, z - 6), new Coord(x + 6, y + 5, z + 6), roof);
		RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1), air);
		RectSolid.fill(editor, rand, new Coord(x - 5, y + 4, z - 1), new Coord(x - 3, y + 4, z + 1), air);
		RectSolid.fill(editor, rand, new Coord(x + 3, y + 4, z - 1), new Coord(x + 5, y + 4, z + 1), air);
		RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 5), new Coord(x + 1, y + 4, z - 3), air);
		RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z + 3), new Coord(x + 1, y + 4, z + 5), air);
		
		// shell
		RectHollow.fill(editor, rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 4, z + 8), blocks, false, true);
		RectSolid.fill(editor, rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y - 1, z + 8), theme.getPrimary().getFloor(), false, true);
		
		
		// corner rooms
		southWest(editor, rand, settings, theme, x - 7, y, z + 2);
		southEast(editor, rand, theme, x + 2, y, z + 2);
		northWest(editor, rand, theme, x - 7, y, z - 7);
		northEast(editor, rand, theme, x + 2, y, z - 7);
		
		// outer walls
		RectSolid.fill(editor, rand, new Coord(x - 8, y, z - 7), new Coord(x - 8, y + 3, z - 7), blocks);
		RectSolid.fill(editor, rand, new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7), blocks);
		RectSolid.fill(editor, rand, new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7), blocks);
		
		IBlockFactory backWalls = theme.getSecondary().getWall();
		
		// wall planks
		RectSolid.fill(editor, rand, new Coord(x - 8, y + 1, z - 6), new Coord(x - 8, y + 3, z - 3), backWalls);
		RectSolid.fill(editor, rand, new Coord(x - 8, y + 1, z + 3), new Coord(x - 8, y + 3, z + 6), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 8, y + 1, z - 6), new Coord(x + 8, y + 3, z - 3), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 8, y + 1, z + 3), new Coord(x + 8, y + 3, z + 6), backWalls);
		
		RectSolid.fill(editor, rand, new Coord(x - 6, y + 1, z - 8), new Coord(x - 3, y + 3, z - 8), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 3, y + 1, z - 8), new Coord(x + 6, y + 3, z - 8), backWalls);
		RectSolid.fill(editor, rand, new Coord(x - 6, y + 1, z + 8), new Coord(x - 3, y + 3, z + 8), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 3, y + 1, z + 8), new Coord(x + 6, y + 3, z + 8), backWalls);
		
		return false;
	}

	private static void corner(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock doubleSlab = Slab.get(Slab.STONE, false, true, true);
		MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
		MetaBlock cyan = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
		
		// pillars
		pillar(editor, rand, theme, x, y, z);
		pillar(editor, rand, theme, x + 5, y, z);
		pillar(editor, rand, theme, x, y, z + 5);
		pillar(editor, rand, theme, x + 5, y, z + 5);
		
		// tile floor
		RectSolid.fill(editor, rand, new Coord(x, y - 1, z), new Coord(x + 5, y - 1, z + 5), cyan);
		RectSolid.fill(editor, rand, new Coord(x + 1, y - 1, z + 2), new Coord(x + 4, y - 1, z + 3), doubleSlab);
		RectSolid.fill(editor, rand, new Coord(x + 2, y - 1, z + 1), new Coord(x + 3, y - 1, z + 4), doubleSlab);
		
		// ceiling dome
		RectSolid.fill(editor, rand, new Coord(x + 2, y + 4, z + 2), new Coord(x + 3, y + 8, z + 3), air);
		air.set(editor, new Coord(x + 3, y + 4, z + 1));
		air.set(editor, new Coord(x + 4, y + 4, z + 1));
		air.set(editor, new Coord(x + 3, y + 4, z + 4));
		air.set(editor, new Coord(x + 4, y + 4, z + 4));
		
		air.set(editor, new Coord(x + 1, y + 4, z + 3));
		air.set(editor, new Coord(x + 1, y + 4, z + 4));
		air.set(editor, new Coord(x + 4, y + 4, z + 3));
		air.set(editor, new Coord(x + 4, y + 4, z + 4));
		
		RectHollow.fill(editor, rand, new Coord(x + 1, y + 4, z + 1), new Coord(x + 4, y + 8, z + 4), cobble, false, true);
		RectSolid.fill(editor, rand, new Coord(x + 2, y + 8, z + 2), new Coord(x + 3, y + 8, z + 3), air);
	}
	
	
	private void southWest(IWorldEditor editor, Random rand, LevelSettings settings, ITheme theme, int x, int y, int z){
		
		corner(editor, rand, theme, x, y, z);
		
		IStair stair = theme.getSecondary().getStair();
		stair.setOrientation(Cardinal.NORTH, true);
		RectSolid.fill(editor, rand, new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5), stair);
		stair.setOrientation(Cardinal.EAST, true);
		RectSolid.fill(editor, rand, new Coord(x, y, z + 1), new Coord(x, y, z + 4), stair);
                
		boolean generous = false;
                String world_name = editor.getWorldName();
                if(WorldConfig.wc.dict.containsKey(world_name) && WorldConfig.wc.dict.get(world_name).roguelike.generous) generous = true;
		if(generous){
			Coord bs = new Coord(x + 1, y + 1, z + 5);
			BrewingStand.generate(editor, bs);
			BrewingStand.add(editor, bs, BrewingStand.FUEL, new ItemStack(Material.BLAZE_POWDER));
		}
		try {
			Treasure.generate(editor, rand, new Coord(x, y + 1, z + 4), Treasure.BREWING, Dungeon.getLevel(y));
		} catch (ChestPlacementException cpe){
			// do nothing
		}
	}
	
	// fountains
	private static void southEast(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = BlockType.get(BlockType.STONE_BRICK);
		IStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock slab = Slab.get(Slab.STONEBRICK, false, false, false);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		
		corner(editor, rand, theme, x, y, z);
		
		RectSolid.fill(editor, rand, new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5), stone);
		stair.setOrientation(Cardinal.WEST, false).set(editor, new Coord(x + 1, y + 1, z + 5));
		water.set(editor, new Coord(x + 2, y + 1, z + 5));
		slab.set(editor, new Coord(x + 2, y + 2, z + 5));
		stair.setOrientation(Cardinal.EAST, false).set(editor, new Coord(x + 3, y + 1, z + 5));
		
		RectSolid.fill(editor, rand, new Coord(x + 5, y, z + 1), new Coord(x + 5, y, z + 4), stone);
		stair.setOrientation(Cardinal.NORTH, false).set(editor, new Coord(x + 5, y + 1, z + 1));
		water.set(editor, new Coord(x + 5, y + 1, z + 2));
		slab.set(editor, new Coord(x + 5, y + 2, z + 2));
		stair.setOrientation(Cardinal.SOUTH, false).set(editor, new Coord(x + 5, y + 1, z + 3));
		
		RectSolid.fill(editor, rand, new Coord(x + 3, y, z + 3), new Coord(x + 4, y, z + 4), stone);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x + 3, y + 1, z + 3));
		
		stair.setOrientation(Cardinal.NORTH, false).set(editor, new Coord(x + 4, y, z + 1));
		stair.setOrientation(Cardinal.WEST, false).set(editor, new Coord(x + 3, y, z + 2));
		stair.setOrientation(Cardinal.NORTH, false).set(editor, new Coord(x + 2, y, z + 3));
		stair.setOrientation(Cardinal.WEST, false).set(editor, new Coord(x + 1, y, z + 4));
		
		
	}
	
	private static void northWest(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = BlockType.get(BlockType.STONE_BRICK);
		MetaBlock redstone = BlockType.get(BlockType.REDSTONE_BLOCK);
		MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP_LIT);
		MetaBlock farmland = BlockType.get(BlockType.FARMLAND);
		MetaBlock soul_sand = BlockType.get(BlockType.SOUL_SAND);
		
		corner(editor, rand, theme, x, y, z);
		
		stone.set(editor, new Coord(x + 1, y, z));
		FlowerPot.generate(editor, rand, new Coord(x + 1, y + 1, z));
		farmland.set(editor, new Coord(x + 2, y, z));
		Crops.get(Crops.CARROTS).set(editor, new Coord(x + 2, y + 1, z));
		farmland.set(editor, new Coord(x + 3, y, z));
		Crops.get(Crops.CARROTS).set(editor, new Coord(x + 3, y + 1, z));
		stone.set(editor, new Coord(x + 4, y, z));
		FlowerPot.generate(editor, rand, new Coord(x + 4, y + 1, z));
		
		stone.set(editor, new Coord(x, y, z + 1));
		FlowerPot.generate(editor, rand, new Coord(x, y + 1, z + 1));
		soul_sand.set(editor, new Coord(x, y, z + 2));
		Crops.get(Crops.NETHERWART).set(editor, new Coord(x, y + 1, z + 2));
		soul_sand.set(editor, new Coord(x, y, z + 3));
		Crops.get(Crops.NETHERWART).set(editor, new Coord(x, y + 1, z + 3));
		stone.set(editor, new Coord(x, y, z + 4));
		FlowerPot.generate(editor, rand, new Coord(x, y + 1, z + 4));
		
		stone.set(editor, new Coord(x + 1, y, z + 1));
		
		IStair stair = new MetaStair(StairType.STONEBRICK);
		stair.setOrientation(Cardinal.SOUTH, false).fill(editor, rand, new RectSolid(new Coord(x + 2, y, z + 1), new Coord(x + 4, y, z + 1)));
		stair.setOrientation(Cardinal.EAST, false).fill(editor, rand, new RectSolid(new Coord(x + 1, y, z + 2), new Coord(x + 1, y, z + 4)));
		
		redstone.set(editor, new Coord(x + 2, y - 1, z + 2));
		lamp.set(editor, new Coord(x + 3, y - 1, z + 2));
		lamp.set(editor, new Coord(x + 2, y - 1, z + 3));
		
		BlockType.get(BlockType.WATER_FLOWING).set(editor, new Coord(x, y, z));
	}
	
	private static void northEast(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = BlockType.get(BlockType.STONE_BRICK);
		MetaBlock redstone = BlockType.get(BlockType.REDSTONE_BLOCK);
		MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP_LIT);
		MetaBlock farmland = BlockType.get(BlockType.FARMLAND);
		
		corner(editor, rand, theme, x, y, z);
		
		stone.set(editor, new Coord(x + 1, y, z));
		FlowerPot.generate(editor, rand, new Coord(x + 1, y + 1, z));
		farmland.set(editor, new Coord(x + 2, y, z));
		Crops.get(Crops.MELON).set(editor, new Coord(x + 2, y + 1, z));
		farmland.set(editor, new Coord(x + 3, y, z));
		stone.set(editor, new Coord(x + 4, y, z));
		FlowerPot.generate(editor, rand, new Coord(x + 4, y + 1, z));
		
		stone.set(editor, new Coord(x + 5, y, z + 1));
		FlowerPot.generate(editor, rand, new Coord(x + 5, y + 1, z + 1));
		farmland.set(editor, new Coord(x + 5, y, z + 2));
		Crops.get(Crops.PUMPKIN).set(editor, new Coord(x + 5, y + 1, z + 2));
		farmland.set(editor, new Coord(x + 5, y, z + 3));
		stone.set(editor, new Coord(x + 5, y, z + 4));
		FlowerPot.generate(editor, rand, new Coord(x + 5, y + 1, z + 4));
		
		stone.set(editor, new Coord(x + 4, y, z + 1));
		
		IStair stair = new MetaStair(StairType.STONEBRICK);
		
		stair.setOrientation(Cardinal.SOUTH, false).fill(editor, rand, new RectSolid(new Coord(x + 1, y, z + 1), new Coord(x + 3, y, z + 1)));
		stair.setOrientation(Cardinal.WEST, false).fill(editor, rand, new RectSolid(new Coord(x + 4, y, z + 2), new Coord(x + 4, y, z + 4)));
		
		redstone.set(editor, new Coord(x + 3, y - 1, z + 2));
		lamp.set(editor, new Coord(x + 2, y - 1, z + 2));
		lamp.set(editor, new Coord(x + 3, y - 1, z + 3));
		
		BlockType.get(BlockType.WATER_FLOWING).set(editor, new Coord(x + 5, y, z));
	}
	
	private static void pillar(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		theme.getSecondary().getPillar().fill(editor, rand, new RectSolid(new Coord(x, y, z), new Coord(x, y + 2, z)));
		theme.getPrimary().getWall().set(editor, rand, new Coord(x, y + 3, z));
		IStair stair = theme.getSecondary().getStair();
		stair.setOrientation(Cardinal.EAST, true).set(editor, new Coord(x + 1, y + 3, z));
		stair.setOrientation(Cardinal.WEST, true).set(editor, new Coord(x - 1, y + 3, z));
		stair.setOrientation(Cardinal.SOUTH, true).set(editor, new Coord(x, y + 3, z + 1));
		stair.setOrientation(Cardinal.NORTH, true).set(editor, new Coord(x, y + 3, z - 1));
	
	}
	
	public int getSize(){
		return 9;
	}
	
}
