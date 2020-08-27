package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.treasure.TreasureManager;
import forge_sandbox.greymerk.roguelike.treasure.loot.books.BookStatistics;
import forge_sandbox.greymerk.roguelike.util.WeightedChoice;
import forge_sandbox.greymerk.roguelike.util.WeightedRandomizer;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import java.util.List;
import org.bukkit.inventory.ItemStack;
//import shadow_manager.DungeonWorldManager;
import zhehe.util.config.RoguelikeLootNode;
import zhehe.util.config.SimpleWorldConfig;
import zhehe.util.config.WorldConfig;
//import net.minecraft.item.ItemStack;

public class DungeonTaskLoot implements IDungeonTask {

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {
		TreasureManager treasure = editor.getTreasure();
                settings.processLoot(rand, treasure);
//                boolean loot = true;
//                String world_name = editor.getWorldName();
//                if(
//                        (WorldConfig.wc.dict.containsKey(world_name) && !(WorldConfig.wc.dict.get(world_name).roguelike.builtinLoot))
//                ) loot = false;
//                
//		if(loot) settings.processLoot(rand, treasure);
                
                String world_name = editor.getWorldName();
                if(!WorldConfig.wc.dict.containsKey(world_name)) return true;
                SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
                WeightedRandomizer<ItemStack>[] each = new WeightedRandomizer[5];
                WeightedRandomizer<ItemStack>[] whole = new WeightedRandomizer[5];
                int amount_each[] = new int[]{ 0,0,0,0,0 };
                int amount_whole[] = new int[]{ 0,0,0,0,0 };
                for(int i = 0; i < 5; i++) {
                    each[i] = new WeightedRandomizer<>();
                    whole[i] = new WeightedRandomizer<>();
                }
                {
                    List<RoguelikeLootNode> loots = swc.roguelike.loots;
                    for(RoguelikeLootNode node : loots) {
                        int weight = node.weight;
                        ItemStack is = node.getItem();
                        is.setAmount(node.max);
                        if(weight > 0) {
                            WeightedChoice<ItemStack> wc = new WeightedChoice<>(is, weight);
                            if(node.each) {
                                each[node.level].add(wc);
                                amount_each[node.level]++;
                            } else {
                                whole[node.level].add(wc);
                                amount_whole[node.level]++;
                            }
                        }
                    }
                }
                for(int i = 0; i < 5; i++) {
                    if(amount_each[i] == 0) continue;
                    treasure.addItemToAll(rand, i, each[i], amount_each[i]);
                }
                for(int i = 0; i < 5; i++) {
                    if(amount_whole[i] == 0) continue;
                    treasure.addItem(rand, i, whole[i], amount_whole[i]);
                }

                
//		treasure.addItem(rand, Treasure.STARTER, new WeightedChoice<>(new BookStatistics(editor).get(), 0), 1);
                return true;
	}
}
