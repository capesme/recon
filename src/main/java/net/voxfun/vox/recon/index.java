package net.voxfun.vox.recon;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.voxfun.vox.recon.commands.*;
import net.voxfun.vox.recon.listners.*;
import net.voxfun.vox.recon.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class index extends JavaPlugin {
    private GameManager gameManager;
    FileConfiguration config = getConfig();
    public static String uri = null;
    public static MongoClient mongoClient = null;
    public static MongoDatabase database = null;
    public static Logger logger = Bukkit.getLogger();

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);

        getServer().getPluginManager().registerEvents(new BlockBreakListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new DamagedListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(gameManager, this), this);
        getServer().getPluginManager().registerEvents(new ItemDropListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new onJoin(gameManager), this);
        getServer().getPluginManager().registerEvents(new onLeave(gameManager), this);
        getServer().getPluginManager().registerEvents(new ShotArrowListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new HandSwapListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(gameManager), this);

        new InGameStatsManager(gameManager);

        getCommand("game").setExecutor(new GameCommand(gameManager));
        getCommand("game").setTabCompleter(new GameCommandTabComplete());
        getCommand("vote").setExecutor(new VoteCommand(gameManager));
        getCommand("vote").setTabCompleter(new VoteCommandTabComplete());
        getCommand("reloadDb").setExecutor(new ReloadDBCommand(gameManager));

        new DontHurtEntity();
        new MinimumPlayerAmount();
        new MaximumPlayerAmount();
        new DontInteractBlockArrow();

        if (config.get("database") == null) {
            config.addDefault("database", "null");
            config.options().copyDefaults(true);
            saveConfig();
        }
        uri = !config.get("database").toString().equals("null") ? config.get("database").toString().startsWith("mongodb+srv://") ? config.get("database").toString() : null : null;
        loadDb();
    }

    private void loadDb() {
        if (uri == null) {
            logger.info("Could not load database, it's missing it's uri!");
            return;
        }
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase("capesme");
        new MapManager(this.gameManager);
    }

    public static void reloadDb() {
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase("capesme");
        MapManager.reloadMaps();
        GameManager.cleanup();
        allowedInteractionBlockList.reload();
    }

    @Override
    public void onDisable() { GameManager.cleanup(); }
}

// - vote stuff - OK
// - spectator chat - OK
// - Move starting countdown above the hotbar - OK
// - no one alive, end the game - OK
// - reset scoreboard - OK
// - game setting to interact with entities. - OK
// - disallowedBlockInteractions: ["*"], ["OAK_DOOR"] - OK
// - custom game time - OK
// - fix tied games - OK .. somewhat
// - kill streaks - OK .. somewhat
// - Aiming game in lobby - OK .. somewhat
// - Leaderboard in game
// - Permissions - OK
// - Disallow 2nd hand - OK
// - Fix players spawning on each other - OK
// - Cosmetics
// - Fix votes - OK
// - Information Messages