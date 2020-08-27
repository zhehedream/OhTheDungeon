package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.monster.MobType;
import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.Potion;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.treasure.loot.Slot;
import forge_sandbox.greymerk.roguelike.treasure.loot.TippedArrow;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;

public class ProfileMagicArcher implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		
		mob.setMobClass(MobType.STRAY, false);
		
		mob.setSlot(EquipmentSlot.OFF_HAND, TippedArrow.get(Potion.HARM));
		mob.setSlot(EquipmentSlot.HAND, ItemWeapon.getBow(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level)));
		
		for(EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
				}){
			ItemStack item = ItemArmour.get(rand, Slot.getSlot(slot), Quality.WOOD);
			Enchant.enchantItem(rand, item, 20);
			ItemArmour.dyeArmor(item, 51, 0, 102);
			mob.setSlot(slot, item);
		}
	}

}
