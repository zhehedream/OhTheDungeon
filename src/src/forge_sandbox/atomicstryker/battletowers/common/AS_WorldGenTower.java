/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.atomicstryker.battletowers.common;

import forge_sandbox.atomicstryker.battletowers.common.TreasureList.ItemStackNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import otd.Main;
import shadow_lib.api.SpawnerDecryAPI;
import zhehe.util.config.EnumType.ChestType;
import zhehe.util.config.LootNode;
import zhehe.util.config.SimpleWorldConfig;
import zhehe.util.config.WorldConfig;

public class AS_WorldGenTower
{

    public String failState;
    public static BlockData TORCH = Bukkit.createBlockData("minecraft:wall_torch[facing=south]");

    private static int candidates[][] = { { 4, -5 }, { 4, 0 }, { 4, 5, }, { 0, -5 }, { 0, 0 }, { 0, 5, }, { -4, -5 }, { -4, 0, }, { -4, 5 } };

    private static int candidatecount = candidates.length;
    private final static int maxHoleDepthInBase = 22;

    public static void generate(World world, Random random, int ix, int jy, int kz, int towerchoice, boolean underground)
    {
        TowerTypes towerChosen = TowerTypes.values()[towerchoice];

        Material towerWallBlockID = towerChosen.getWallBlockID();
        Material towerLightBlockID = towerChosen.getLightBlockID();
        BlockData towerFloorBlockID = towerChosen.getFloorBlockID();

        int startingHeight = underground ? Math.max(jy - 70, 15) : jy - 6;
        int maximumHeight = underground ? jy + 7 : 120;

        int floor = 1;
        boolean topFloor = false;
        int builderHeight = startingHeight;
        
        List<Location> loc = new ArrayList<>();
        List<Material> mat = new ArrayList<>();
        
        for (; builderHeight < maximumHeight; builderHeight += 7) // builderHeight jumps floors
        {
            if (builderHeight + 7 >= maximumHeight)
            {
                topFloor = true;
            }

            for (int floorIterator = 0; floorIterator < 7; floorIterator++) // build each floor height block till next floor
            {
                if (floor == 1 && floorIterator < 4) // initial floor
                {
                    floorIterator = 4;
                }
                for (int xIterator = -7; xIterator < 7; xIterator++) // do each X
                {
                    for (int zIterator = -7; zIterator < 7; zIterator++) // do each Z
                    {
                        int iCurrent = xIterator + ix;
                        int jCurrent = floorIterator + builderHeight;
                        int zCurrent = zIterator + kz;

                        if (zIterator == -7) // last row, 14
                        {
                            if (xIterator > -5 && xIterator < 4) // rear outer wall
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                            }
                            continue;
                        }
                        if (zIterator == -6 || zIterator == -5) // rows 12 and 13
                        {
                            if (xIterator == -5 || xIterator == 4) // outer wall parts
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                                continue;
                            }
                            if (zIterator == -6) // row 13 extra
                            {
                                if (xIterator == (floorIterator + 1) % 7 - 3) // stairwell!!
                                {
                                    if (!(underground && floor == 1))
                                    {
                                        Directional dir = (Directional) Bukkit.createBlockData(towerChosen.getStairBlockID());
                                        dir.setFacing(BlockFace.EAST);
                                        world.getBlockAt(iCurrent, jCurrent, zCurrent).setBlockData(dir, false);
                                    }
                                    if (floorIterator == 5)
                                    {
                                        world.getBlockAt(iCurrent - 7, jCurrent, zCurrent).setBlockData(towerFloorBlockID, false);
                                    }
                                    if (floorIterator == 6 && topFloor) // top ledge part
                                    {
                                        buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                                    }
                                    continue;
                                }
                                if (xIterator < 4 && xIterator > -5) // tower insides
                                {
                                    world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                                }
                                continue;
                            }
                            if (zIterator != -5 || xIterator <= -5 || xIterator >= 5) // outside tower
                            {
                                continue;
                            }
                            if (floorIterator != 0 && floorIterator != 6 || xIterator != -4 && xIterator != 3)
                            {
                                if (floorIterator == 5 && (xIterator == 3 || xIterator == -4))
                                {
                                    buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockID);
                                }
                                else
                                {
                                    buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator); // under stairwell
                                }
                            }
                            else
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator == -4 || zIterator == -3 || zIterator == 2 || zIterator == 3) // rows 11, 10, 5, 4
                        {
                            if (xIterator == -6 || xIterator == 5) // outer wall parts
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                                continue;
                            }
                            if (xIterator <= -6 || xIterator >= 5) // outside tower
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockID);
                                continue;
                            }
                            if (world.getBlockAt(iCurrent, jCurrent, zCurrent).getType()!= Material.CHEST) // tower inside space
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator > -3 && zIterator < 2) // rows 10 to 5
                        {
                            if (xIterator == -7 || xIterator == 6)
                            {
                                if (floorIterator < 0 || floorIterator > 3 || ((xIterator != -7 && xIterator != 6) || underground) || zIterator != -1 && zIterator != 0) // wall, short of window
                                {
                                    buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                                }
                                else
                                {
                                    world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                                }
                                continue;
                            }
                            if (xIterator <= -7 || xIterator >= 6)
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockID);
                            }
                            else
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator == 4) // row 3
                        {
                            if (xIterator == -5 || xIterator == 4)
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                                continue;
                            }
                            if (xIterator <= -5 || xIterator >= 4)
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockID);
                            }
                            else
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator == 5) // row 2
                        {
                            if (xIterator == -4 || xIterator == -3 || xIterator == 2 || xIterator == 3)
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                                continue;
                            }
                            if (xIterator <= -3 || xIterator >= 2)
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockID);
                            }
                            else
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                            }
                            continue;
                        }
                        if (zIterator != 6 || xIterator <= -3 || xIterator >= 2)
                        {
                            continue;
                        }
                        if (floorIterator < 0 || floorIterator > 3 || xIterator != -1 && xIterator != 0)
                        {
                            buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                        }
                        else
                        {
                            buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockID, floor, floorIterator);
                        }
                    }

                }
            }

            if (floor == 2)
            {
                world.getBlockAt(ix + 3, builderHeight, kz - 5).setType(towerWallBlockID, false);
                world.getBlockAt(ix + 3, builderHeight - 1, kz - 5).setType(towerWallBlockID, false);
            }
            if ((!underground && topFloor) || (underground && floor == 1))
            {
                if (towerChosen != TowerTypes.Null)
                {
                    world.spawnEntity(new Location(world, ix + 0.5D, builderHeight + 6, kz + 0.5D), EntityType.WITHER_SKELETON);
                    world.spawnEntity(new Location(world, ix + 0.5D, builderHeight + 6, kz + 0.5D), EntityType.WITHER_SKELETON);
                    world.spawnEntity(new Location(world, ix + 0.5D, builderHeight + 6, kz + 0.5D), EntityType.WITHER_SKELETON);
                }
            }
            else
            {
                if (towerChosen != TowerTypes.Null)
                {
                    try {
                        Block block = world.getBlockAt(ix + 2, builderHeight + 6, kz + 2);
                        block.setType(Material.SPAWNER, true);
                        CreatureSpawner tileentitymobspawner = ((CreatureSpawner)block.getState());
                        tileentitymobspawner.setSpawnedType(getMobType(random));
                        tileentitymobspawner.update();
                        SpawnerDecryAPI.setSpawnerDecry(block, Main.instance);

                        block = world.getBlockAt(ix - 3, builderHeight + 6, kz + 2);
                        block.setType(Material.SPAWNER, true);
                        tileentitymobspawner = (CreatureSpawner) block.getState();
                        tileentitymobspawner.setSpawnedType(getMobType(random));
                        tileentitymobspawner.update();
                        SpawnerDecryAPI.setSpawnerDecry(block, Main.instance);
                    } catch(Exception ex) {
                        Block block = world.getBlockAt(ix + 2, builderHeight + 6, kz + 2);
                        block.setType(Material.AIR, true);
                    }
                }
                else
                {
                    world.getBlockAt(ix + 2, builderHeight + 6, kz + 2).setType(Material.AIR, false);
                    world.getBlockAt(ix - 3, builderHeight + 6, kz + 2).setType(Material.AIR, false);
                }
            }
            // chest pedestal
            world.getBlockAt(ix, builderHeight + 6, kz + 3).setBlockData(towerFloorBlockID, false);
            world.getBlockAt(ix - 1, builderHeight + 6, kz + 3).setBlockData(towerFloorBlockID, false);

            if (builderHeight + 56 >= 120 && floor == 1)
            {
                floor = 2;
            }
            if (towerChosen != TowerTypes.Null) {
                boolean best_chest = false;
                if(!underground && topFloor) best_chest = true;
                if(underground && floor == 1) best_chest = true;
                Material material1, material2;
                if(!best_chest) {
                    material1 = TreasureList.treasure_block[random.nextInt(TreasureList.treasure_block.length)];
                    material2 = TreasureList.treasure_block[random.nextInt(TreasureList.treasure_block.length)];
                } else {
                    String world_name = world.getName();
                    boolean box = true;
                    if(WorldConfig.wc.dict.containsKey(world_name)) {
                        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
                        if(swc.battletower.chest == ChestType.CHEST) box = false;
                    }
                    
                    if(box) material1 = Material.SHULKER_BOX;
                    else material1 = Material.CHEST;
                    material2 = TreasureList.top_treasure_block[random.nextInt(TreasureList.top_treasure_block.length)];
                }
//                world.getBlockAt(ix, builderHeight + 7, kz + 3).setType(material1, true);
//                world.getBlockAt(ix - 1, builderHeight + 7, kz + 3).setType(material2, true);
                loc.add(new Location(world, ix, builderHeight + 7, kz + 3));
                loc.add(new Location(world, ix - 1, builderHeight + 7, kz + 3));
                mat.add(material1);
                mat.add(material2);
//                Bukkit.getLogger().log(Level.SEVERE, Integer.toString(builderHeight + 7));
                
//                if(best_chest) {
//                    Block block = world.getBlockAt(ix, builderHeight + 7, kz + 3);
//                    ShulkerBox sb = (ShulkerBox) block.getState();
//                    Inventory inv = sb.getInventory();
//                    for(ItemStackNode isn : TreasureList.TOP) {
//                        if(random.nextDouble() < isn.chance) {
//                            int amount = isn.min + random.nextInt(isn.max - isn.min + 1);
//                            ItemStack is = isn.is.clone();
//                            is.setAmount(amount);
//                            inv.addItem(is);
//                        }
//                    }
//                }
            }
