/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.generator;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.WorldEditor;
import java.util.Random;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.World;
import shadow_lib.async.AsyncRoguelikeDungeon;
import shadow_lib.async.AsyncWorldEditor;
import zhehe.util.AsyncLog;
import zhehe.util.config.SimpleWorldConfig;
import zhehe.util.config.WorldConfig;

/**
 *
 * @author
 */
public class RoguelikeGenerator implements IGenerator {
    @Override
    public Set<String> getBiomeExclusions(World world) {
        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
        return swc.roguelike.biomeExclusions;
    }
    @Override
    public boolean generateDungeon(World world, Random random, Chunk chunk) {
        AsyncWorldEditor editor = new AsyncWorldEditor(world);
        boolean res = AsyncRoguelikeDungeon.generateAsync(random, editor, 
                chunk.getX() * 16 + 4, chunk.getZ() * 16 + 4);
        if(res) AsyncLog.logMessage("[Roguelike Dungeon @ " + world.getName() + "] x=" + chunk.getX() * 16 + ", z=" + chunk.getZ() * 16);
        return res;
    }

    public boolean generateDungeonSync(World world, Random random, Chunk chunk) {
        IWorldEditor editor = new WorldEditor(world);
        Dungeon dungeon = new Dungeon(editor);
        boolean res = dungeon.forceSpawnInChunk(random, chunk.getX(), chunk.getZ());
        if(res) AsyncLog.logMessage("[Roguelike Dungeon @ " + world.getName() + "] x=" + chunk.getX() * 16 + ", z=" + chunk.getZ() * 16);
        return res;
    }
}
