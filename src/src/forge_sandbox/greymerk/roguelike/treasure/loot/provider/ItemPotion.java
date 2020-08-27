package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.Potion;
import forge_sandbox.greymerk.roguelike.treasure.loot.PotionForm;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.item.ItemStack;

public class ItemPotion extends ItemBase{

	public ItemPotion(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		final Potion[] potions = new Potion[]{
				Potion.HEALING,
				Potion.STRENGTH,
				Potion.SWIFTNESS,
				Potion.REGEN
		};
		Potion type = potions[rand.nextInt(potions.length)];
		return Potion.getSpecific(PotionForm.REGULAR, type, true, false);
	}
}