/*
            if (towerChosen != TowerTypes.Null)
            {
                // chest
                TowerStageItemManager floorChestManager;
                if (!underground)
                {
                    floorChestManager = topFloor ? WorldGenHandler.getTowerStageManagerForFloor(10) : WorldGenHandler.getTowerStageManagerForFloor(floor);
                }
                else
                {
                    floorChestManager = floor == 1 ? WorldGenHandler.getTowerStageManagerForFloor(10) : WorldGenHandler.getTowerStageManagerForFloor(Math.abs(11 - floor));
                }

                for (int chestlength = 0; chestlength < 2; chestlength++)
                {
                    world.setBlockState(new BlockPos(ix - chestlength, builderHeight + 7, kz + 3), Blocks.CHEST.getStateFromMeta(2));
                    TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(new BlockPos(ix - chestlength, builderHeight + 7, kz + 3));
                    if (tileentitychest != null)
                    {
                        int count = underground ? AS_BattleTowersCore.instance.itemGenerateAttemptsPerFloor * 2 : AS_BattleTowersCore.instance.itemGenerateAttemptsPerFloor;
                        List<ItemStack> generatedStacks = floorChestManager.getStageItemStacks(world, world.rand, tileentitychest, count);
                        List<Integer> freeSlots = new ArrayList<>(tileentitychest.getSizeInventory());
                        for (int i = 0; i < tileentitychest.getSizeInventory(); i++)
                        {
                            freeSlots.add(i);
                        }
                        Iterator<ItemStack> iterator = generatedStacks.iterator();
                        while (iterator.hasNext() && !freeSlots.isEmpty())
                        {
                            Integer slot = freeSlots.get(world.rand.nextInt(freeSlots.size()));
                            freeSlots.remove(slot);
                            tileentitychest.setInventorySlotContents(slot, iterator.next());
                        }
                    }
                }
            }
            else
            {
                for (int chestlength = 0; chestlength < 2; chestlength++)
                {
                    world.getBlockAt(ix - chestlength, builderHeight + 7, kz + 3).setType(Material.CHEST, false);
                }
            }
*/
            // move lights builder a bit higher, to support non-opaque lights such as lamps
            if (towerLightBlockID == Material.TORCH)
            {
                world.getBlockAt(ix + 3,  builderHeight+2,  kz - 6).setBlockData(TORCH, false);
                world.getBlockAt(ix - 4,  builderHeight+2,  kz - 6).setBlockData(TORCH, false);
                world.getBlockAt(ix + 1,  builderHeight+2,  kz - 4).setBlockData(TORCH, false);
                world.getBlockAt(ix - 2,  builderHeight+2,  kz - 4).setBlockData(TORCH, false);
            }
            else
            {
                world.getBlockAt(ix + 3,  builderHeight+2,  kz - 6).setType(towerLightBlockID, false);
                world.getBlockAt(ix - 4,  builderHeight+2,  kz - 6).setType(towerLightBlockID, false);
                world.getBlockAt(ix + 1,  builderHeight+2,  kz - 4).setType(towerLightBlockID, false);
                world.getBlockAt(ix - 2,  builderHeight+2,  kz - 4).setType(towerLightBlockID, false);
            }

            if (towerChosen != TowerTypes.Null)
            {
                for (int l3 = 0; l3 < (floor * 4 + towerChosen.ordinal()) - 8 && !topFloor; l3++) // random hole poker
                {
                    int k4 = 5 - random.nextInt(12);
                    int k5 = builderHeight + 5;
                    int j6 = 5 - random.nextInt(10);
                    if (j6 < -2 && k4 < 4 && k4 > -5 && k4 != 1 && k4 != -2)
                    {
                        continue;
                    }
                    k4 += ix;
                    j6 += kz;
                    if (world.getBlockAt(k4, k5, j6).getType()== towerFloorBlockID.getMaterial() && world.getBlockAt(k4, k5 + 1, j6).getType()!= Material.SPAWNER)
                    {
                        world.getBlockAt(k4, k5, j6).setType(Material.AIR, false);
                    }
                }
            }

            floor++;
        }
        
        int len = loc.size();
        for(int i = 0; i < len; i++) {
            Location l = loc.get(i);
            Material m = mat.get(i);
            world.getBlockAt(l).setType(m, true);
            
            if(m == Material.SHULKER_BOX || m == Material.CHEST) {
                    Block block = world.getBlockAt(l);
                    Inventory inv;
                    if(m == Material.SHULKER_BOX) {
                        ShulkerBox sb = (ShulkerBox) block.getState();
                        inv = sb.getInventory();
                    } else {
                        Chest ch = (Chest) block.getState();
                        inv = ch.getInventory();
                    }
                    String world_name = world.getName();
                    boolean builtin = true;
                    if(WorldConfig.wc.dict.containsKey(world_name)) {
                        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
                        if(!swc.battletower.builtinLoot) builtin = false;
                    }
                    if(builtin) {
                        for(ItemStackNode isn : TreasureList.TOP) {
                            if(random.nextDouble() < isn.chance) {
                                int amount = isn.min + random.nextInt(isn.max - isn.min + 1);
                                ItemStack is = isn.is.clone();
                                is.setAmount(amount);
                                inv.addItem(is);
                            }
                        }
                    }
                    if(WorldConfig.wc.dict.containsKey(world_name)) {
                        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
                        for(LootNode ln : swc.battletower.loots) {
                            if(random.nextDouble() < ln.chance) {
                                ItemStack is = ln.getItem();
                                int amount = ln.min + random.nextInt(ln.max - ln.min + 1);
                                is.setAmount(amount);
                                inv.addItem(is);
                            }
                        }
                    }
            }
        }

