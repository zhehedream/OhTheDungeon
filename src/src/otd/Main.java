/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
//import forge_sandbox.jaredbgreat.dldungeons.ConfigHandler;
import forge_sandbox.Sandbox;
import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.treasure.loot.Banner;
import forge_sandbox.greymerk.roguelike.treasure.loot.Firework;
import forge_sandbox.greymerk.roguelike.treasure.loot.Shield;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
//import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
//import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import static forge_sandbox.jaredbgreat.dldungeons.builder.Builder.commandPlaceDungeon;
import forge_sandbox.jaredbgreat.dldungeons.themes.ThemeReader;
import forge_sandbox.jaredbgreat.dldungeons.themes.ThemeType;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import otd.aetherlegacy.WorldGenTestAether;
import otd.draylar.data.BattleTowerSchematics;
import otd.event.Event;
import otd.generator.BattleTowerGenerator;
import otd.generator.DungeonPopulator;
import otd.generator.SmoofyDungeonGenerator;
import otd.update.UpdateChecker;
import otd.util.WorldDiagnostic;
import shadow_lib.async.AsyncRoguelikeDungeon;
import shadow_lib.async.AsyncWorldEditor;
import shadow_lib.async.io.papermc.lib.PaperLib;
import shadow_manager.DungeonWorldManager;
import zhehe.util.I18n;
import zhehe.util.Logging;
import zhehe.util.config.PluginConfig;
import zhehe.util.config.WorldConfig;
import zhehe.util.gui.BattleTowerConfig;
import zhehe.util.gui.BiomeSetting;
import zhehe.util.gui.DoomlikeConfig;
import zhehe.util.gui.DraylarBattleTowerConfig;
import zhehe.util.gui.DungeonSpawnSetting;
import zhehe.util.gui.LootItem;
import zhehe.util.gui.LootManager;
import zhehe.util.gui.RoguelikeConfig;
import zhehe.util.gui.RoguelikeLootItem;
import zhehe.util.gui.RoguelikeLootManager;
import zhehe.util.gui.SmoofyConfig;
import zhehe.util.gui.WorldEditor;
import zhehe.util.gui.WorldManager;

/**
 *
 * @author
 */
public class Main extends JavaPlugin {
    public static JavaPlugin instance;
    public static boolean disabled = false;
    private static Integer api_version = 6;
    public static MultiVersion.Version version = MultiVersion.Version.UNKNOWN;

    
    public Main() {
        if(MultiVersion.is114()) {
            version = MultiVersion.Version.V1_14_R1;
            Bukkit.getLogger().log(Level.INFO, "[Oh The Dungeons You'll Go] MC Version: 1.14.x");
        }
        else if(MultiVersion.is115()) {
            version = MultiVersion.Version.V1_15_R1;
            Bukkit.getLogger().log(Level.INFO, "[Oh The Dungeons You'll Go] MC Version: 1.15.x");
        }
        else if(MultiVersion.is116R1()) {
            version = MultiVersion.Version.V1_16_R1;
            Bukkit.getLogger().log(Level.INFO, "[Oh The Dungeons You'll Go] MC Version: 1.16.[0-1]");
        }
        else if(MultiVersion.is116R2()) {
            version = MultiVersion.Version.V1_16_R2;
            Bukkit.getLogger().log(Level.INFO, "[Oh The Dungeons You'll Go] MC Version: 1.16.2");
        }
        else version = MultiVersion.Version.UNKNOWN;
    }
    
    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, "[Oh The Dungeons You'll Go] Plugin is disabled");
        disabled = true;
    }
    
    @Override
    public void onEnable() {
        if(version == MultiVersion.Version.UNKNOWN) {
            Bukkit.getLogger().log(Level.SEVERE, "[Oh The Dungeons You'll Go] Unsupported MC Version");
            throw new UnsupportedOperationException("Unsupported MC Version");
        }
        
        //PaperLib.suggestPaper(this);
        disabled = false;
        instance = this;
        
        Sandbox.mkdir();
        I18n.init();
        WorldConfig.loadWorldConfig();
        
        ThemeReader.setConfigDir();
        ThemeReader.setThemesDir();
        ThemeReader.readSpecialChest();
    	ThemeReader.readThemes(); 
        ThemeType.SyncMobLists();
        
        Dungeon.init = true;        
        
        getServer().getPluginManager().registerEvents(new DLDWorldListener(), this);
        
        getServer().getPluginManager().registerEvents(WorldEditor.instance, this);
        getServer().getPluginManager().registerEvents(WorldManager.instance, this);
        getServer().getPluginManager().registerEvents(RoguelikeConfig.instance, this);
        getServer().getPluginManager().registerEvents(LootManager.instance, this);
        getServer().getPluginManager().registerEvents(LootItem.instance, this);
        getServer().getPluginManager().registerEvents(RoguelikeLootManager.instance, this);
        getServer().getPluginManager().registerEvents(RoguelikeLootItem.instance, this);
        getServer().getPluginManager().registerEvents(BiomeSetting.instance, this);
        getServer().getPluginManager().registerEvents(DoomlikeConfig.instance, this);
        getServer().getPluginManager().registerEvents(BattleTowerConfig.instance, this);
        getServer().getPluginManager().registerEvents(DungeonSpawnSetting.instance, this);
        getServer().getPluginManager().registerEvents(SmoofyConfig.instance, this);
        getServer().getPluginManager().registerEvents(DraylarBattleTowerConfig.instance, this);
        
        getServer().getPluginManager().registerEvents(new Event(), this);
        
        PluginConfig.instance.init();
        PluginConfig.instance.update();
        
        String update = PluginConfig.instance.config.get("updater");
        if(update != null && update.equalsIgnoreCase("TRUE")) {
            Bukkit.getLogger().log(Level.INFO, "[Oh The Dungeons You'll Go] Update checking...");
            asyncUpdateChecker();
        }
        
        registerCommand();
        BattleTowerSchematics.init(this);
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            WorldDiagnostic.diagnostic();
//                try {
//                    Roll.registerRecipe();
//                    DungeonWorldManager.createDungeonWorld();
//                } catch(Exception ex) {
//                    
//                }
        }, 1L);
        
        Bukkit.getScheduler().runTaskLater(this, () -> {
            PaperLib.suggestPaper(Main.instance);
        }, 1L);
        
        
