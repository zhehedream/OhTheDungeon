package forge_sandbox.greymerk.roguelike.dungeon.segment;

import forge_sandbox.greymerk.roguelike.util.WeightedChoice;
import forge_sandbox.greymerk.roguelike.util.WeightedRandomizer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SegmentGeneratorCustom extends SegmentGeneratorBase{

	public SegmentGeneratorCustom(JsonObject json){
		
		if(json.has("segments")){
			segments = new WeightedRandomizer<Segment>();
			JsonArray data = json.get("segments").getAsJsonArray();
			for(JsonElement e : data){
				JsonObject weighted = e.getAsJsonObject();
				String type = weighted.get("type").getAsString();
				int weight = weighted.get("weight").getAsInt();
				segments.add(new WeightedChoice<Segment>(Segment.valueOf(type), weight));
			}
		}
		
		if(json.has("arch")){
			String s = json.get("arch").getAsString();
			arch = Segment.valueOf(s);
		}
		
	}
}
