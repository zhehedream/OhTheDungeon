package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.monster.MonsterProfile;
import org.bukkit.World;

public class ProfileSkeleton implements IMonsterProfile{

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		

		
		if(level == 3 && rand.nextInt(40) == 0){
			MonsterProfile.get(MonsterProfile.POISONARCHER).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(level > 1 && rand.nextInt(50) == 0){
			MonsterProfile.get(MonsterProfile.MAGICARCHER).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(level > 1 && rand.nextInt(10) == 0){
			MonsterProfile.get(MonsterProfile.WITHER).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(level > 0 && rand.nextInt(20) == 0){
			MonsterProfile.get(MonsterProfile.SWORDSMAN).addEquipment(world, rand, level, mob);
			return;
		}

		MonsterProfile.get(MonsterProfile.ARCHER).addEquipment(world, rand, level, mob);
	}

}