//        loadAdvancement();
    }
    
    private void loadAdvancement() {
        File out = new File(Main.instance.getDataFolder(), "OhTheDungeonAdvancement.jar");
        try(InputStream in = Main.instance.getResource("OhTheDungeonAdvancement.jar");
           OutputStream writer = new BufferedOutputStream(
               new FileOutputStream(out, false))) {
            // Step 3
            byte[] buffer = new byte[1024 * 4];
            int length;
            while((length = in.read(buffer)) >= 0) {
                writer.write(buffer, 0, length);
            }
        } catch(Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Load Advancements error...");
            return;
        }
        try {
            getServer().getPluginManager().loadPlugin(out);
        } catch(InvalidDescriptionException | InvalidPluginException | UnknownDependencyException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Load Advancements error...");
        }
    }
    
    private BukkitRunnable update_check_task_id;
    private final int RESOURCE_ID = 83218;
    private void asyncUpdateChecker() {
        update_check_task_id = new BukkitRunnable() {
            @Override
            public void run() {
                UpdateChecker.CheckUpdate(instance, RESOURCE_ID);
            }
        };
        update_check_task_id.runTaskTimerAsynchronously(this, 200, 20 * 3600 * 1);
    }
    
    private class DLDWorldListener implements Listener {
        @EventHandler(priority = EventPriority.LOW)
        public void onWorldInit(WorldInitEvent event) {
            String world_name = event.getWorld().getName();
            if(world_name.equals(DungeonWorldManager.WORLD_NAME)) return;
            Logging.logInfo("[Oh The Dungeons You'll Go] Found world: " + world_name);
            event.getWorld().getPopulators().add(new DungeonPopulator());
        }
    }
    
    private void registerCommand() {
        OTD otd = new OTD();
        OTD_PLACE otd_place = new OTD_PLACE();
        OTD_CP otd_cp = new OTD_CP();
        
        PluginCommand command;
        command = this.getCommand("oh_the_dungeons");
        if(command != null) {
            command.setExecutor(otd);
            command.setTabCompleter(otd);
        }
        command = this.getCommand("oh_the_dungeons_place");
        if(command != null) {
            command.setExecutor(otd_place);
            command.setTabCompleter(otd_place);
        }
        command = this.getCommand("oh_the_dungeons_cp");
        if(command != null) {
            command.setExecutor(otd_cp);
            command.setTabCompleter(otd_cp);
        }
    }
    
    public class OTD implements TabExecutor {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
            return new ArrayList<>();
        }
        @Override
        public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Player only command");
                return true;
            }
            Player p = (Player) sender;
            if(!p.hasPermission("oh_the_dungeons.admin")) {
                sender.sendMessage("You don't have permission to do that");
                return true;
            }
            WorldManager wm = new WorldManager();
            wm.openInventory(p);
            return true;
        }
    }
    
    public class OTD_CP implements TabExecutor {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
            List<String> res = new ArrayList<>();
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(!p.hasPermission("oh_the_dungeons.admin")) return res;
            }
            if(args.length == 1 || args.length == 2) {
                for(World w : Bukkit.getWorlds()) {
                    res.add(w.getName());
                }
            }
            return res;
        }
        @Override
        public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(!p.hasPermission("oh_the_dungeons.admin")) {
                    sender.sendMessage("You don't have permission to do that");
                    return true;
                }
            }
            
            if(args.length != 2) return false;
            if(!WorldConfig.wc.dict.containsKey(args[0])) {
                sender.sendMessage("Invalid source world");
                return true;
            }
            
            WorldConfig.wc.dict.put(args[1], WorldConfig.wc.dict.get(args[0]));
            WorldConfig.save();
            sender.sendMessage("Done");
            
            return true;
        }
    }

    public class OTD_PLACE implements TabExecutor {
        
        private Set<Player> players = new HashSet<>();
        
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
            List<String> res = new ArrayList<>();
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(!p.hasPermission("oh_the_dungeons.admin")) return res;
                if(args.length == 1) {
                    res.add("roguelike");
                    res.add("doomlike");
                    res.add("battletower");
                    res.add("smoofy");
                    res.add("draylar");
                }
            }
            return res;
        }
        @Override
        @SuppressWarnings("ConvertToStringSwitch")
        public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Player only command");
                return true;
            }
            Player p = (Player) sender;
            if(!p.hasPermission("oh_the_dungeons.admin")) {
                sender.sendMessage("You don't have permission to do that");
                return true;
            }
            String type;
            if(args.length == 0) {
                sender.sendMessage("Dungeon type is needed here");
                return true;
            }
            
            if(!players.contains(p)) {
                sender.sendMessage("Make sure you want to do that");
                sender.sendMessage("Type command again in 10s to confirm");
                players.add(p);
                
                Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
                    @Override
                    public void run() {
                        players.remove(p);
                    }
                }, 20 * 10);
                
                return true;
            }
            
            type = args[0];
            Location loc = p.getLocation();
            Chunk chunk = loc.getChunk();
            World world = loc.getWorld();
            if(type.equals("doomlike")) {
                try {
                    boolean res = commandPlaceDungeon(new Random(), chunk.getX(), chunk.getZ(), world);
                    if(!res) {
                        sender.sendMessage("Fail: No theme available for this chunk...");
                    } else {
                        sender.sendMessage("Done");
                    }
                } catch (Throwable ex) {
                    sender.sendMessage("Internal Error when placing a dungeon in this chunk...");
                }
            } else if(type.equals("roguelike")) {
                Random rand = new Random();
                //IWorldEditor editor = new forge_sandbox.greymerk.roguelike.worldgen.WorldEditor(world);
                AsyncWorldEditor editor = new AsyncWorldEditor(world);
                boolean flag = AsyncRoguelikeDungeon.generateAsync(rand, editor, loc.getBlockX(), loc.getBlockZ());
                
//                Dungeon dungeon = new Dungeon(editor);
//                try {
//                    if(Dungeon.settingsResolver.getSettings(editor, rand, new Coord(loc.getBlockX(), 0, loc.getBlockZ())) == null) {
//                        flag = false;
//                    }
//                } catch(Exception ex) {
//                    flag = false;
//                }
                if(!flag) sender.sendMessage("Fail: No theme available for this chunk...");
//                dungeon.generateNear(rand, loc.getBlockX(), loc.getBlockZ());
                sender.sendMessage("Done");
            } else if(type.equals("battletower")) {
                BattleTowerGenerator g = new BattleTowerGenerator();
                g.generateDungeon(world, new Random(), chunk);
                sender.sendMessage("Done");
            } else if(type.equals("smoofy")) {
                SmoofyDungeonGenerator.halfAsyncGenerate(world, chunk, new Random());
            } else if(type.equals("draylar")) {
                Location location = p.getLocation();
                BattleTowerSchematics.place(world, new Random(), location.getBlockX(), location.getBlockZ());
            } else if(type.equals("test")) {
                Location location = p.getLocation();
                WorldGenTestAether.generate(world, new Random(), location.getBlockX(), location.getBlockZ());
            } else return false;
            return true;
        }
    }
}
