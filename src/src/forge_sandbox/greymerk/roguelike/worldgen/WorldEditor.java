package forge_sandbox.greymerk.roguelike.worldgen;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.ITreasureChest;
import forge_sandbox.greymerk.roguelike.treasure.TreasureManager;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.init.Blocks;
//import net.minecraft.tileentity.TileEntity;
//import zhehe.roguelikedungeon.util.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldServer;
//import net.minecraft.world.gen.ChunkProviderServer;
import org.bukkit.block.Block;
import shadow_lib.async.later.Later;
import zhehe.util.FormatItem;
import zhehe.util.worldgen.HalfAsyncWorld;

public class WorldEditor implements IWorldEditor{
	
	HalfAsyncWorld world;
	private Map<Material, Integer> stats;
	private TreasureManager chests;
	private static List<Material> invalid;
	static {
            // TODO
		invalid = new ArrayList<>();
		invalid.add(Material.OAK_PLANKS);
		invalid.add(Material.WATER);
		invalid.add(Material.CACTUS);
		invalid.add(Material.SNOW);
		invalid.add(Material.GRASS);
		invalid.add(Material.STONE);
		invalid.add(Material.OAK_LEAVES);
		invalid.add(Material.POPPY);
                invalid.add(Material.DANDELION);
	};
        
        @Override
        public String getWorldName() {
            return world.world.getName();
        }
        
        @Override
        public void addLater(Later later) {
            //nothing here
        }
        
        @Override
        public boolean isFakeWorld() {
            return false;
        }
        
        @Override
        public boolean commit(int count) {
            return world.commit(count);
        }
        
        @Override
        public World getWorld() {
            return world.world;
        }
        
        @Override
        public Biome getBiome(Coord pos) {
            return world.world.getBiome(pos.getX(), pos.getZ());
        }
	
	public WorldEditor(World world){
		this.world = new HalfAsyncWorld(world);
		stats = new HashMap<>();
		this.chests = new TreasureManager();
	}
                	
	private boolean setBlock(Coord pos, MetaBlock block, int flags, boolean fillAir, boolean replaceSolid){
                Material material = block.getBlock();
                Material mat = getMaterial(pos);

                if(mat == Material.CHEST) return false;
		if(mat == Material.TRAPPED_CHEST) return false;
		if(mat == Material.SPAWNER) return false;
                
		boolean isAir = mat == Material.AIR;
		
		if(!fillAir && isAir) return false;
		if(!replaceSolid && !isAir)	return false;
                
                boolean patch = false;
                
                if(        material == Material.IRON_BARS || material == Material.REDSTONE_WIRE
                        || material == Material.WATER || material == Material.LAVA
                        || material == Material.OAK_FENCE || material == Material.SPRUCE_FENCE
                        || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE
                        || material == Material.DARK_OAK_FENCE || material == Material.ACACIA_FENCE
                        || material == Material.NETHER_BRICK_FENCE
                        ) {
                    patch = true;
                }
		
                
		try{
                        if(!patch) {
//                            world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setBlockData(block.getState(), flags == 1);
                            world.setBlockData(pos.getX(), pos.getY(), pos.getZ(), block.getState(), flags == 1);
                        } else {
//                            world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setType(block.getBlock(), true);
                            world.setType(pos.getX(), pos.getY(), pos.getZ(), block.getBlock(), true);
                        }
                        
		} catch(NullPointerException npe){
			//ignore it.
		}
		
                Material type = material;
		Integer count = stats.get(type);
		if(count == null){
			stats.put(type, 1);	
		} else {
			stats.put(type, count + 1);
		}
		
		return true;
		
	}
	
	@Override
	public boolean setBlock(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid){
		return this.setBlock(pos, block, block.getFlag(), fillAir, replaceSolid);
	}
        
        @Override
        public Block getBlock(Coord pos) {
//                return world.getBlockAt(pos.getX(), pos.getY(), pos.getZ());
                return world.getBlock(pos.getX(), pos.getY(), pos.getZ());
        }
	
	@Override
	public boolean isAirBlock(Coord pos){
//		return world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).getType() == Material.AIR;
                return world.getType(pos.getX(), pos.getY(), pos.getZ()) == Material.AIR;
	}
	
	@Override
	public long getSeed(){
		return this.world.world.getSeed();
	}
			
	@Override
	public void spiralStairStep(Random rand, Coord origin, IStair stair, IBlockFactory fill){
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(new Coord(-1, 0, -1));
		end = new Coord(origin);
		end.add(new Coord(1, 0, 1));
		
		RectSolid.fill(this, rand, start, end, air);
		fill.set(this, rand, origin);
		
		Cardinal dir = Cardinal.directions[origin.getY() % 4];
		cursor = new Coord(origin);
		cursor.add(dir);
		stair.setOrientation(Cardinal.left(dir), false).set(this, cursor);
		cursor.add(Cardinal.right(dir));
		stair.setOrientation(Cardinal.right(dir), true).set(this, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).set(this, cursor);
	}
	
	@Override
	public void fillDown(Random rand, Coord origin, IBlockFactory blocks){

		Coord cursor = new Coord(origin);
		
		while(!getMaterial(cursor).isSolid() && cursor.getY() > 1){
			blocks.set(this, rand, cursor);
			cursor.add(Cardinal.DOWN);
		}
	}
	
        @Override
        public Material getMaterial(Coord pos) {
//            return world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).getType();
            return world.getType(pos.getX(), pos.getY(), pos.getZ());
        }
        
	@Override
	public MetaBlock getMetaBlock(Coord pos){
//		return new MetaBlock(world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).getBlockData());
                return new MetaBlock(world.getBlockData(pos.getX(), pos.getY(), pos.getZ()));
	}
	
	@Override
	public boolean validGroundBlock(Coord pos){
		if(isAirBlock(pos)) return false;
                Material material = getMaterial(pos);
		return !invalid.contains(material);
	}
	
	@Override
	public int getStat(Block type){
            return 0;
//		if(!this.stats.containsKey(type)) return 0;
//		return this.stats.get(type);
	}
	
	@Override
	public Map<Material, Integer> getStats(){
		return this.stats;
	}
	
	@Override
	public void addChest(ITreasureChest toAdd){
		this.chests.add(toAdd);
	}
	
	@Override
	public TreasureManager getTreasure(){
		return this.chests;
	}
	
	@Override
	public boolean canPlace(MetaBlock block, Coord pos, Cardinal dir){
                return this.isAirBlock(pos);
	}

	@Override
	public IPositionInfo getInfo(Coord pos) {
		return new PositionInfo(this.world.world, pos);
	}
	
	@Override
	public String toString(){
		String toReturn = "";

		for(Map.Entry<Material, Integer> pair : stats.entrySet()){
			toReturn += (new FormatItem(pair.getKey())).getUnlocalizedName() + ": " + pair.getValue() + "\n";
		}
		
		return toReturn;
	}
}

