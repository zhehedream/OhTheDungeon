package forge_sandbox.greymerk.roguelike.dungeon.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.LevelGenerator;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonFactory;
import forge_sandbox.greymerk.roguelike.dungeon.base.SecretFactory;
import forge_sandbox.greymerk.roguelike.dungeon.segment.SegmentGenerator;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.theme.Theme;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.ILoot;
import forge_sandbox.greymerk.roguelike.treasure.loot.Loot;
import forge_sandbox.greymerk.roguelike.treasure.loot.LootRuleManager;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemSpecialty;

public class SettingsRandom extends DungeonSettings{

	public SettingsRandom(Random rand){
		
		this.towerSettings = new TowerSettings(
				Tower.getRandom(rand),
				Theme.getRandom(rand));
		
		Map<Integer, LevelSettings> levels = new HashMap<Integer, LevelSettings>();
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			
			level.setDifficulty(i);
			level.setGenerator(LevelGenerator.CLASSIC);
			level.setNumRooms(15);
			level.setRange(60);
			
			DungeonFactory rooms = DungeonFactory.getRandom(rand, 8);
			level.setRooms(rooms);
			
			level.setScatter(15);
			
			SecretFactory secrets = SecretFactory.getRandom(rand, 2);
			level.setSecrets(secrets);
			
			SegmentGenerator segments = SegmentGenerator.getRandom(rand, 12);
			level.setSegments(segments);
			
			level.setTheme(Theme.getRandom(rand));
			levels.put(i, level);
		}
		
		this.levels = levels;
	
		this.lootRules = new LootRuleManager();
		ILoot loot = Loot.getLoot();
		lootRules.add(Treasure.STARTER, loot.get(Loot.WEAPON, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.FOOD, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.TOOL, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.SUPPLY, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 0, true, 2);
		for(int i = 0; i < 5; ++i){
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.POTION, i),  i, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.ARMOUR, i),  i, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.FOOD, i),  i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.POTION, i),  i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.WEAPON, i),  i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, i),  i, true, 1);
			lootRules.add(Treasure.BLOCKS, loot.get(Loot.BLOCK, i),  i, true, 6);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, i),  i, true, 1);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBONUS, i),  i, true, 2);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBOOK, i),  i, true, 1);
			lootRules.add(Treasure.FOOD, loot.get(Loot.FOOD, i),  i, true, 8);
			lootRules.add(Treasure.ORE, loot.get(Loot.ORE, i),  i, true, 5);
			lootRules.add(Treasure.POTIONS, loot.get(Loot.POTION, i),  i, true, 6);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.ORE, i),  i, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.TOOL, i),  i, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.BLOCK, i),  i, true, 1);
			lootRules.add(Treasure.SUPPLIES, loot.get(Loot.SUPPLY, i),  i, true, 6);
			lootRules.add(Treasure.SMITH, loot.get(Loot.ORE, i),  i, true, 6);
			lootRules.add(Treasure.SMITH, loot.get(Loot.SMITHY, i),  i, true, 1);
			lootRules.add(Treasure.MUSIC, loot.get(Loot.MUSIC, i),  i, true, 1);
			lootRules.add(Treasure.REWARD, loot.get(Loot.REWARD, i),  i, true, 1);
			lootRules.add(null, loot.get(Loot.JUNK, i),  i, true, 6);
			lootRules.add(null, new ItemSpecialty(0, i, Quality.get(i)),  i, false, 3);
			lootRules.add(null, new ItemEnchBook(0, i),  i, false, i * 2 + 5);
		}
		
	}
}
