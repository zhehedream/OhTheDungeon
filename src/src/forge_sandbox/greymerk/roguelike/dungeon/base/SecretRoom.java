package forge_sandbox.greymerk.roguelike.dungeon.base;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SecretRoom implements ISecretRoom {

	private int count;
	private IDungeonRoom prototype;
	DungeonRoom type;
	
	public SecretRoom(DungeonRoom type, int count){
		this.count = count;
		this.type = type;
		this.prototype = DungeonRoom.getInstance(type);
	}
	
	public SecretRoom(SecretRoom toCopy){
		this.count = toCopy.count;
		this.prototype = toCopy.prototype;
		this.type = toCopy.type;
	}
	
	private boolean isValid(IWorldEditor editor, Random rand, Cardinal dir, Coord pos){
		if(count <= 0) return false;
		Coord cursor = new Coord(pos);
		cursor.add(dir, prototype.getSize() + 5);
		
		return prototype.validLocation(editor, dir, cursor);
	}
	
	@Override
	public void add(int count){
		this.count += count;
	}
	
	@Override
	public int getCount(){
		return this.count;
	}
	
	@Override
	public IDungeonRoom genRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos){
		if(!isValid(editor, rand, dir, pos)) return null;
		
		int size = prototype.getSize();
		
		Coord start = new Coord(pos);
		Coord end = new Coord(pos);
		start.add(Cardinal.orthogonal(dir)[0]);
		start.add(Cardinal.DOWN);
		start.add(dir, 2);
		end.add(Cardinal.orthogonal(dir)[1]);
		end.add(dir, size + 5);
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, settings.getTheme().getPrimary().getWall(), false, true);
		
		end = new Coord(pos);
		end.add(dir, size + 5);
		end.add(Cardinal.UP);
		RectSolid.fill(editor, rand, pos, end, BlockType.get(BlockType.AIR));
		
		end.add(Cardinal.DOWN);
		this.prototype.generate(editor, rand, settings, new Cardinal[]{dir}, end);
		count -= 1;
		
		IDungeonRoom generated = this.prototype;
		this.prototype = DungeonRoom.getInstance(this.type);
		
		return generated;
	}
	
	@Override
	public boolean equals(Object o){
		
		SecretRoom other = (SecretRoom)o;
		
		if(this.type != other.type) return false;
		
		if(this.count != other.count) return false;
		
		return true;
	}
}