//        System.out.println("Battle Tower type " + towerChosen + " spawned at [ " + ix + " | " + kz + " ], underground: " + underground);
    }

    private static void buildFloorPiece(World world, int i, int j, int k, BlockData towerFloorBlockID)
    {
        world.getBlockAt(i, j, k).setBlockData(towerFloorBlockID, false);
    }

    private static void buildWallPiece(World world, int i, int j, int k, Material towerWallBlockID, int floor, int floorIterator)
    {
        world.getBlockAt(i, j, k).setType(towerWallBlockID, false);
        if (floor == 1 && floorIterator == 4)
        {
            fillTowerBaseToGround(world, i, j, k, towerWallBlockID);
        }
    }

    private static void fillTowerBaseToGround(World world, int i, int j, int k, Material blocktype)
    {
        int y = j - 1;
        while (y > 0 && !isBuildableBlockID(world.getBlockAt(i, y, k).getType()))
        {
            world.getBlockAt(i, y, k).setType(blocktype, true);
            y--;
        }
    }

    private static boolean isFoliageBlockID(Material ID)
    {
        return (ID == Material.SNOW || ID == Material.TALL_GRASS || ID == Material.DEAD_BUSH || ID == Material.GRASS ||
                ID == Material.OAK_LOG || ID == Material.OAK_LEAVES ||
                ID == Material.SPRUCE_LOG || ID == Material.SPRUCE_LEAVES ||
                ID == Material.BIRCH_LOG || ID == Material.BIRCH_LEAVES ||
                ID == Material.JUNGLE_LOG || ID == Material.JUNGLE_LEAVES ||
                ID == Material.ACACIA_LOG || ID == Material.ACACIA_LEAVES ||
                ID == Material.DARK_OAK_LOG || ID == Material.DARK_OAK_LEAVES);
    }

    private static boolean isBuildableBlockID(Material ID)
    {
        return (ID == Material.STONE || ID == Material.GRASS_BLOCK || ID == Material.SAND || ID == Material.SANDSTONE ||
                ID == Material.GRAVEL || ID == Material.DIRT);
    }

    private static EntityType getMobType(Random random)
    {
        switch (random.nextInt(10))
        {
        case 0:
        case 1:
        case 2:
        {
            return EntityType.SKELETON;
        }
        case 3:
        case 4:
        case 5:
        case 6:
        {
            return EntityType.ZOMBIE;
        }
        case 7:
        case 8:
        {
            return EntityType.SPIDER;
        }
        case 9:
        {
            return EntityType.CAVE_SPIDER;
        }
        default:
            return EntityType.ZOMBIE;
        }
    }

    private static class BlockConst {
        public static BlockData DOUBLE_STONE_SLAB = Bukkit.createBlockData("minecraft:stone_slab[type=double]");
        public static BlockData DOUBLE_SANDSTONE_SLAB = Bukkit.createBlockData("minecraft:sandstone_slab[type=double]");
        public static BlockData PETRIFIED_OAK_SLAB = Bukkit.createBlockData("minecraft:petrified_oak_slab[type=double]");
    }
    
    public enum TowerTypes
    {
        Null("null",Material.AIR, Material.AIR, Material.AIR, Material.AIR),
        CobbleStone("cobblestone",Material.COBBLESTONE, Material.TORCH, BlockConst.DOUBLE_STONE_SLAB, Material.STONE_STAIRS),
        CobbleStoneMossy("cobblestonemossy",Material.MOSSY_COBBLESTONE, Material.TORCH, BlockConst.DOUBLE_STONE_SLAB, Material.STONE_STAIRS),
        SandStone("sandstone",Material.SANDSTONE, Material.TORCH, BlockConst.DOUBLE_SANDSTONE_SLAB, Material.SANDSTONE_STAIRS),
        Ice("ice",Material.ICE, Material.AIR /* Blocks.GLOWSTONE */, Material.CLAY, Material.OAK_STAIRS), // since when does glowstone melt ice
        SmoothStone("smoothstone",Material.STONE, Material.TORCH, BlockConst.PETRIFIED_OAK_SLAB, Material.STONE_STAIRS),
        Netherrack("netherrack",Material.NETHERRACK, Material.GLOWSTONE, Material.SOUL_SAND, Material.NETHER_BRICK_STAIRS),
        Jungle("jungle",Material.MOSSY_COBBLESTONE, Material.COBWEB, Material.DIRT, Material.JUNGLE_STAIRS);

        private final Material wallBlockID;
        private final Material lightBlockID;
        private final BlockData floorBlockID;
        private final Material stairBlockID;
        private final String typeName;
        
        TowerTypes(String t, Material a, Material b, Material c, Material e)
        {
            this.wallBlockID = a;
            this.lightBlockID = b;
            this.floorBlockID = Bukkit.createBlockData(c);
            this.stairBlockID = e;
            this.typeName = t;
        }

        TowerTypes(String t, Material a, Material b, BlockData c, Material e)
        {
            this.wallBlockID = a;
            this.lightBlockID = b;
            this.floorBlockID = c;
            this.stairBlockID = e;
            this.typeName = t;
        }

        Material getWallBlockID()
        {
            return wallBlockID;
        }

        Material getLightBlockID()
        {
            return lightBlockID;
        }

        BlockData getFloorBlockID()
        {
            return floorBlockID;
        }

        Material getStairBlockID()
        {
            return stairBlockID;
        }

        public String getName()
        {
            return this.typeName;
        }
    }

}

