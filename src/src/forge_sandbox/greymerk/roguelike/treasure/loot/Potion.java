package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.util.IWeighted;
import forge_sandbox.greymerk.roguelike.util.WeightedChoice;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

//import net.minecraft.init.Items;
//import net.minecraft.init.PotionEffectTypes;
//import net.minecraft.item.ItemStack;
//import net.minecraft.potion.PotionEffectType;
//import net.minecraft.potion.PotionUtils;

public enum Potion {
	
	HEALING, HARM, REGEN, POISON, STRENGTH, WEAKNESS, SLOWNESS, SWIFTNESS, FIRERESIST;
	
	public static ItemStack getRandom(Random rand){
		Potion type = Potion.values()[rand.nextInt(Potion.values().length)];
		return getSpecific(rand, type);
	}
	
	public static ItemStack getSpecific(Random rand, Potion effect){
		return getSpecific(PotionForm.REGULAR, effect, rand.nextBoolean(), rand.nextBoolean());
	}
	
	public static ItemStack getSpecific(Random rand, PotionForm type, Potion effect){
		return getSpecific(type, effect, rand.nextBoolean(), rand.nextBoolean());
	}
	
	public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception{
		if(!data.has("name")) throw new Exception("Potion missing name field");
		String nameString = data.get("name").getAsString();
                PotionEffectType type;
                try {
                    type = PotionEffectType.getByName(nameString);
                } catch(Exception ex) {
                    type = PotionEffectType.REGENERATION;
//                    Bukkit.getLogger().log(Level.SEVERE, "###" + nameString);
                }
		ItemStack item = !data.has("form") ? new ItemStack(Material.POTION)
				: data.get("form").getAsString().toLowerCase().equals("splash") ? new ItemStack(Material.SPLASH_POTION)
				: data.get("form").getAsString().toLowerCase().equals("lingering") ? new ItemStack(Material.LINGERING_POTION)
				: new ItemStack(Material.POTION);
                PotionMeta meta = (PotionMeta) item.getItemMeta();
                meta.addCustomEffect(new PotionEffect(type, 60, 1), true);
                item.setItemMeta(meta);
                return new WeightedChoice<>(item, weight);
//		return new WeightedChoice<ItemStack>(PotionUtils.addPotionToItemStack(item, type), weight);
	}
	
	public static ItemStack getSpecific(PotionForm type, Potion effect, boolean upgrade, boolean extend){
		
		ItemStack potion;
		
		switch(type){
		case REGULAR: potion = new ItemStack(Material.POTION); break;
		case SPLASH: potion = new ItemStack(Material.SPLASH_POTION); break;
		case LINGERING: potion = new ItemStack(Material.LINGERING_POTION); break;
		default: potion = new ItemStack(Material.POTION); break;
		}
		
		PotionEffectType data = getEffect(effect, upgrade, extend);
		
		return addPotionToItemStack(potion, data, upgrade, extend);
	}
        
        private static ItemStack addPotionToItemStack(ItemStack potion, PotionEffectType data, boolean upgrade, boolean extend) {
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            int level = upgrade ? 1 : 0;
            if(upgrade) extend = false;
            int time = extend ? 8 * 60 * 20 : 3 * 60 * 20;
            meta.addCustomEffect(new PotionEffect(data, time, level), true);
            potion.setItemMeta(meta);
            return potion;
        }
	
	public static PotionEffectType getEffect(Potion effect, boolean upgrade, boolean extend){
		
		if(effect == null) return PotionEffectType.GLOWING;
		
		switch(effect){
		case HEALING: return upgrade ? PotionEffectType.HEAL : PotionEffectType.HEAL;
		case HARM: return upgrade ? PotionEffectType.HARM : PotionEffectType.HARM;
		case REGEN:
			if(extend){
				return PotionEffectType.REGENERATION;
			} else {
				return upgrade ? PotionEffectType.REGENERATION : PotionEffectType.REGENERATION;
			}
		case POISON:
			if(extend){
				return PotionEffectType.POISON;
			} else {
				return upgrade ? PotionEffectType.POISON : PotionEffectType.POISON;
			}
		case STRENGTH:
			if(extend){
				return PotionEffectType.INCREASE_DAMAGE;
			} else {
				return upgrade ? PotionEffectType.INCREASE_DAMAGE : PotionEffectType.INCREASE_DAMAGE;
			}
		case WEAKNESS:
			if(extend){
				return PotionEffectType.WEAKNESS;
			} else {
				return PotionEffectType.WEAKNESS;
			}
		case SLOWNESS:
			if(extend){
				return PotionEffectType.SLOW;
			} else {
				return PotionEffectType.SLOW;
			}
		case SWIFTNESS:
			if(extend){
				return PotionEffectType.SPEED;
			} else {
				return upgrade ? PotionEffectType.SPEED : PotionEffectType.SPEED;
			}
		case FIRERESIST:
			if(extend){
				return PotionEffectType.FIRE_RESISTANCE;
			} else {
				return PotionEffectType.FIRE_RESISTANCE;
			} 
		default: return PotionEffectType.GLOWING;
		}
	}
}
