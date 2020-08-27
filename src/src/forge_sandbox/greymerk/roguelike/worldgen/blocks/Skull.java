package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Material;
import org.bukkit.block.data.Rotatable;
//import net.minecraft.block.BlockSkull;
//import net.minecraft.init.Blocks;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntitySkull;

public enum Skull {

	SKELETON, WITHER, ZOMBIE, STEVE, CREEPER;
	
	public static void set(IWorldEditor editor, Random rand, int x, int y, int z, Cardinal dir, Skull type){
		Material skull = Material.CREEPER_HEAD;
                if(null != type) switch (type) {
                    case SKELETON:
                        skull = Material.SKELETON_SKULL;
                        break;
                    case WITHER:
                        skull = Material.WITHER_SKELETON_SKULL;
                        break;
                    case ZOMBIE:
                        skull = Material.ZOMBIE_HEAD;
                        break;
                    case STEVE:
                        skull = Material.PLAYER_HEAD;
                        break;
                    default:
                        skull = Material.CREEPER_HEAD;
                        break;
                }
		MetaBlock skullBlock = new MetaBlock(skull);
                
                Rotatable state = (Rotatable) skullBlock.getState();
                state.setRotation(Cardinal.facing(Cardinal.EAST));
                skullBlock.setState(state);
                
                Coord pos = new Coord(x, y, z);
                skullBlock.set(editor, pos);
//		
//		// Makes the skull sit flush against the block below it.
//		skullBlock.withProperty(BlockSkull.FACING, Cardinal.facing(Cardinal.UP));
//		
//		Coord pos = new Coord(x, y, z);
//		
//		if(!skullBlock.set(editor, pos)) return;
//		
//		
//		TileEntity skullEntity = editor.getTileEntity(pos);
//		
//		if(skullEntity == null) return;
//		if(!(skullEntity instanceof TileEntitySkull)) return;
//		
//		TileEntitySkull skull = (TileEntitySkull)skullEntity;
//		
//		setType(skull, type);
//		setRotation(rand, skull, dir);
	}
	
	public static void set(IWorldEditor editor, Random rand, Coord cursor, Cardinal dir, Skull type){
		int x = cursor.getX();
		int y = cursor.getY();
		int z = cursor.getZ();
		
		set(editor, rand, x, y, z, dir, type);
	}
	
//	public static void setType(TileEntitySkull skull, Skull type){		
//		skull.setType(getSkullId(type));
//	}
//	
//	public static void setRotation(Random rand, TileEntitySkull skull, Cardinal dir){
//		
//		int directionValue = getDirectionValue(dir);
//		
//		// nudge the skull so that it isn't perfectly aligned.
//		directionValue += -1 + rand.nextInt(3);
//		
//		// make sure the skull direction value is less than 16
//		directionValue = directionValue % 16;
//		
//		skull.setSkullRotation(directionValue);
//		
//		
//		
//	}
	
	public static int getSkullId(Skull type){
		switch(type){
		case SKELETON: return 0;
		case WITHER: return 1;
		case ZOMBIE: return 2;
		case STEVE: return 3;
		case CREEPER: return 4;
		default: return 0;
		}
	}
	
	public static int getDirectionValue(Cardinal dir){
		switch(dir){
		case NORTH: return 0;
		case EAST: return 4;
		case SOUTH: return 8;
		case WEST: return 12;
		default: return 0;
		}
	}	
}
