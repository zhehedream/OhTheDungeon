package forge_sandbox.greymerk.roguelike.util.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import forge_sandbox.greymerk.roguelike.util.graph.Edge;
import forge_sandbox.greymerk.roguelike.util.graph.Graph;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;

public class MinimumSpanningTree{
	
	List<MSTPoint> points;
	Set<Edge<MSTPoint>> mstEdges;
	
	public MinimumSpanningTree(Random rand, int size, int edgeLength){
		this(rand, size, edgeLength, new Coord(0, 0, 0));
	}
	
	public MinimumSpanningTree(Random rand, int size, int edgeLength, Coord origin){
		
		points = new ArrayList<MSTPoint>();
		mstEdges = new HashSet<Edge<MSTPoint>>();
		
		
		int offset = size / 2 * edgeLength;
		
		for(int i = 0; i < size; ++i){
			
			Coord temp = new Coord(origin);
			temp.add(Cardinal.NORTH, offset);
			temp.add(Cardinal.WEST, offset);
			temp.add(Cardinal.SOUTH, edgeLength * i);
			
			for(int j = 0; j < size; ++j){
				points.add(new MSTPoint(new Coord(temp), rand));
				temp.add(Cardinal.EAST, edgeLength);
			}
		}

		ArrayList<Edge<MSTPoint>> edges = new ArrayList<Edge<MSTPoint>>();
		for(MSTPoint p : points){
			for(MSTPoint o : points){
				if(p.equals(o)) continue;
				edges.add(new Edge<MSTPoint>(p,o, p.distance(o)));
			}
}
		
		

		Collections.sort(edges);
		
		for(Edge<MSTPoint> e : edges){
			MSTPoint start = e.getStart();
			MSTPoint end = e.getEnd();
			
			if(find(start) == find(end)) continue;
			union(start, end);
			mstEdges.add(e);
		}
		
	}
	
	
	private void union(MSTPoint a, MSTPoint b){
		MSTPoint root1 = find(a);
		MSTPoint root2 = find(b);
		if(root1 == root2) return;
		
		if(root1.getRank() > root2.getRank()){
			root2.setParent(root1);
		} else {
			root1.setParent(root2);
			if(root1.getRank() == root2.getRank()){
				root2.incRank();
			}
		}
	}
	
	private MSTPoint find(MSTPoint p){
		if(p.getParent() == p) return p;
		p.setParent(find(p.getParent()));
		return p.getParent();
	}
	
	public void generate(IWorldEditor editor, Random rand, IBlockFactory blocks, Coord pos){
		
		for(Edge<MSTPoint> e : this.mstEdges){
			
			Coord start = e.getStart().getPosition();
			start.add(pos);
			Coord end = e.getEnd().getPosition();
			end.add(pos);
			
			RectHollow.fill(editor, rand, start, end, blocks);
		}
	}
	
	public List<Edge<MSTPoint>> getEdges(){
		List<Edge<MSTPoint>> toReturn = new ArrayList<Edge<MSTPoint>>();
		toReturn.addAll(this.mstEdges);
		return toReturn;
	}
	
	public Graph<Coord> getGraph(){
		Graph<Coord> layout = new Graph<Coord>();
		for(Edge<MSTPoint> e : this.mstEdges){
			Coord start = e.getStart().getPosition();
			Coord end = e.getEnd().getPosition();
			layout.addEdge(new Edge<Coord>(start, end, start.distance(end)));
		}
		
		return layout;
	}
}
