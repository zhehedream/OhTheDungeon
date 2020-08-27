package forge_sandbox.greymerk.roguelike.citadel;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.theme.Theme;
import forge_sandbox.greymerk.roguelike.util.mst.MinimumSpanningTree;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public class Citadel {

	public static final int EDGE_LENGTH = 17;
	
	public static void generate(IWorldEditor editor, int x, int z){
		
		Random rand = getRandom(editor, x, z);
		
		MinimumSpanningTree mst = new MinimumSpanningTree(rand, 7, EDGE_LENGTH);
		//mst.generate(world, rand, new MetaBlock(Blocks.glowstone), new Coord(x, 100, z));
		
		CityGrounds.generate(editor, rand, mst, Theme.getTheme(Theme.OAK), new Coord(x, 50, z));
	}
	
	
	public static Random getRandom(IWorldEditor editor, int x, int z){
		long seed = editor.getSeed() * x * z;
		Random rand = new Random();
		rand.setSeed(seed);
		return rand;
	}
}
